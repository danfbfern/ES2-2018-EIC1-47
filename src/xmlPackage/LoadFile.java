package xmlPackage;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import testFrame.GUI;

public class LoadFile {
	private File file;
	private GUI gui;
	
	public LoadFile(File f, GUI gui) {
		this.file=f;
		this.gui=gui;
	}
	
	public void load() {
		try {
			File inputFile = file;
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();
	         System.out.print("Root element: ");
	         System.out.println(doc.getDocumentElement().getNodeName());
	         NodeList nList = doc.getElementsByTagName("P");
	         System.out.println(nList.getLength());
	         
	         
	         for (int temp = 0; temp < nList.getLength(); temp++) {
	             Node nNode = nList.item(temp);//P
	             
	             NamedNodeMap map = nNode.getAttributes();//atributos do P
	             System.out.println("description" + map.item(0).getNodeValue());
	             gui.setTxDescription((map.item(0).getNodeValue()));
	             System.out.println("email " + map.item(1).getNodeValue());
	             gui.setTxMail((map.item(1).getNodeValue()));
	             System.out.println("groupname " + map.item(2).getNodeValue());
	             gui.setTxtVarGroup((map.item(2).getNodeValue()));
	             System.out.println("maxTime " + map.item(3).getNodeValue());
	             
	             DateFormat format = new SimpleDateFormat("dd-mm-yyyy HH.MM");
	             Date date=new Date();
				try {
					date = format.parse(map.item(3).getNodeValue());
				} catch (DOMException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
	            
				   
	 			  DateFormat formatter = DateFormat.getInstance(); // Date and time
	 		      String dateStr = formatter.format(date);
	 		      System.out.println(dateStr);
	             gui.setMaxTime(dateStr);
	             System.out.println("title " + map.item(4).getNodeValue());
	             gui.setTxProblem((map.item(4).getNodeValue()));


	            NodeList list2 = nNode.getChildNodes();///variables
	            Node nNode2 = list2.item(0);
	            
	            NodeList map2 = nNode2.getChildNodes();//V's
	            gui.editVariables(map2.getLength());
	            for(int i=0; i<map2.getLength(); i++) {
	            	Node node3 = map2.item(i);
	            	NamedNodeMap map3 = node3.getAttributes();
	            	System.out.println("varLimit" + map3.item(0));
	            	System.out.println("varName" + map3.item(1));
	            	System.out.println("varType" + map3.item(2));
	            	
	            }
	             
//	             if(nNode.getNodeType() == Node.ATTRIBUTE_NODE) {
//	            	 String attr = ((Attribute) nNode).toString();
//	            	 System.out.println(attr);
//	             }
//	             
//	             if (nNode.getNodeType() == Node.ELEMENT_NODE) {
//	                Element eElement = (Element) nNode;
//	                System.out.print("company : ");
//	                System.out.println(eElement.getAttribute("company"));
//	                NodeList carNameList = eElement.getElementsByTagName("carname");
//	                
//	                for (int count = 0; count < carNameList.getLength(); count++) {
//	                   Node node1 = carNameList.item(count);
//	                   
//	                   if (node1.getNodeType() == node1.ELEMENT_NODE) {
//	                      Element car = (Element) node1;
//	                      System.out.print("car name : ");
//	                      System.out.println(car.getTextContent());
//	                      System.out.print("car type : ");
//	                      System.out.println(car.getAttribute("type"));
//	                   }
//	                }
//	             }
	          }


		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
