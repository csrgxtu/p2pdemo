/**
 * Author: Archer Reilly
 * Date: 08/Nov/2014
 * File: RelayServer.java
 * Desc: public network relay server, which tell client A IP
 * address and port of client B's public network routers, and
 * vesvas.
 *
 * Produced By CSRGXTU.
 */
import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;

public class RelayServer extends Thread {
    private ServerSocket serverSocket;
  
    public RelayServer (int port) throws IOException {
        serverSocket = new ServerSocket(port);
        //serverSocket.setSoTimeout(10000);
    }

    public void run() {
	    while (true) {
	        try {
		        System.out.println("Waiting for client on port " +
		                   serverSocket.getLocalPort() + "...");
		        Socket server = serverSocket.accept();
				InetSocketAddress srcSockAddr = server.getRemoteSocketAddress();

		        System.out.println("Just connected to " +
		                   server.getRemoteSocketAddress());
		        DataInputStream in =
		            new DataInputStream(server.getInputStream());
		        System.out.println(in.readUTF());
		        DataOutputStream out =
		            new DataOutputStream(server.getOutputStream());
		        out.writeUTF("Thanking you for connecting to  "
		                 + server.getLocalSocketAddress() + "\nGoodybye!");
		        server.close();
		        } catch (SocketTimeoutException e) {
		        System.out.println("Socket time out!");
		        break;
		        } catch (IOException e) {
		        e.printStackTrace();
		        break;
	        }
	    }
    }

    public static void main(String[] args) {
	    int port = Integer.parseInt(args[0]);
	    try {
	        Thread t = new RelayServer(port);
	        t.start();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }

}