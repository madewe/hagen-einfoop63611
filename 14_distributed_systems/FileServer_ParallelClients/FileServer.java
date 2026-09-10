import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer {

	public final static int SERVER_PORT = 7891;
	
	public static void main(String[] args) throws IOException {
		
		try(ServerSocket serversocket = new ServerSocket(SERVER_PORT)) {
			System.out.println("Server bereit an Port "+SERVER_PORT);
			while(true) {
					Socket socket = serversocket.accept();
					Thread serviceThread = new ServerServiceThread(socket);
					serviceThread.start();
			}
		} catch(IOException e) {
			System.out.println(e.getMessage());
		} 
	}
}