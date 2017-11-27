/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DavidTest;


import java.awt.Dimension;
import java.awt.Graphics;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *
 * @author gaona
 */
public class ControlTorre extends JPanel{
    static ArrayList<Avion> aviones = new ArrayList<>();
    static ServerSocket servSocket;
    static Socket fromClientSocket;
    
    static float[] x = new float[4];
    static float[] y = new float[4];
    
    static float[] velx = new float[4];
    static float[] vely = new float[4];
    
    public static void main(String args[]){
        int Port = 12345;
        //Pista 1
        x[0]    =  9500;
        y[0]    =  15000;
        velx[0] =  76;
        vely[0] =  0;
        //Pista 2
        x[1]    = 15000;
        y[1]    = 9500;
        velx[1] = 0;
        vely[1] = 76;
        //Pista 3
        x[2]    = 20500;
        y[2]    = 15000;
        velx[2] = -76;
        velx[2] = 0;
        //Pista 4
        x[3] = 15000;
        y[3] = 20500;
        velx[3] = 0;
        vely[3] = -76;
        
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
                        System.out.println("Llego el avion " + nAvion.ip + "por el puerto: " +  inPacket.getPort());
                        
                        int ind = 0;
                        
                        ind = SearchA(nAvion.getIp());
                        if(ind < 0){
                            float posmin = (float) Math.sqrt( Math.pow(nAvion.posx - x[0] ,2) + Math.pow(nAvion.posy - y[0], 2) );
                            
                            for(int i = 0; i < 4; i++){
                                float posact = (float) Math.sqrt( Math.pow(nAvion.posx - x[i] ,2) + Math.pow(nAvion.posy - y[i], 2) );
                                if(posact < posmin)
                                {
                                    posmin = posact;
                                    nAvion.pista= i;
                                }
                            }
                            aviones.add(nAvion);
                            ind = SearchA(nAvion.getIp());
                        }
                        
                        System.out.println("El avion con el que estoy trabajando es" + aviones.get(ind).ip  + " Y va a aterrizar en " + aviones.get(ind).pista ) ;
                        
                        aviones.get(ind).posx = nAvion.posx;
                        aviones.get(ind).posy = nAvion.posy;
                        
                        aviones.get(ind).velx = nAvion.velx;
                        aviones.get(ind).vely = nAvion.vely;
                        
                        aviones.get(ind).zona = nAvion.zona;
                        
                        aviones.get(ind).CalcularAceleracion(x[aviones.get(ind).pista],y[aviones.get(ind).pista] ,  velx[aviones.get(ind).pista], vely[aviones.get(ind).pista]);
                        
                        System.out.println( "Su aceleracion va a ser X:  " + aviones.get(ind).aclx  );
                        
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        ObjectOutputStream oos = new ObjectOutputStream(outputStream);
                        oos.writeObject( aviones.get(ind) );
                        data = outputStream.toByteArray();
                        DatagramPacket sendPacket = new DatagramPacket(data,data.length, inPacket.getAddress() , inPacket.getPort() );
                        socket.send(sendPacket);
                        System.out.println("Termine de enviar info");;
                        
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
                }
           }
       };
        
       res.start();
       
       // Creacion y visualizacion de la ventana
        JFrame v = new JFrame();
        ControlTorre panel = new ControlTorre();
        v.getContentPane().add(panel);
        v.pack();
        v.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        v.setVisible(true);

        // Bucle para cambiar las coordenadas de los puntos
        while (true)
        {
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
    
    
    //Este metodo se utiliza para busvcar 
    static public int SearchA(String ip){
        for(int i = 0; i < aviones.size(); i++)
        {
            if(ip.equals( aviones.get(i).getIp() ))
                return i;
        }
        
        return -1;
    }
    
    public void paint(Graphics g){
        super.paint(g);
        //g.drawString( Float.toString(miAvion.posx), 20, 20);
        g.drawLine(190, 300, 410, 300);
        g.drawLine(300, 190, 300, 410);
        
        for(int i = 0; i < aviones.size(); i++)
        {
            g.drawRect(aviones.get(i).getIntPosx(), aviones.get(i).getIntPosy(), 5, 5);
        }
        //g.drawRect(miAvion.getIntPosx(), miAvion.getIntPosy(), 5, 5);
    }
    
    public Dimension getPreferredSize()
    {
        return new Dimension(600, 600);
    }
    public static class GetData extends Thread{
        boolean control = true;
        
        public void detener(){
            control = false;
        }
    }
    

}
