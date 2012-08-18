package in.jmkl.dcsms.frame.widget;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Keterangan {
	String result = null;
	String title = null;
	String author = null;
	String ver = null;
	InputStream filename = null;
	
	public String getTitle(){
		return title;
	}
	public String getAuthor(){
		return author;
		
	}
	public String getVersion(){
		return ver;
	}
	
	
	public void getKeterangan(String namazip, String namaxmldalamzip) {


		Document obj_doc = null;
		DocumentBuilderFactory doc_build_fact = null;
		DocumentBuilder doc_builder = null;
		try {
			filename = new FileInputStream(namazip);
			ZipInputStream zis = new ZipInputStream(filename);
			ZipEntry ze = null;
			while ((ze = zis.getNextEntry()) != null) {
				if (ze.getName().equals(namaxmldalamzip)) {
					doc_build_fact = DocumentBuilderFactory.newInstance();
					doc_builder = doc_build_fact.newDocumentBuilder();
					obj_doc = doc_builder.parse(zis);
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		NodeList items = null;
		if (null != obj_doc) {
			Element root = obj_doc.getDocumentElement();
			items = root.getElementsByTagName("dcsmsframe");
			for (int i = 0; i < items.getLength(); i++) {
				Node item = items.item(i);
				NodeList properties = item.getChildNodes();
				for (int j = 0; j < properties.getLength(); j++) {
					Node property = properties.item(j);
					String name = property.getNodeName();
					if (name.equalsIgnoreCase("title")) {
						// Store it where you want
						title = property.getFirstChild().getNodeValue();

					}
					if (name.equalsIgnoreCase("author")) {
						author = property.getFirstChild().getNodeValue();

					}
					if (name.equalsIgnoreCase("ver")) {
						ver = property.getFirstChild().getNodeValue();

					}
					result = title + "\n" + author + "\n" + ver;

				}
			}

		}

	}

}
