import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class MainServerAR {

	/**
	 * @param args
	 */
	static String client_ip_1="";
	static String client_ip_2="";
	static String last_client="";
	static boolean client_1_turn=true;
	
	public static void main(String args[]) throws Exception
	{
        while(true)
        {
        	System.out.println("Esperando mensaje.");
        	String client_msg=receiveFromClient();
        	System.out.println("Recibi: "+client_msg);
        	if(client_msg.equals("touch"))
        	{
        		client_1_turn=!client_1_turn;
        	}
        	
        	if(client_1_turn)
        	{
        		sendToClient("1",last_client);
        	}else
        	{
        		sendToClient("2",last_client);
        	}
        }
	 }
	
	static String receiveFromClient()
	{
		String msg = "";
		try
		{
	        DatagramSocket serverSocket = new DatagramSocket(9876);
	        byte[] receiveData = new byte[1024];

	        DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
	        serverSocket.receive(receivePacket);
	        msg = new String(receivePacket.getData(),0,receivePacket.getLength());
	        serverSocket.close();
	        
	        last_client=receivePacket.getAddress().getHostAddress();
	        
	        if(client_ip_1=="")
	        {
	        	client_ip_1=last_client;
	        	System.out.println("111111a111");
	        }else if(client_ip_2=="" && !client_ip_1.equals(last_client))
	        {
	        	client_ip_2=last_client;
	        	System.out.println("22222222b2222");
	        }
//	        InetAddress IPAddress = receivePacket.getAddress();
//	        int port = receivePacket.getPort();
//	        serverSocket.close();
	        //System.out.println("MESSAGE RECEIVED  "+sentence+"  "+IPAddress+"         "+port);
		}catch(Exception e)
		{
			
		}
		return msg;
	}
	
	static void sendToClient(String mensaje, String ip)
	{
		try
		{
	        String messageStr = mensaje;
			int msg_length = messageStr.length();
			byte[] message = messageStr.getBytes();
			InetAddress local = InetAddress.getByName(ip);
			DatagramPacket p = new DatagramPacket(message, msg_length, local,9876);
			DatagramSocket s = new DatagramSocket();
			s.send(p);
			s.close();
		}catch(Exception e)
		{
			
		}
	}	
}
