package pregunta2;

import java.util.*;

public class Middleware {
    
    public Queue<String> queue;
    public String smsSend;
    
    public Middleware(){
        queue = new LinkedList<String>();
        smsSend="";
    }
    
    public void addQueue(String sms){
        queue.offer(sms);
    }
    
    public void pollQueue(){
        queue.poll();
    }
    
    public String getSMS(){
        
        /*for(String q : queue){
            System.out.println(q);
        }*/
        smsSend=queue.element();
        
        return smsSend;
    }
    public String getSize(){
        
        return String.valueOf(queue.size());
    }
}
