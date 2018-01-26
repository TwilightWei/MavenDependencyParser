package main.java;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import main.java.config.ConfigReader;
import main.java.file.FileIO;
import main.java.file.FileParser;

public class MavenXmlBuilder {
	public static void main(String[] args) {
		ConfigReader configReader = new ConfigReader();
		FileParser fileParser = new FileParser();
		FileIO fileIO = new FileIO();
		
		ArrayList<String> sources;
		List<File> files;
		String version = new String();
		String filePath = new String();
		String configPath = "D:\\Users\\user\\git\\MavenDependencyParser\\src\\config.properties";
		
		/*
		 * Get config data: source code path and spring framework version
		 */
		sources = new ArrayList<>(Arrays.asList(configReader.readConfig(configPath, "sources").split(",")));
		version = configReader.readConfig(configPath, "version");
		
		/*
		 * Read all pom.xml files and get the dependency part
		 */
		for(String source: sources) {
			filePath = source +"\\dependency";
			files = fileParser.getFiles(source, "pom.xml");
			fileIO.writeString(filePath, "  <dependencies>");
			
			for(File file:files) {
				String artifactId = new String();
				String groupId = new String();
				
				try {
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
					Document doc = dBuilder.parse(file);
					doc.getDocumentElement().normalize();
					
					NodeList gList = doc.getElementsByTagName("groupId");
					for(int temp = 0; temp < gList.getLength(); temp++) {
						Node nNode = gList.item(temp);
						if(nNode.getNodeType() == Node.ELEMENT_NODE) {
							Element eElement = (Element) nNode;
							if(eElement.getParentNode().getNodeName() == "parent") {
								groupId = eElement.getTextContent();
							}
							if(eElement.getParentNode().getNodeName() == "project") {
								groupId = eElement.getTextContent();
							}
						}
					}
					
					NodeList aList = doc.getElementsByTagName("artifactId");
					for(int temp = 0; temp < aList.getLength(); temp++) {
						Node nNode = aList.item(temp);
						if(nNode.getNodeType() == Node.ELEMENT_NODE) {
							Element eElement = (Element) nNode;
							if(eElement.getParentNode().getNodeName() == "project") {
								artifactId = eElement.getTextContent();
							}
						}
					}
					
					fileIO.writeString(filePath, "    <dependency>");
					fileIO.writeString(filePath, "      <groupId>"+groupId+"</groupId>");
					fileIO.writeString(filePath, "      <artifactId>"+artifactId+"</artifactId>");
					fileIO.writeString(filePath, "      <version>"+version+"</version>");
					fileIO.writeString(filePath, "      <type>pom</type>");
					fileIO.writeString(filePath, "    </dependency>");
				} catch (SAXException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					e.printStackTrace();
				}				
			}
			fileIO.writeString(filePath, "  </dependencies>");
			System.out.println("Finished");
		}
	}
}
