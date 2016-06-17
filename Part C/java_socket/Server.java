package java_socket;
import java.net.*;
import java.util.Scanner;
import java.io.*;
public class Server extends Thread {
	   private ServerSocket serverSocket;
	   static String filename;
	   static String next_hop_ip;
	   static int next_hop_port;
	   public Server(int port) throws IOException
	   {
	      serverSocket = new ServerSocket(port);
	      serverSocket.setSoTimeout(10000000);
	   }

	   public void run()
	   {
	      while(true)
	      {
	         try
	         {
	            System.out.println("Waiting for client on port " +
	            serverSocket.getLocalPort() + "...");
	            Socket server = serverSocket.accept();
	            System.out.println("Just connected to "
	                  + server.getRemoteSocketAddress());
	            DataInputStream in =
	                  new DataInputStream(server.getInputStream());
	            System.out.println(in.readUTF());
	            Graph.main1((String)in.readUTF(),filename); //calling bellman ford algo
	            //read the entire file and send it as string to the next hop
	            
	            //Sever to new client logic here
	            Socket client = new Socket(next_hop_ip, next_hop_port);
	            String content = new Scanner(new File(filename)).useDelimiter("\\Z").next();
	            
	            OutputStream outToServer = client.getOutputStream();
		         DataOutputStream out = new DataOutputStream(outToServer);
		         out.writeUTF(content);
		         InputStream inFromServer = client.getInputStream();
		         DataInputStream in1 =
		                        new DataInputStream(inFromServer);
		         System.out.println("Server says " + in1.readUTF());
		         client.close();
	            
	            
	            
	            
	            
	            
	            
	            
	            DataOutputStream out1 = new DataOutputStream(server.getOutputStream());
	            out1.writeUTF("Thank you for connecting to "
	              + server.getLocalSocketAddress() + "\nGoodbye!");
	            server.close();
	         }catch(SocketTimeoutException s)
	         {
	            System.out.println("Socket timed out!");
	            break;
	         }catch(IOException e)
	         {
	            e.printStackTrace();
	            break;
	         }
	      }
	   }
	   public static void main(String [] args)
	   {
	      int port = Integer.parseInt(args[0]); //port number args[2] =next hop ip args[3] = next hop port
	      next_hop_ip = args[2];
	      next_hop_port = Integer.parseInt(args[3]);
	      filename = args[1]; //change in weight
 	      try
	      {
	         Thread t = new Server(port);
	         t.start();
	      }catch(IOException e)
	      {
	         e.printStackTrace();
	      }
	   }
}
