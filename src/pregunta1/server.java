/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pregunta1;

import java.io.*;
import java.net.*;
import java.util.Scanner;

class Server {

    public static void main(String args[]) throws Exception {
        String fromClientSMS;
        String fromClientID;
        String fromClient;
        String toClient;

        Middleware mid = new Middleware();

        ServerSocket server = new ServerSocket(23232);
        System.out.println("wait for connection on port 23232");
        Scanner sc = new Scanner(System.in);

        boolean run = true;
        while (run) {
            Socket client = server.accept();
            System.out.println("got connection on port 8080");

            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);

            fromClient = in.readLine();
            System.out.println("Recibido: " + fromClient);

            if (fromClient.equals("Producing")) {
                String s = "";

                System.out.print("Esperando mensaje: \n");
                //toClient = sc.nextLine();
                toClient = "Esperando tu sms";
                //System.out.println("send olleH");
                out.println(toClient);
                fromClientSMS = in.readLine();
                System.out.println("Recibido: " + fromClientSMS);
                s = fromClientSMS;
                System.out.print("Esperando destino: \n");
                //toClient = sc.nextLine();
                toClient = "Esperando destino sms";
                //System.out.println("send olleH");
                out.println(toClient);
                fromClientID = in.readLine();
                System.out.println("Recibido: " + fromClientID);

                fromClient = fromClientSMS + fromClientID;

                mid.addQueue(fromClient);

            } else if (fromClient.equals("Consuming")) {
                System.out.print("<<Buscando SMS... \n");
                toClient = "Tus SMS no leidos";
                out.println(toClient);

                toClient = mid.getSize();
                out.println(toClient);

                fromClient = in.readLine();
                System.out.println("Recibido: " + fromClient);

                if (fromClient.equals("si")) {

                    for (String q : mid.queue) {
                        toClient = q;
                        out.println(toClient);
                    }
                    mid.queue.clear();
                    System.out.print("<<<<<< >>>>>>> \n");
                }

            } else if (fromClient.equals("Adios")) {

                toClient = "Adios cliente";
                out.println(toClient);
                //out.println(toClient);
                client.close();
                System.out.println("socket cerrado");

            }

            /*if (fromClient.equals("Hello")) {
                toClient = "olleH";
                System.out.println("send olleH");
                out.println(toClient);
                fromClient = in.readLine();
                System.out.println("received: " + fromClient);

                if (fromClient.equals("Bye")) {
                    toClient = "eyB";
                    System.out.println("send eyB");
                    out.println(toClient);
                    client.close();
                    run = false;
                    System.out.println("socket cerrado");
                }
            }*/
        }
        server.close();
    }

}
