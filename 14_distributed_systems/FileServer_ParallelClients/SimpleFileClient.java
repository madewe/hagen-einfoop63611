import java.net.Socket;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.BufferedInputStream;
import java.io.IOException;

public class SimpleFileClient extends Thread {
	
	private Socket socket;
	private String fileName;
	private int id;
	
	SimpleFileClient(Socket socket, int id) {
		this.socket = socket;
		this.id = id;
	}
	
	SimpleFileClient(Socket socket, String file, int id) {
		this.socket = socket;
		this.fileName = file;
		this.id = id;
	}
	
	public void run() {		
		String fileNameAtClient = this.fileName.substring(0, this.fileName.length()-4)+id+".txt";
		try(BufferedOutputStream toFile = new BufferedOutputStream(new FileOutputStream(fileNameAtClient));
			BufferedWriter toServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
			BufferedInputStream fromServer = new BufferedInputStream(socket.getInputStream());) {
			
			String command = "GET "+fileName+" "+id;
			toServer.write(command+"\n");
			System.out.println("Sende: "+command);
			toServer.flush();
			
			int data = -1;
			long writtenBytes = 0;
			while((data = fromServer.read()) != -1) {
				toFile.write(data);
				writtenBytes++;
			}
			toFile.flush();
			
			if(writtenBytes == 0) {
				System.out.println("Datei-Transaktion fehlgeschlagen, Client "+id+" hat keine Datei vom Server erhalten");
			} else {
				System.out.println("Client mit Id "+id +" meldet erfolgreiche Datei-Transaktion");
			}
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
}