/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pregunta4;

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
    int id;
    Middleware mid;

    public Producings(int id, Socket s, BufferedReader dis, PrintWriter dos, Middleware mid) {
        this.socket = s;
        this.dis = dis;
        this.dos = dos;
        this.id = id;
        this.mid = mid;
    }

    public void run() {

        for (int i = 0; i < Middleware.NUM_QUEUES; i++) {
            System.out.println("Longitud de cola" + i + ": " + Integer.parseInt(mid.getSize(i)));
        }

        while (true) {

            String fromClientID;
            String fromClientSMS;
            String fromClientNUM;
            try {

                dos.println("Ingrese ID cola");
                fromClientID = dis.readLine();
                int id = Integer.parseInt(fromClientID);
                System.out.println("Recibido: " + fromClientID);

                dos.println("Ingrese operacion");
                fromClientSMS = dis.readLine();
                System.out.println("Recibido: " + fromClientSMS);

                if (fromClientSMS.equalsIgnoreCase("salir")) {
                    this.socket.close();
                    break;
                }

                if (fromClientSMS.equals("factorial")) {
                    System.out.print("Esperando numero: \n");

                    dos.println("Ingrese numero");

                    fromClientNUM = dis.readLine();
                    System.out.println("Recibido: " + fromClientSMS);

                    mid.addQueue(fromClientSMS, id - 1);
                    String factorial = mid.factorial(Integer.parseInt(fromClientNUM));
                    mid.addQueue(factorial, id);

                    for (Consumings c : Server.consuming) {
                        if (id-1 == c.id) {
                            c.sendSMS(fromClientNUM);
                        }
                    }

                    for (int i = 0; i < Middleware.NUM_QUEUES; i++) {
                        System.out.println("Longitud de cola " + i + ": " + mid.getSize(i));
                    }
                    dos.println(factorial + "--");
                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {
                this.dis.close();
                this.dos.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
