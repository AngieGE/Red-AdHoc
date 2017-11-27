/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DavidTest;

import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import java.awt.Dimension;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gaona
 */
public class ControlAvion extends JPanel{
    static Avion miAvion;
    static sender emisor;
    static int port;
    
    public static void main(String args[]){
        port = 12345;

        miAvion = new Avion(){};
        //System.out.println("X: " + miAvion.posx + " Y: " + miAvion.posy + " Z: " + miAvion.posz);
        
        miAvion.start();
        //miAvion.detener();
        
        emisor= new sender(){
            @Override
            public void run(){
                int port = 12345;
                while(control){
                    try {
                        DatagramSocket socket = new DatagramSocket();
                        InetAddress server = InetAddress.getByName("192.168.137.1");
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        ObjectOutputStream oos = new ObjectOutputStream(outputStream);
                        miAvion.setIp(server.getHostAddress());
                        oos.writeObject(miAvion);
                        byte[] data = outputStream.toByteArray();
                        DatagramPacket sendPacket = new DatagramPacket(data,data.length,server,port);
                        socket.send(sendPacket);
                        System.out.println("Message sent from client");
                        
                        byte[] incomingData = new byte[1024];
                        DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
                        System.out.println("Recibiendo");
                        socket.receive(incomingPacket);
                        byte[] nData = incomingPacket.getData();
                        ByteArrayInputStream in = new ByteArrayInputStream(nData);
                        ObjectInputStream is = new ObjectInputStream(in);
                        Avion ninfo = (Avion) is.readObject();
                        //System.out.println("Lectura completada");
                        
                        //System.out.println("Informacion recibida mi nueva aceleracion sera X: " +nAvion.aclx + " Y: " + nAvion.acly );
                        miAvion.aclx = ninfo.aclx;
                        miAvion.acly = ninfo.acly;
                        
                        miAvion.velx = ninfo.velx;
                        miAvion.vely = ninfo.vely;
                        
                        miAvion.posx = ninfo.posx;
                        miAvion.posy = ninfo.posy;
                        
                        oos.close();
                        outputStream.close();
                        socket.close();
                    } catch (UnknownHostException ex) {
                        Logger.getLogger(ControlAvion.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(ControlAvion.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ControlAvion.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ControlAvion.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        
        emisor.start();
        
        
        // Creacion y visualizacion de la ventana
        JFrame v = new JFrame();
        ControlAvion panel = new ControlAvion();
        v.getContentPane().add(panel);
        v.pack();
        v.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        v.setVisible(true);

        // Bucle para cambiar las coordenadas de los puntos
        while (true)
        {
            System.out.println("X: " + miAvion.posx + " Y: " + miAvion.posy + " Z: " + miAvion.posz);
            // Se provoca el repintado del panel.
            // La llamada a repaint() hará que java llame al metodo
            // paint() que hemos redefinido.
            panel.repaint();

            // Retardo de un segundo (1000 milisegundos)
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    public void paint(Graphics g){
        super.paint(g);
        g.drawString( Float.toString(miAvion.posx), 20, 20);
        g.drawLine(190, 300, 410, 300);
        g.drawLine(300, 190, 300, 410);
        g.drawRect(miAvion.getIntPosx(), miAvion.getIntPosy(), 5, 5);
    }
    
    @Override
    public Dimension getPreferredSize()
    {
        super.getPreferredSize();
        return new Dimension(600, 600);
    }
    
    public static class sender extends Thread{
        boolean control = true;
        public void detener(){
            control = false;
        }
    }

}
