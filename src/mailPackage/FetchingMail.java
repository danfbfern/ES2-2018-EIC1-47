package mailPackage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

public class FetchingMail {
	private static ArrayList<Message> msg;
	
	public static void fetch(String pop3Host, String storeType, String user, String password) {
		
		try {
			//create properties field
			msg = new ArrayList<>();
			Properties properties = new Properties();
			properties.put("mail.store.protocol", "pop3");
			properties.put("mail.pop3.host", pop3Host);
	        properties.put("mail.pop3.port", "995");
	        properties.put("mail.pop3.starttls.enable", "true");
	        Session emailSession = Session.getDefaultInstance(properties);
	         //emailSession.setDebug(true);
	        
	     // create the POP3 store object and connect with the pop server
	         Store store = emailSession.getStore("pop3s");

	         store.connect(pop3Host, user, password);

	         // create the folder object and open it
	         Folder emailFolder = store.getFolder("INBOX");
	         emailFolder.open(Folder.READ_ONLY);

	         BufferedReader reader = new BufferedReader(new InputStreamReader(
		      System.in));

	         // retrieve the messages from the folder in an array and print it
	         Message[] messages = emailFolder.getMessages();
	         System.out.println("messages.length---" + messages.length);

	         for (int i = 0; i < messages.length; i++) {
	            Message message = messages[i];
	            msg.add(message);
	         }

	         // close the store and folder objects
//	         emailFolder.close(false);
//	         store.close();

	      } catch (NoSuchProviderException e) {
	         e.printStackTrace();
	      } catch (MessagingException e) {
	         e.printStackTrace();
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
		}
	
	
	
//	/*
//	   * This method checks for content-type 
//	   * based on which, it processes and
//	   * fetches the content of the message
//	   */
//	   public static void writePart(Part p) throws Exception {
//	      if (p instanceof Message)
//	         //Call methos writeEnvelope
//	         writeEnvelope((Message) p);
//
//	      System.out.println("----------------------------");
//	      System.out.println("CONTENT-TYPE: " + p.getContentType());
//
//	      //check if the content is plain text
//	      if (p.isMimeType("text/plain")) {
//	         System.out.println("This is plain text");
//	         System.out.println("---------------------------");
//	         System.out.println((String) p.getContent());
//	      } 
//	      
//	      else {
//	         Object o = p.getContent();
//	         if (o instanceof String) {
//	            System.out.println("This is a string");
//	            System.out.println("---------------------------");
//	            System.out.println((String) o);
//	         } 
//	         else if (o instanceof InputStream) {
//	            System.out.println("This is just an input stream");
//	            System.out.println("---------------------------");
//	            InputStream is = (InputStream) o;
//	            is = (InputStream) o;
//	            int c;
//	            while ((c = is.read()) != -1)
//	               System.out.write(c);
//	         } 
//	         else {
//	            System.out.println("This is an unknown type");
//	            System.out.println("---------------------------");
//	            System.out.println(o.toString());
//	         }
//	      }
//
//	   }
//	  
//	   
//	   
//	   /*
//	    * This method would print FROM,TO and SUBJECT of the message
//	    */
//	    public static void writeEnvelope(Message m) throws Exception {
//	       System.out.println("This is the message envelope");
//	       System.out.println("---------------------------");
//	       Address[] a;
//
//	       // FROM
//	       if ((a = m.getFrom()) != null) {
//	          for (int j = 0; j < a.length; j++)
//	          System.out.println("FROM: " + a[j].toString());
//	       }
//
//	       // TO
//	       if ((a = m.getRecipients(Message.RecipientType.TO)) != null) {
//	          for (int j = 0; j < a.length; j++)
//	          System.out.println("TO: " + a[j].toString());
//	       }
//
//	       // SUBJECT
//	       if (m.getSubject() != null)
//	          System.out.println("SUBJECT: " + m.getSubject());
//
//	    }
	    
	    public static ArrayList<Message> getMsg() {
		return msg;
	}



//		public static void main(String[] args) {
//
//	        String host = "pop.gmail.com";// change accordingly
//	        String mailStoreType = "pop3";
//	        String username = 
//	           "testmail2018es@gmail.com";// change accordingly
//	        String password = "testmail2018";// change accordingly
//
//	        //Call method fetch
//	        fetch(host, mailStoreType, username, password);
//
//	     }
	}

