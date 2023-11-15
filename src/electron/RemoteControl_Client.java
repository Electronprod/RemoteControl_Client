package electron;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.locks.LockSupport;

import org.json.simple.JSONObject;

import electron.utils.logger;
import electron.utils.messages;

public class RemoteControl_Client {

	public static void main(String[] args) {
		String projectname = "";
		logger.log("--------------------------------------------");
		logger.log("Starting "+projectname);
		logger.log("Waiting for IP...");
		String ip = messages.input("Enter IP of server", projectname);
		if(ip == null) {
			logger.error("Incorrect input. Shutting down.");
			System.exit(1);
		}
		logger.log("Waiting for PORT...");
		int port = Integer.parseInt(messages.input("Enter port of server", projectname));
		logger.log("--------------------------------------------");
		logger.log("Connecting to "+ip+":"+port);
		try {
			Socket client = new Socket(ip, port);
			logger.log("Connected to "+client);
			Thread receiver = new Receiver(client);
			receiver.start();
			while(true) {
				OutputStream outToServer;
					Scanner scan = new Scanner(System.in);
					String command = scan.nextLine();
					outToServer = client.getOutputStream();
				    DataOutputStream out = new DataOutputStream(outToServer);
				    JSONObject obj = new JSONObject();
				    obj.put("command", command);
				    out.writeUTF(obj.toJSONString()); 
					if(command.contains("/exit")) {
						client.close();
						receiver.stop();
						System.exit(0);
					}
					LockSupport.parkNanos(100);
			}
		} catch (IOException e) {
			logger.log("--------------------------------------------");
			logger.error("Something went wrong: "+e.getMessage());
			logger.log("Disconnected from server. Bye.");
			logger.log("--------------------------------------------");
			System.exit(1);
		}
	}

}
class Receiver extends Thread{
	Socket client;
	public Receiver(Socket client) {
		this.client=client;
	}
	public void run() {
		while(true) {
			DataInputStream in;
			try {
				in = new DataInputStream(client.getInputStream());
				String data = in.readUTF();
				logger.log("[Received]: "+data);
			} catch (IOException e) {
				LockSupport.parkNanos(50);
//				log.log("--------------------------------------------");
//				log.error("Error receiving data: "+e.getMessage());
//				log.log("Disconnected from server. Bye.");
//				log.log("--------------------------------------------");
//				System.exit(1);
			}
			
		}
	}
}
