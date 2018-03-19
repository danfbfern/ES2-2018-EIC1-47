package xmlPackage;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CreateProbFile {
	
	
	public void writeFile() throws IOException {
		ArrayList<String> varList = new ArrayList<>();
		varList.add("Type: int, Value:21 ");
		varList.add("Type: boolean, Value:true");
		try {
			
			BufferedReader in = new BufferedReader(new     InputStreamReader(System.in));
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			//root element
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("P");
			doc.appendChild(rootElement);
			
			
			//Set P elements
			String ex1 = in.readLine();
			rootElement.setAttribute("name", ex1 );
			
			//Descrição
			rootElement.setAttribute("description", ex1 );
			
			//mail
			rootElement.setAttribute("email", ex1 );
			
			//TempoMax
			rootElement.setAttribute("maxTime", ex1 );
			
			//Number of var
			rootElement.setAttribute("numberOfVars", ex1 );
			
			//Group Name
			rootElement.setAttribute("groupName", ex1 );
			
			///Variaveis
			Element variable = doc.createElement("variable");
			rootElement.appendChild(variable);
			for(int i=0; i!=varList.size(); i++) {
				variable.setAttribute("V"+i, varList.get(i));
			}
			
			
			
			///////////////////////////////////////////////////////////////////////
			/////Write Content
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("./xml/user/file.xml"));
			
			transformer.transform(source,  result);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		  } catch (TransformerException tfe) {
			tfe.printStackTrace();
		  }
	}
	
	public static void main(String[] args) {
		CreateProbFile pf = new CreateProbFile();
		try {
			pf.writeFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
