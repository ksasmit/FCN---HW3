package java_socket;
import java.net.*;
import java.io.*;
public class Client {
	   public static void main(String [] args) // three arguments required here
	   {
	      String serverName = args[0];// ip
	      int port = Integer.parseInt(args[1]); //port
	      try
	      {
	         System.out.println("Connecting to " + serverName +
			 " on port " + port);
	         Socket client = new Socket(serverName, port);
	         System.out.println("Just connected to " 
			 + client.getRemoteSocketAddress());
	         OutputStream outToServer = client.getOutputStream();
	         DataOutputStream out = new DataOutputStream(outToServer);
	         //out.writeUTF("Hello from "+ client.getLocalSocketAddress());
	         Graph.main1(args[2],args[3]);//args[2]  <weight change info>     args[3]  <filename>
	         out.writeUTF(args[2]); // change in weight in the correct format
	         InputStream inFromServer = client.getInputStream();
	         DataInputStream in =
	                        new DataInputStream(inFromServer);
	         System.out.println("Server says " + in.readUTF());
	         //write these to a file weight.txts
	         client.close();
	      }catch(IOException e)
	      {
	         e.printStackTrace();
	      }
	   }
}
