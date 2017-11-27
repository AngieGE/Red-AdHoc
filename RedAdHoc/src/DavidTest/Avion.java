/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DavidTest;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gaona
 */
public class Avion extends Thread implements Serializable{
    
    String ip;
    
    /**
     * Posicion actual del avion
     */
    float posx;
    float posy;
    float posz;
    
    /**
     * Velocidad actual del avion
     */
    float velx;
    float vely;
    float velz;
    
    /**
     * Aceleracion Actual
     */
    float aclx;
    float acly;
    float aclz;
    
    /**
     * Control de operacion
     */
    boolean control;
    
    public Avion(){
        int zona = (int) (Math.random() * 8 ) + 1;
        switch(zona){
            case 1:
                posx = (float) (Math.random() * 1000)   + 14500;
                posy = (float) (Math.random() * 15000)  - 15000;
                posz = (float) (Math.random() * 2000)   + 5000;
                velx = (float) (Math.random() * 22) - 122;
                vely = (float) (Math.random() * 22) + 100;
                velz = 0;
                break;
            case 2:
                posx = (float) (Math.random() * 1000)   + 14500;
                posy = (float) (Math.random() * 15000)  - 15000;
                posz = (float) (Math.random() * 2000)   + 5000;
                velx = (float) (Math.random() * 22) - 122;
                vely = (float) (Math.random() * 22) - 122;
                velz = 0;
                break;
            case 3:
                posx = (float) (Math.random() * 1000)   - 15500;
                posy = (float) (Math.random() * 15500)  - 15500;
                posz = (float) (Math.random() * 2000)   + 5000;
                velx = (float) (Math.random() * 22) + 100;
                vely = (float) (Math.random() * 22) + 100;
                velz = 0;
                break;
            case 4:
                posx = (float) (Math.random() * 1000)  - 15500;
                posy = (float) (Math.random() * 15500)  - 0;
                posz = (float) (Math.random() * 2000)   + 5000; 
                velx = (float) (Math.random() * 22) + 100;
                vely = (float) (Math.random() * 22) - 122;
                velz = 0;
                break;
            case 5:
                posx = (float) (Math.random() * 15500)  - 15500;
                posy = (float) (Math.random() * 1000)   + 14500;
                posz = (float) (Math.random() * 2000)   + 5000;
                velx = (float) (Math.random() * 22) + 100;
                vely = (float) (Math.random() * 22) - 122;
                velz = 0;
                break;
            case 6:
                posx = (float) (Math.random() * 15500)  - 0;
                posy = (float) (Math.random() * 1000)   + 14500;
                posz = (float) (Math.random() * 2000)   + 5000;
                velx = (float) (Math.random() * 22) - 122;
                vely = (float) (Math.random() * 22) - 122;
                velz = 0;
                break;
            case 7:
                posx = (float) (Math.random() * 15500)  - 15500;
                posy = (float) (Math.random() * 1000)   - 15500;
                posz = (float) (Math.random() * 2000)   + 5000;
                velx = (float) (Math.random() * 22) + 100;
                vely = (float) (Math.random() * 22) + 100;
                velz = 0;
                break;
            case 8:
                posx = (float) (Math.random() * 15500)  - 0;
                posy = (float) (Math.random() * 1000)   + 14500;
                posz = (float) (Math.random() * 2000)   + 5000;
                velx = (float) (Math.random() * 22) - 122;
                vely = (float) (Math.random() * 22) + 100;
                velz = 0;
                break;
        }
        
        aclx = 0;
        acly = 0;
        aclx = 0;
        
        ip = "Soy el avion numero 1";
        
        control = true;
    }

    public Avion(float posx, float posy, float posz, float velx, float vely, float velz) {
        //this.ip = ip;
        this.posx = posx;
        this.posy = posy;
        this.posz = posz;
        this.velx = velx;
        this.vely = vely;
        this.velz = velz;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public float getPosx() {
        return posx;
    }

    public void setPosx(float posx) {
        this.posx = posx;
    }

    public float getPosy() {
        return posy;
    }

    public void setPosy(float posy) {
        this.posy = posy;
    }

    public float getPosz() {
        return posz;
    }

    public void setPosz(float posz) {
        this.posz = posz;
    }

    public float getVelx() {
        return velx;
    }

    public void setVelx(float velx) {
        this.velx = velx;
    }

    public float getVely() {
        return vely;
    }

    public void setVely(float vely) {
        this.vely = vely;
    }

    public float getVelz() {
        return velz;
    }

    public void setVelz(float velz) {
        this.velz = velz;
    }
    
    public void setAcl(float aclx, float acly, float aclz){
        this.aclx = aclx;
        this.acly = acly;
        this.aclz = aclz;
    }
    
    @Override
    public void run(){
        while(control){
            //Se calcula la nueva posicion
            posx = posx + (velx * 1) + ((aclx * 1)/2);
            posy = posy + (vely * 1) + ((acly * 1)/2);
            posz = posz + (velz * 1) + ((aclz * 1)/2);

            //se calcula la nueva velocida
            velx = velx + (aclx * 1);
            vely = vely + (acly * 1);
            velz = velz + (aclz * 1);
            
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    
    
    public void detener(){
        control = false;
    }
    
    
}
