/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pregunta2;

/**
 *
 * @author PC FERNANDO
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Producings extends Thread{
	
	
	BufferedReader dis;
	Socket socket;
	PrintWriter dos;
        Middleware mid;
        

	public Producings(Socket s, BufferedReader dis, PrintWriter dos, Middleware mid) {
		this.socket = s;
		this.dis = dis;
		this.dos = dos;
                this.mid=mid;
	}
	
	public void run() {
		
		System.out.println("Longitud de cola: "+ Integer.parseInt(mid.getSize()));

		while(true) {
			
			String fromClient;
			try {
				
				fromClient = dis.readLine();
				
				if( fromClient.equalsIgnoreCase("salir") ) {
					this.socket.close();
					break;
				}


				String sms = fromClient;
				mid.addQueue(sms);
				
				
				for( Consumings c : Server.consuming ) {
					c.sendSMS(sms);
				}
				
				System.out.println("Longitud de cola: "+ mid.getSize());
				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
		try{ 
            this.dis.close(); 
            this.dos.close(); 
              
        }catch(IOException e){ 
            e.printStackTrace(); 
        } 
		
		
		
	}
	
	
}
