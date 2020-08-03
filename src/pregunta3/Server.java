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
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.Vector;

class Server {

    static Vector<Consumings> consuming = new Vector<Consumings>();
    static Vector<Producings> producing = new Vector<Producings>();

    public static void main(String args[]) throws Exception {
        String fromClientSMS;
        String fromClientID;
        String fromClient;
        String toClient;

        Middleware mid = new Middleware();
        //mid.createQueues();

        ServerSocket server = new ServerSocket(23232);
        System.out.println("Esperando conexion!!!!");
        Scanner sc = new Scanner(System.in);

        boolean run = true;
        while (run) {
            Socket client = server.accept();
            System.out.println("Conexion establecida!!!");

            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);

            fromClient = in.readLine();
            System.out.println("Recibido: " + fromClient);

            if (fromClient.equals("Producing")) {
                System.out.print("Esperando ID cola: \n");
                //toClient = sc.nextLine();
                toClient = "Ingrese ID cola";
                //System.out.println("send olleH");
                out.println(toClient);

                fromClientID = in.readLine();
                int id = Integer.parseInt(fromClientID);
                System.out.println("Recibido: " + fromClientID);

                System.out.print("Esperando sms: \n");
                //toClient = sc.nextLine();
                toClient = "Ingrese SMS";
                out.println(toClient);

                fromClientSMS = in.readLine();
                System.out.println("Recibido: " + fromClientSMS);

                //fromClient = fromClientSMS + fromClientID;
                mid.addQueue(fromClientSMS, id);

                for (Consumings c : Server.consuming) {
                    if (id == c.id) {
                        c.sendSMS(fromClientSMS);
                    }
                }

                Producings n = new Producings(client, in, out, mid);
                Thread t = new Thread(n);
                producing.add(n);
                t.start();

            } else if (fromClient.startsWith("Consuming")) {

                int pos1 = fromClient.indexOf(':');

                int id = Integer.parseInt(fromClient.substring(10, fromClient.length()));

                for (Consumings c : Server.consuming) {
                    if (c.id == id) {
                        Server.consuming.remove(c);
                        break;
                    }
                }

                Consumings n = new Consumings(id, client, in, out, mid);
                Thread t = new Thread(n);
                consuming.add(n);
                t.start();

            } else if (fromClient.equals("Adios")) {

                toClient = "Adios cliente";
                out.println(toClient);
                //out.println(toClient);
                client.close();
                System.out.println("socket cerrado");

            }
        }
        server.close();
    }

}
