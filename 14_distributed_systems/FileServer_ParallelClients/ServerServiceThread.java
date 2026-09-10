import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerServiceThread extends Thread {

	private Socket socket;
	
	ServerServiceThread(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try(BufferedReader fromClient = new BufferedReader(
				new InputStreamReader(socket.getInputStream(), "UTF-8"));) {
			String request = fromClient.readLine();
			System.out.println("Request: "+request);
			
			String[] requestArray = request.split(" ");
			
			if(requestArray[0].equals("GET") && requestArray[1].endsWith(".txt")) {
				String fileName = requestArray[1];
				System.out.println("Dateiübertragung von "+fileName+" an "+socket.getInetAddress()+" mit Client-Nr. "
						+requestArray[2]+" wird gestartet.");
				
				try(BufferedInputStream fromFile = new BufferedInputStream(new FileInputStream(fileName))) {
					BufferedOutputStream toClient = new BufferedOutputStream(socket.getOutputStream());
					
					int data = -1;
					while((data = fromFile.read()) != -1) {
						toClient.write(data);
					}
					toClient.flush();
					System.out.println("Datei "+fileName+" wurde an Client-Nr. "+requestArray[2] +" unter "
										+socket.getInetAddress()+" übertragen.");
				}
			}
			socket.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}