/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pregunta2;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Consumings extends Thread{
	
	
	BufferedReader dis;
	Socket socket;
	PrintWriter dos;
	int id;
        Middleware mid;

	public Consumings(int id,Socket s, BufferedReader dis, PrintWriter dos, Middleware mid) {
		this.socket = s;
		this.dis = dis;
		this.dos = dos;
		this.id = id;
                this.mid=mid;
	}
	
	public void run() {
		
            if (Integer.parseInt(mid.getSize())  != 0) {
                String toClient = "";
                for(String q : mid.queue) {
                    toClient += q + "<-";
                }
                
                dos.println(toClient);
            } else {
                dos.println("Empty Queue");
            } // TODO Auto-generated catch block
		
		
	}
	
	public void sendSMS (String sms) {
            dos.println(sms+"-"); // TODO Auto-generated catch block
	}
	
	
}
