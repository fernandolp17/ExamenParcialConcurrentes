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
                int id = Integer.parseInt(fromClientID)*2;
                System.out.println("Recibido: " + fromClientID);

                System.out.print("Esperando numero: \n");
                //toClient = sc.nextLine();
                toClient = "Ingrese operacion";
                out.println(toClient);

                fromClientSMS = in.readLine();
                System.out.println("Recibido: " + fromClientSMS);

                if (fromClientSMS.equals("factorial")) {
                    System.out.print("Esperando numero: \n");
                    //toClient = sc.nextLine();
                    toClient = "Ingrese numero";
                    out.println(toClient);

                    fromClientSMS = in.readLine();
                    System.out.println("Recibido: " + fromClientSMS);

                    mid.addQueue(fromClientSMS, id);
                    String factorial = mid.factorial(Integer.parseInt(fromClientSMS));
                    mid.addQueue(factorial, id + 1);

                    for (Consumings c : Server.consuming) {
                        if (id == c.id) {
                            c.sendSMS(fromClientSMS);
                        }
                    }
                }
                
                if(Integer.parseInt(mid.getSize(id+1)) != 0 ){
                    toClient="";
                    for(String item: mid.queues[id+1]){
                        toClient += item + "--";
                    }
                    out.println(toClient);
                }else{
                    out.println("Empty Queue");
                }

                Producings n = new Producings(id+1,client, in, out, mid);
                Thread t = new Thread(n);
                producing.add(n);
                t.start();

            } else if (fromClient.startsWith("Consuming")) {

                int pos1 = fromClient.indexOf(':');

                int id = Integer.parseInt(fromClient.substring(10, fromClient.length()));

                Consumings n = new Consumings(id+1, client, in, out, mid);
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
