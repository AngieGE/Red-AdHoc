/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DavidTest;

import java.awt.Graphics;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import java.awt.Dimension;
/**
 *
 * @author gaona
 */
public class ControlAvion extends JPanel{
    static Avion miAvion;
    
    public static void main(String args[]){
        
        miAvion = new Avion();
        //System.out.println("X: " + miAvion.posx + " Y: " + miAvion.posy + " Z: " + miAvion.posz);
        
        miAvion.start();
        
        
        
        //miAvion.detener();
        
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
    }
    
    public Dimension getPreferredSize()
    {
        return new Dimension(500, 500);
    }
    
    public class envioS extends Thread{
        
    }
}
