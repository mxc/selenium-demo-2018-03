package za.co.demo;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Main {

	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
		 DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		    Document document = parser.parse(new File("target/classes/productlist.xml"));

		    // create a SchemaFactory capable of understanding WXS schemas
		    SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

		    // load a WXS schema, represented by a Schema instance
		    Source schemaFile = new StreamSource(new File("target/classes/product.xsd"));
		    Schema schema = factory.newSchema(schemaFile);

		    // create a Validator instance, which can be used to validate an instance document
		    Validator validator = schema.newValidator();
		    
		    // validate the DOM tree
		    try {
		        validator.validate(new DOMSource(document));
		    } catch (SAXException e) {
		        e.printStackTrace();
		    
		    }
		    System.out.println(document.getDocumentElement());
		    NodeList list = document.getElementsByTagName("product");
		    System.out.println("Count="+list.getLength());
		
	}

}
