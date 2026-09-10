import java.util.Random;
import java.net.Socket;
import java.io.IOException;

public class ParallelClients {
	
	private static Random random = new Random();
	private final static int SERVER_PORT = 7891;
	
	public static void main(String[] args) throws IOException {
		if(args.length != 2) {
			System.out.println("Fatal error! 2 arguments needed (host and filename)!");
			System.exit(0);
		}
		String host = args[0];
		String fileNameAtServer = args[1];
		int numberOfClients = random.nextInt(10)+1;
		
		for(int i = 0; i < numberOfClients; i++) {
			new SimpleFileClient(new Socket(host, SERVER_PORT), fileNameAtServer, i+1).start();
		}
	}
}