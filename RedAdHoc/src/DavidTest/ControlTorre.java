/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DavidTest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gaona
 */
public class ControlTorre {
    static ArrayList<Avion> aviones = new ArrayList<>();
    static ServerSocket servSocket;
    static Socket fromClientSocket;
    
    public static void main(String args[]){
        int Port = 12345;
        
        try {
            servSocket = new ServerSocket(Port);
        } catch (IOException ex) {
            Logger.getLogger(ControlTorre.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        GetData res = new GetData(){
           @Override
           public void run(){
                int port = 12345;
                while(control){
                    
                    try {
                        DatagramSocket socket = new DatagramSocket(port);
                        byte[] incomingData = new byte[1024];
                        DatagramPacket inPacket = new DatagramPacket(incomingData,incomingData.length);
                        socket.receive(inPacket);
                        byte[] data = inPacket.getData();
                        ByteArrayInputStream in  = new ByteArrayInputStream(data);
                        ObjectInputStream is = new ObjectInputStream(in);
                        Avion nAvion = (Avion) is.readObject();
                        System.out.println("Llego el avion " + nAvion.ip);
                        is.close();
                        in.close();
                        socket.close();
                    } catch (SocketException ex) {
                        Logger.getLogger(ControlTorre.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(ControlTorre.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ControlTorre.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ControlTorre.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
           }
       };
        
       res.start();
        
    }
    
    public static class GetData extends Thread{
        boolean control = true;
        
        public void detener(){
            control = false;
        }
    }
}
