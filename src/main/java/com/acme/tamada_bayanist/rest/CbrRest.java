package com.acme.tamada_bayanist.rest;

import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.acme.tamada_bayanist.properties.CbrProperties;
import com.google.common.collect.ImmutableMap;

@RestController
@RequestMapping(value = "/currency/api/rate")
public class CbrRest {

	@Autowired
	CbrProperties props;
	@Autowired
	CbrService service;

	@RequestMapping(value = "{code:[A-Z]{3}}", method = RequestMethod.GET)
	public Map<String, Object> rate(final @PathVariable("code") String code) {
		return this.rate(code, new DateTime().plusDays(1));
	}

	@RequestMapping(value = "{code:[A-Z]{3}}/{date}", method = RequestMethod.GET)
	public Map<String, Object> rate(final @PathVariable("code") String code, @PathVariable("date") @DateTimeFormat(iso = ISO.DATE) DateTime date) {
		return ImmutableMap.<String, Object> builder()//
				.put("code", code)//
				.put("date", date.toString("YYYY-MM-dd"))//
				.put("rate", service.rate(code, date))//
				.build();
	}

}
