package com.acme.tamada_bayanist.properties;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;

import com.google.common.collect.ImmutableMap;
import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

@ConfigurationProperties(prefix = "cbr")
public class CbrProperties {

	@Autowired
	PebbleEngine pebbleEngine;

	private String url;
	private String xpath;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	}

	private String pebbleEngine(String templateString, Map<String, Object> context) {
		try {
			PebbleTemplate template = pebbleEngine.getTemplate(templateString);
			Writer writer = new StringWriter();
			template.evaluate(writer, context);
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getUrl(DateTime date) {
		Map<String, Object> context = ImmutableMap.<String, Object> builder()//
				.put("dd", date.toString("dd"))//
				.put("MM", date.toString("MM"))//
				.put("YYYY", date.toString("YYYY"))//
				.build();
		return pebbleEngine(this.getUrl(), context);
	}

	public String getXpath(String code) {
		Map<String, Object> context = ImmutableMap.<String, Object> builder()//
				.put("code", code)//
				.build();
		return pebbleEngine(this.getXpath(), context);
	}

}
