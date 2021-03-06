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
import java.util.ArrayList;

/**
 *
 * @author gaona
 */
public class Torre extends javax.swing.JFrame {
    
    static ArrayList<Aviones> aviones = new ArrayList<Aviones>();
    static int x = 0;
    
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
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 623, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 387, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        
    }//GEN-LAST:event_formWindowActivated

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:

    }//GEN-LAST:event_formWindowOpened

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
        
        
        while (true){
            conexion();
        }
    }
    
    
    public static void conexion(){
        
        
        System.out.println("Soy el Servidor");

        try {

          DatagramSocket socketUDP = new DatagramSocket(6789);
          byte[] bufer = new byte[1000];

            // Construimos el DatagramPacket para recibir peticiones
            DatagramPacket peticion = new DatagramPacket(bufer, bufer.length);

            // Leemos una petici�n del DatagramSocket
            socketUDP.receive(peticion);

            System.out.print("Datagrama recibido del host: " + peticion.getAddress());
            System.out.println(" desde el puerto remoto: " + peticion.getPort() );

            System.out.println("Recibí del cliente: " + new String(peticion.getData()) );

            String resultado;
            int  i = searchAvion( String.valueOf(peticion.getAddress()) );
            //Separamos la repuesta en un arreglo
            String[] res = new String(peticion.getData()).split(" ");
            
            if( i < 0 ){
                aviones.add(new Aviones( String.valueOf(peticion.getAddress()), Float.parseFloat(res[0]),Float.parseFloat(res[1]), Float.parseFloat(res[2])) );
                aviones.get(aviones.size() - 1).doPhysics();
                if(aviones.get(aviones.size() - 1).getY() < 300 ){
                    resultado = "Llegaste";
                }else{
                    resultado = aviones.get(aviones.size() - 1).getX() + " " + aviones.get(aviones.size() - 1).getY() + " " + aviones.get(aviones.size() - 1).getZ() ;
                }
            }else{
                aviones.get(i).setX(Float.parseFloat(res[0]));
                aviones.get(i).setY(Float.parseFloat(res[1]));
                aviones.get(i).setZ(Float.parseFloat(res[2]));
                aviones.get(i).doPhysics();
                if(aviones.get(i).getY() < 300 ){
                    resultado = "Llegaste";
                }else{
                    resultado = aviones.get(i).getX() + " " + aviones.get(i).getY() + " " + aviones.get(i).getZ() ;
                }
            }

            byte[] mensaje = resultado.getBytes();

            // Construimos el DatagramPacket para enviar la respuesta
            DatagramPacket respuesta = new DatagramPacket(mensaje, resultado.length(), peticion.getAddress(), peticion.getPort());

            // Enviamos la respuesta, que es un eco
            socketUDP.send(respuesta);
            
            socketUDP.close();
        } catch (SocketException e) {
          System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
          System.out.println("IO: " + e.getMessage());
        }
    
    }
    
    public static int searchAvion(String ip){
        for(int i = 0;i < aviones.size(); i++ ){
            if( ip.equals(aviones.get(i).getIp() ) )
                return i; 
        }
        return -1;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
