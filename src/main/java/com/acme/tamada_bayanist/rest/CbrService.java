package com.acme.tamada_bayanist.rest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.xml.xpath.XPathExpressionException;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.acme.tamada_bayanist.XmlUtils;
import com.acme.tamada_bayanist.properties.CbrProperties;
import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * <pre>
 * Рекомендации по работе с веб-сервисами сайта Банка России:
- минимизируйте количество вызовов;
- минимизируйте количество передаваемой информации;
- оптимизируйте логику работы с нашими веб-сервисами;
- при использовании полученных данных в своих приложениях и особенно для публикации их на своих интернет или интранет серверах используйте промежуточное хранение информации!
 * </pre>
 */
@Service
public final class CbrService {

	private final Cache<String, String> url_xml = CacheBuilder.newBuilder().maximumSize(100).expireAfterWrite(2, TimeUnit.HOURS).build();

	private static final Logger log = LoggerFactory.getLogger(CbrService.class);

	@Autowired
	CbrProperties props;

	@PostConstruct
	public void init() {

	}

	@PreDestroy
	public void shutdown() {

	}

	private String readUrl(final String url) {
		try {
			return url_xml.get(url, new Callable<String>() {
				@Override
				public String call() throws MalformedURLException, IOException {
					log.debug("cache url - '{}'", url);
					return new Scanner(new URL(url).openStream(), "UTF-8").useDelimiter("\\A").next();
				}
			});
		} catch (ExecutionException e) {
			throw new RuntimeException(e.getCause());
		}
	}

	public String rate(final String code, final DateTime date) {
		Preconditions.checkNotNull(code, "code must not be null");
		Preconditions.checkNotNull(date, "date must not be null");

		final String uri = props.getUrl(date);
		final String xml = readUrl(uri);
		try {
			return XmlUtils.xpath(xml, props.getXpath(code), String.class);
		} catch (XPathExpressionException | SAXException | IOException e) {
			throw new RuntimeException(e.getCause());
		}
	}

}
