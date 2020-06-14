import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Server
{
	private static HashMap<String, Aranzman> aranzmani = new HashMap<String, Aranzman>();
	
    private int port;
    private ServerSocket serverSocket;


    public Server(int port)
            throws IOException
    {
        this.port = port;
        this.serverSocket = new ServerSocket(this.port);
    }


    public void run()
    {
        System.out.println("Web server running on port: " + port);
        System.out.println("Document root is: " + new File("/static").getAbsolutePath() + "\n");

        Socket socket;

        while (true)
        {
            try
            {
                // prihvataj zahteve
                socket = serverSocket.accept();
                InetAddress addr = socket.getInetAddress();

                // dobavi resurs zahteva
                String resource = this.getResource(socket.getInputStream());
                
                // fail-safe
                if (resource == null)
                    continue;

                if (resource.equals(""))
                    resource = "static/pocetna.html";

                System.out.println("Request from " + addr.getHostName() + ": " +  resource);

                // posalji odgovor
                this.sendResponse(resource, socket.getOutputStream());
                socket.close();
                socket = null;
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }


    private String getResource(InputStream is)
            throws IOException
    {
        BufferedReader dis = new BufferedReader(new InputStreamReader(is));
        String s = dis.readLine();

        // fail-safe
        if (s == null)
            return null;

        String[] tokens = s.split(" ");

        // prva linija HTTP zahteva: METOD /resurs HTTP/verzija
        // obradjujemo samo GET metodu
        String method = tokens[0];
        if (!method.equals("GET"))
            return null;

        // String resursa
        String resource = tokens[1];

        // izbacimo znak '/' sa pocetka
        resource = resource.substring(1);

        // ignorisemo ostatak zaglavlja
        String s1;
        while (!(s1 = dis.readLine()).equals(""))
            System.out.println(s1);

        return resource;
    }


    private void sendResponse(String resource, OutputStream os)
            throws IOException
    {
        PrintStream ps = new PrintStream(os);

        // zamenimo web separator sistemskim separatorom
        resource = resource.replace('/', File.separatorChar);
        
        resource = URLDecoder.decode(resource, "UTF-8");

        if(resource.startsWith("register")) {
        	
        	System.out.println("tu sam na register");
        	
        	HashMap<String, String> parameters = getParameter(resource);
        	String id_aranzmana = parameters.get("id_aranzmana");
        	String full_name = parameters.get("full_name");
        	String passport_number = parameters.get("passport_number");
        	String destination_name = parameters.get("destination_name");
        	String vacation_type = parameters.get("vacation_type");
        	String price = parameters.get("price");
        	
    		if(aranzmani.containsKey(id_aranzmana) || !id_aranzmana.matches("[0-9][0-9]-[0-9][0-9][0-9]")) { // 02-123
				ps.print("HTTP/1.1 200 OK\n\n");
				ps.print("<html><body><b>Aranzman vec kreiran!</b></body></html>");
				return;
			} else {
				Aranzman aranzman = new Aranzman(id_aranzmana, full_name, passport_number, destination_name, vacation_type, price, "Potvrda aranzmana");
				aranzmani.put(id_aranzmana, aranzman);
			}
    		
    		ps.print("HTTP/1.1 200 OK\n\n");
    		
    		String response = "<html>" + 
    				"    <head>" + 
    				"        <title>Pregled aranzmana</title>" + 
    				"    </head>" + 
    				"    <body>" + "<h1>HTTP PREGLED ARANZMANA</h1>" +
    				"        <table border=\"1\">" + 
    				"            <thead>" + 
    				"                <td>Broj Aranzmana</td>" + 
    				"                <td>Puno Ime</td>" + 
    				"                <td>Broj pasosa</td>" + 
    				"                <td>Naziv destinacije</td>" + 
    				"                <td>Cena</td>               " + 
    				"            </thead>" + 
    				"            <tbody>" + 
    				"                <td></td>" + 
    				"            </tbody>";
    				       
    		for(Aranzman aranz : aranzmani.values()) {
    			if(aranz.isConfirmed()) {
    				response +="<tr style=\"background-color: gray;\">"; 
    			}else {
    				response += "<tr>";
    			}
    			
    			response += "<td>" + aranz.getId_aranzmana()+"</td>" +
    						"<td>"+ aranz.getFull_name()+"</td>" +
    						"<td>"+ aranz.getPassport_number()+"</td>" +
    						"<td>"+ aranz.getDestination_name()+"</td>" +
    						"<td>"+ aranz.getPrice()+"</td>";
    			
    			if(!aranz.isConfirmed()) {
    				response += "<td><a href=\"http://localhost:8081/confirm?id="+aranz.getId_aranzmana()+"\">"+ aranz.getConfirmation() +"</a></td>";
    			} else {
    				response += "<td></td>";		
    			}
    			
    			
    			//"<td><a href=\"http://localhost:8074/positive?id="+patient.getBzo()+"\">"+ patient.getRezultat() +"</a></td>";
    					//response+="<td> <a href =\"https:localhost:8081/confirm?id ="+aranz.getId_aranzmana()+ "\" >" + aranz.getConfirmation()+ "</a></td>";   					
    	
    		}
    		
    		response += "</tr></tbody></table>";
    		response += "</tbody></table>";
    		response += "<h2>Pretraga aranzmana  po destinaciji:</h2>";
    		response += "</tr></tbody></table>";
			response += "<form action=\"http://localhost:8081/search\">\r\n" + 
					"		    <label for=\"\">Naziv destinacije : </label>\r\n" + 
					"		    <input type=\"text\" name=\"destinacija_pretraga\" id=\"\">\r\n" + 
					"		    <input type=\"submit\" value=\"Pretrazi\">\r\n" + 
					"		</form>";
			response += "</body></html>";
			
			ps.print(response);
			return;     	        	
        }else if(resource.startsWith("confirm")) {
        	
        	System.out.println("usao sam ovdje");
        	
        	HashMap<String, String> parameters = getParameter(resource);
        	
        	String id_aranzmana = parameters.get("id");             	
        	id_aranzmana = id_aranzmana.replaceAll("\\\\", "/");
        	
        	Aranzman aranz = aranzmani.get(id_aranzmana);      
        	
        	ps.print("HTTP/1.1 200 OK\n\n");
        	System.out.println("pre konfirmacije");
        	aranz.setConfirmed(true);
        	System.out.println("nakon konfirmacije");
        	
        	String response ="<html>" + 
    				"    <head>" + 
    				"        <title>Pregled aranzmana</title>" + 
    				"    </head>" + 
    				"    <body>" + "<h1 style=\"color: green;\">HTTP Pregled aranzmana</h1>"+ 
    				"        <table border=\"1\">" + 
    				"            <thead>" + 
    				"                <td>Broj Aranzmana</td>" + 
    				"                <td>Puno Ime</td>" + 
    				"                <td>Broj pasosa</td>" + 
    				"                <td>Naziv destinacije</td>" + 
    				"                <td>Cena</td>               " + 
    				"            </thead>" + 
    				"            <tbody>" + 
    				"                <td></td>" + 
    				"            </tbody>";
        	for(Aranzman aranz11 : aranzmani.values()) {
        		if(aranz11.isConfirmed()) {
        			response += "<tr style=\"background-color: gray;\">";
        			
    				response += "<td>"+ aranz11.getId_aranzmana() +"</td>" +
    						"<td>"+ aranz11.getFull_name() +"</td>" +
    						"<td>"+ aranz11.getPassport_number() +"</td>" +
    						"<td>"+ aranz11.getDestination_name() +"</td>" +
    						"<td>"+ aranz11.getPrice() +"</td>" +
    						"<td>"+"Potvrdjeno"+"</td>";
        			
        		}else {
        			response += "<tr>";     	
        			
        			response += "<td>"+ aranz11.getId_aranzmana() +"</td>" +
    						"<td>"+ aranz11.getFull_name() +"</td>" +
    						"<td>"+ aranz11.getPassport_number() +"</td>" +
    						"<td>"+ aranz11.getDestination_name() +"</td>" +
    						"<td>"+ aranz11.getPrice() +"</td>";
    				response += "<td> <a href =\"https:localhost:8081/confirm?id ="+aranz.getId_aranzmana()+ "\" >" + aranz.getConfirmation()+ "</a></td>";
        		}       	        		       		    	        		
        	}
        	
    		response += "</tr></tbody></table>";
    		response += "</tbody></table>";
    		response += "<h2>Pretraga aranzmana  po destinaciji:</h2>";
    		response += "</tr></tbody></table>";
			response += "<form action=\"http://localhost:8081/search\">\r\n" + 
					"		    <label for=\"\">Naziv destinacije : </label>\r\n" + 
					"		    <input type=\"text\" name=\"destinacija_pretraga\" id=\"\">\r\n" + 
					"		    <input type=\"submit\" value=\"Pretrazi\">\r\n" + 
					"		</form>";
			response += "</body></html>";
			
			ps.print(response);
			return;
        	
        	
        	
        	
        	
        }else if(resource.startsWith("search")) {
        	
        	HashMap<String, String> parameters = getParameter(resource);
        	String destinacija = parameters.get("destinacija_pretraga");
        	
        	ArrayList<Aranzman> aranzmani_list = new ArrayList<Aranzman>();
        	
        	for(Aranzman aranz11 : aranzmani.values()) {
        		if(aranz11.getDestination_name().equalsIgnoreCase(destinacija)) {
        			aranzmani_list.add(aranz11);
        		}      		
        	}
        	
        	ps.print("HTTP/1.1 200 OK\n\n");
        	
        	String response = "<html>" + 
					"    <head>" + 
					"        <title>Rezultat pretrage po destinaciji</title>" + 
					"    </head>";
        	if(aranzmani_list.isEmpty()) {
        		response += "<body><h2>Nema pronadjenih aranzmana sa datom destinacijom : \""+destinacija+"\"</h2></body>";
        	}else {
        		
        		response += "<body>" + "<h1 style=\"color: green;\">HTTP Rezultati pretrage aranzmana</h1>"+
						"        <table border=\"1\">" + 
						"            <tr>" + 
						"                <th>Broj aranzmana</th>" + 
						"                <th>Puno ime</th>" + 
						"                <th>Broj pasosa</th>" + 
						"                <th>Naziv destinacije</th>" + 
						"                <th>Cena</th>" + 						                  
						"            </tr>";
        		
        		for(Aranzman aranz11: aranzmani_list) {
        			if(aranz11.isConfirmed() == true) {        	
        				System.out.println("USAO SAM U BACKGROUP GRAY");
        				response +="<tr style=\"background-color: gray;\">"; 				
        			}else {
        				response += "<tr>";
        			}
        				        		  
	        		
	        		response += "<td>"+ aranz11.getId_aranzmana() +"</td>" +
							"<td>"+ aranz11.getFull_name() +"</td>" +
							"<td>"+ aranz11.getPassport_number() +"</td>" +
							"<td>"+ aranz11.getDestination_name() +"</td>" +
							"<td>"+ aranz11.getPrice() +"</td>";
	        		
	        		if(aranz11.isConfirmed() == false) {
	    				response += "<td><a href=\"http://localhost:8081/confirm?id="+aranz11.getId_aranzmana()+"\">"+ aranz11.getConfirmation() +"</a></td>";
	    			} else {
	    				response += "<td>Potvrdjeno!</td>";		
	    			}
	        		
	        		response += "</tr>";
        		
        		}
        		
        		response += "</table></body>";
        		
        		
        	}
        	
        	response += "</html>";
			ps.print(response);
			return;
      	
        }else {
      
        File file = new File(resource);

        if (!file.exists())
        {
            // ako datoteka ne postoji, vratimo kod za gresku
            String errorCode = "HTTP/1.0 404 File not found\r\n"
                    + "Content-type: text/html; charset=UTF-8\r\n\r\n<b>404 Not found:"
                    + file.getName() + "</b>";

            ps.print(errorCode);

//            ps.flush();
            System.out.println("Could not find resource: " + file);
            return;
        }

        // ispisemo zaglavlje HTTP odgovora
        ps.print("HTTP/1.0 200 OK\r\n\r\n");

        // a, zatim datoteku
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[8192];
        int len;

        while ((len = fis.read(data)) != -1)
            ps.write(data, 0, len);

        ps.flush();
        fis.close();
        }
    }
    
    static HashMap<String, String> getParameter(String requestLine){
    	HashMap<String,String> return_val = new HashMap<String, String>();
    	
    	String request = requestLine.split("\\?")[0];
    	
    	return_val.put("request", request);
    	String parameters = requestLine.substring(requestLine.indexOf("?")+1);
    	StringTokenizer tokens = new StringTokenizer(parameters,"&");
    	
    	while(tokens.hasMoreTokens()) {
    		String key ="";
    		String value ="";
    		StringTokenizer param_tokens = new StringTokenizer(tokens.nextToken(),"=");
    		key = param_tokens.nextToken();
    		if(param_tokens.hasMoreTokens()) {
    			value = param_tokens.nextToken();
    		}   		
    		return_val.put(key, value);
    	}   	
    	return return_val;    	    	    	    	
    }
    
    
}
