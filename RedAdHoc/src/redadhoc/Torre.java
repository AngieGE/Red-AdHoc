/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redadhoc;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 *
 * @author gaona
 */
public class Torre extends javax.swing.JFrame {

    /**
     * Creates new form Torre
     */
    public Torre() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Torre.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Torre.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Torre.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Torre.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Torre().setVisible(true);
            }
        });
        
        comunicacion();
    }
    
    
    public static void comunicacion(){
        System.out.println("Soy el Servidor");

        try {

          DatagramSocket socketUDP = new DatagramSocket(6789);
          byte[] bufer = new byte[1000];

            // Construimos el DatagramPacket para recibir peticiones
            DatagramPacket peticion =
              new DatagramPacket(bufer, bufer.length);

            // Leemos una petici�n del DatagramSocket
            socketUDP.receive(peticion);

            System.out.print("Datagrama recibido del host: " +
                               peticion.getAddress());
            System.out.println(" desde el puerto remoto: " +
                               peticion.getPort());

            System.out.println("Recibí del cliente: " + new String(peticion.getData()));

            //Separamos la repuesta en un arreglo
            String[] res = new String(peticion.getData()).split(" ");

            //System.out.println(res[2]);
            /*
            a = Integer.parseInt(res[0]);
            b = Integer.parseInt(res[1]);
            c = Integer.parseInt(res[2].trim());
            */

            //Hacemos la chicharronera
            //System.out.println(Math.sqrt(Math.pow(b, 2) - 4*a*c));
            /*
            x1 = (-b + Math.sqrt(Math.pow(b, 2) - 4*a*c))/(2*a);
            x2 = (-b - Math.sqrt(Math.pow(b, 2) - 4*a*c))/(2*a);
            */
            //System.out.println("Error Aquí");

            String resultado = "Enterado";
            byte[] mensaje = resultado.getBytes();

            // Construimos el DatagramPacket para enviar la respuesta
            DatagramPacket respuesta =
              new DatagramPacket(mensaje, resultado.length(),
                                 peticion.getAddress(), peticion.getPort());

            // Enviamos la respuesta, que es un eco
            socketUDP.send(respuesta);

        } catch (SocketException e) {
          System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
          System.out.println("IO: " + e.getMessage());
        }
    
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
