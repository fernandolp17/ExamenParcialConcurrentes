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
import java.util.*;

public class Middleware {
    static final int NUM_CONNECTIONS=2;
    static final int NUM_QUEUES = 2*NUM_CONNECTIONS;
    public Queue<String>[] queues = new Queue[NUM_QUEUES];
    public String smsSend;

    public Middleware() {
        for (int i = 0; i < NUM_QUEUES; i++) {
            queues[i] = new LinkedList<String>();
        }
        smsSend = "";
    }

    public void addQueue(String sms, int i) {
        queues[i].offer(sms);
    }

    public void pollQueue(int i) {
        queues[i].poll();
    }

    public String getSMS(int i) {

        /*for(String q : queue){
            System.out.println(q);
        }*/
        smsSend = queues[i].element();

        return smsSend;
    }

    public String getSize(int i) {

        return String.valueOf(queues[i].size());
    }
    
    public String factorial(int num){
        double prod=1.0;
        for(int i=1; i<=num;i++){
            prod *= i;
        }
        return Double.toString(prod);
    }
}
