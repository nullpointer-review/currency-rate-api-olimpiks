package com.acme.tamada_bayanist;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URI;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.common.collect.ImmutableMap;

public class XmlUtils {

	private static class DocumentBuilderFactoryHolder {
		private static final DocumentBuilderFactory factory = init();

		private static DocumentBuilderFactory init() {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			return factory;
		}
	}

	private static final ThreadLocal<DocumentBuilder> DOCUMENT_BUILDER = new ThreadLocal<DocumentBuilder>() {
		@Override
		protected DocumentBuilder initialValue() {
			try {
				return DocumentBuilderFactoryHolder.factory.newDocumentBuilder();
			} catch (ParserConfigurationException exc) {
				throw new IllegalArgumentException(exc);
			}
		}
	};

	private static Map<Class<?>, QName> type_QName = ImmutableMap.<Class<?>, QName> builder().put(Double.class, XPathConstants.NUMBER)//
			.put(String.class, XPathConstants.STRING)//
			.put(Boolean.class, XPathConstants.BOOLEAN)//
			.put(NodeList.class, XPathConstants.NODESET)//
			.put(Node.class, XPathConstants.NODE)//
			.build();

	/**
	 * 
	 * @param doc
	 * @param expression
	 * @param clazz
	 *            java.lang.Double, java.lang.String, java.lang.Boolean,
	 *            org.w3c.dom.NodeList, org.w3c.dom.Node
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 * @throws XPathExpressionException
	 */
	public static <T> T xpath(Document doc, String expression, Class<T> clazz) throws SAXException, IOException, XPathExpressionException {
		// create an XPathFactory
		XPathFactory xFactory = XPathFactory.newInstance();

		// create an XPath object
		XPath xpath = xFactory.newXPath();

		// compile the XPath expression
		XPathExpression expr = xpath.compile(expression);
		// run the query and get a nodeset
		QName returnType = type_QName.get(clazz);
		if (returnType == null) {
			throw new IllegalArgumentException("illegal argument clazz:" + clazz);
		}
		return (T) expr.evaluate(doc, returnType);
	}

	public static <T> T xpath(URI uri, String expression, Class<T> clazz) throws SAXException, IOException, XPathExpressionException {
		Document doc = DOCUMENT_BUILDER.get().parse(uri.toString());
		return xpath(doc, expression, clazz);
	}

	public static <T> T xpath(InputSource is, String expression, Class<T> clazz) throws SAXException, IOException, XPathExpressionException {
		Document doc = DOCUMENT_BUILDER.get().parse(is);

		return xpath(doc, expression, clazz);

	}

	public static <T> T xpath(String xml, String expression, Class<T> clazz) throws SAXException, IOException, XPathExpressionException {
		return xpath(new InputSource(new StringReader(xml)), expression, clazz);
	}

	public static <T> T xpath(InputStream is, String expression, Class<T> clazz) throws SAXException, IOException, XPathExpressionException {
		return xpath(new InputSource(is), expression, clazz);
	}

}
