package com.acme.tamada_bayanist;

import java.net.URI;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.acme.tamada_bayanist.properties.CbrProperties;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationTest.class)
public class CbrTest {

	@Autowired
	CbrProperties props;

	@Test
	public void testXpath() throws Exception {
		URI uri = ClassLoader.getSystemResource("xml.xml").toURI();
		Assert.assertEquals("62,2237", XmlUtils.xpath(uri, props.getXpath("USD"), String.class));
	}

}
