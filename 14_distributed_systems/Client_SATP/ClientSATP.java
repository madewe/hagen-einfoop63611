package ah2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientSATP {
	
	public static final int SERVER_PORT = 7893;
	
	public static void main(String[] args) {
		String host = "1.2.3.4";
		
		// This is not very professionally, but adequate for the purpose of this exercise
		String benutzer = "hello";
		String pwd = "my friend";
		
		// VERBINDUNGSAUFBAU
		try(Socket socket = new Socket(host, SERVER_PORT);
			BufferedWriter toServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "US-ASCII"));
			BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream(), "US-ASCII"));
			) {
		
			System.out.println("Verbindungsanfrage gesendet an "+host+":"+SERVER_PORT);
			
			// KOMMUNIKATION MIT SERVER
			String response = "";
			
			while((response = fromServer.readLine()) != null) {
				System.out.println(response);
				if(response.startsWith("INFO ")) {
					String serverMessage = response.substring(5);
					//System.out.println(serverMessage);
					
					if(serverMessage.contains("auth")) {
						// AUTHENTIFIZIERUNG
						toServer.write("LOGIN "+benutzer+" "+pwd+"\r\n");
						System.out.println("Sende LOGIN" );
						toServer.flush();
					}
					continue;
				} else if(response.startsWith("ENDE")) {
					System.out.println("Connection closed by foreign host");
					break;
				} else if(response.startsWith("AUFGABE ")) {
					String loesung = loeseAufgabe(response.substring(8));
					toServer.write(loesung+"\r\n");
					toServer.flush();
					System.out.println(loesung);
					continue;
				}				
			}
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private static String loeseAufgabe(String aufgabe) {
		String[] array = aufgabe.split(" ");
		int zahl1 = Integer.valueOf(array[0]);
		int zahl2 = Integer.valueOf(array[2]);
		int ergebnis = 0;
		switch(array[1]) {
		case "+":
			ergebnis = zahl1 + zahl2;
			break;
		case "-":
			ergebnis = zahl1 - zahl2;
			break;
		case "*":
			ergebnis = zahl1 * zahl2;
			break;
		case "/":
			ergebnis = zahl2 != 0 ? zahl1 / zahl2 : 0;
			break;
		}
		return String.valueOf(ergebnis);
	}
}