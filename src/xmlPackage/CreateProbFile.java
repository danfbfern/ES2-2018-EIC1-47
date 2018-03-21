package xmlPackage;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

import testFrame.GUI;
import variable.Variable;

public class CreateProbFile {
	private GUI gui;
	private ArrayList<Variable> varList;
	
	public CreateProbFile(GUI gui) {
		this.gui=gui;
		
	}
	
	
	public void writeFile() throws IOException {
		//ArrayList<String> varList = new ArrayList<>();
//		varList.add("Type: int, Value:21 ");
//		varList.add("Type: boolean, Value:true");
		try {
			varList = gui.getVarList();
			
			//BufferedReader in = new BufferedReader(new     InputStreamReader(System.in));
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			//root element
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("P");
			doc.appendChild(rootElement);
			
			
			//Set P elements
			String ex1 = "adsfgfgfg";
			rootElement.setAttribute("title", gui.getTxProblem().getText() );
			
			//Descrição
			rootElement.setAttribute("description", gui.getTxDescription().getText() );
			
			//mail
			rootElement.setAttribute("email", gui.getTxMail().getText() );
			
			//TempoMax
			Date now = (gui.getMaxTime());
			   
			DateFormat formatter = DateFormat.getInstance(); // Date and time
		      String dateStr = formatter.format(now);
		      System.out.println(dateStr);
			rootElement.setAttribute("maxTime", dateStr );
			//System.out.println(gui.getMaxTime());
			
			//Number of var
//			rootElement.setAttribute("numberOfVars", ex1 );
			
			//Group Name
			rootElement.setAttribute("groupName", gui.getTxtVarGroup().getText());
			
			///Variaveis
			Element variable = doc.createElement("variables");
			rootElement.appendChild(variable);
			for(int i=0; i!=varList.size(); i++) {
				Element vars = doc.createElement("V" + (i+1));
				variable.appendChild(vars);
				vars.setAttribute("varName", varList.get(i).getName());
				vars.setAttribute("varType", varList.get(i).getVarType());
				vars.setAttribute("varLimit", varList.get(i).getLimit());
			}
			
			
			
			///////////////////////////////////////////////////////////////////////
			/////Write Content
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			
			DOMSource source = new DOMSource(doc);
			String timeStamp = new SimpleDateFormat("_[yyyy-MM-dd_HH-mm-ss]").format(Calendar.getInstance().getTime());
			
			String name = "./xml/user/"+gui.getTxProblem().getText()+""+timeStamp+".xml";
			
			
			StreamResult result = new StreamResult(new File(name));
			
		
			transformer.transform(source,  result);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		  } catch (TransformerException tfe) {
			tfe.printStackTrace();
		  }
	}
	
//	public static void main(String[] args) {
//		CreateProbFile pf = new CreateProbFile();
//		try {
//			pf.writeFile();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
