package za.co.demo;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Main {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {

		String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
		SchemaFactory sfactory = SchemaFactory.newInstance(language);
		Schema schema = sfactory.newSchema(new File("target/classes/books.xsd"));
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		factory.setNamespaceAware(true);
		factory.setSchema(schema);		
		DocumentBuilder docBuilder = factory.newDocumentBuilder();
		Document doc = docBuilder.parse(new File("target/classes/books.xml"));
		
		System.out.println(doc);
		System.out.println(doc.getDocumentElement().getAttribute("lastUpdated"));
		NodeList list = doc.getElementsByTagName("author");
		for (int i = 0; i < list.getLength(); i++) {
			System.out.println(list.item(i).getTextContent());
		}
		Element elm = doc.getElementById("b1");
		System.out.println(elm.getAttribute("myid"));
	}

}