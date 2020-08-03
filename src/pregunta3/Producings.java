/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pregunta3;

/**
 *
 * @author PC FERNANDO
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Producings extends Thread {

    BufferedReader dis;
    Socket socket;
    PrintWriter dos;
    Middleware mid;

    public Producings(Socket s, BufferedReader dis, PrintWriter dos, Middleware mid) {
        this.socket = s;
        this.dis = dis;
        this.dos = dos;
        this.mid = mid;
    }

    public void run() {
        
        for(int i=0; i<Middleware.NUM_QUEUES;i++){
            System.out.println("Longitud de cola: " + Integer.parseInt(mid.getSize(i)));
        }
        

        while (true) {

            String fromClientID;
            String fromClientSMS;
            try {

                dos.println("Ingrese ID cola");
                fromClientID = dis.readLine();
                int id = Integer.parseInt(fromClientID);
                System.out.println("Recibido: " + fromClientID);
                
                dos.println("Ingrese SMS");
                fromClientSMS = dis.readLine();
                System.out.println("Recibido: " + fromClientSMS);

                if (fromClientSMS.equalsIgnoreCase("salir")) {
                    this.socket.close();
                    break;
                }

                String sms = fromClientSMS;
                mid.addQueue(sms,id);

                for (Consumings c : Server.consuming) {
                    if(id == c.id){
                        c.sendSMS(sms);
                    }
                }
                
                for(int i=0; i<Middleware.NUM_QUEUES; i++){
                    System.out.println("Longitud de cola: " + mid.getSize(i));
                }
                

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        try {
            this.dis.close();
            this.dos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
