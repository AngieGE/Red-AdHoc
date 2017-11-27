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
    
    String  ip;
    int pista;
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
        int zona = (int) (Math.random() * 4 ) + 1;
        switch(zona){
            case 1:
                posx = (float) (Math.random() * 1000)   + 29500;
                posy = (float) (Math.random() * 30000)  - 0;
                posz = (float) (Math.random() * 1000)   + 5500;
                break;
            case 2:
                posx = (float) (Math.random() * 1000)   - 500;
                posy = (float) (Math.random() * 30000)  - 0;
                posz = (float) (Math.random() * 1000)   + 5500;
                break;
            case 3:
                posx = (float) (Math.random() * 30000)  - 0;
                posy = (float) (Math.random() * 1000)   - 500;
                posz = (float) (Math.random() * 1000)   + 5500;
                break;
            case 4:
                posx = (float) (Math.random() * 30000)  - 0;
                posy = (float) (Math.random() * 1000)   + 29500;
                posz = (float) (Math.random() * 1000)   + 5500; 
                break;
        }
        
        
       float auxx = posx - 15000;
       float auxy = posy - 15000;
        
        velx = (float) ( (Math.random() * 22) + 100 ) * (auxx/Math.abs(auxx) * -1);
        vely = (float) ( (Math.random() * 22) + 100 ) * (auxy/Math.abs(auxy) * -1);
        velz =  - 95;
        aclx = 0;
        acly = 0;
       
        pista = -1;
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
    
    public int getIntPosx(){
        return (int) (posx * 600)/30000; 
    }

    public void setPosx(float posx) {
        this.posx = posx;
    }

    public float getPosy() {
        return posy;
    }
    
    public int getIntPosy(){
        return (int) (posy * 600)/30000;
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
            
            if(posz < 300)
                velz = 0;
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            
            if(posx > 13000 && posx <18000 && posy > 13000 && posy < 18000)
                detener();
        }
    }
    
    public void CalcularAceleracion(float posfx, float posfy, float velfx, float velfy){
        
        float distx = posfx-posx;
        float disty = posfx-posy;
        
        
        if(distx == 0){
            aclx = 0;
            velx = 0;
        }
        else
            aclx = (float) (Math.pow(velfx, 2) - Math.pow(velx, 2) )/ ( distx * 2 );
        if(disty == 0){
            acly = 0;
            vely = 0;
        }else
            acly = (float) (Math.pow(velfy, 2) - Math.pow(vely, 2) )/ ( disty * 2 );
    
    }
    
    public void detener(){
        control = false;
    }
    
    
}
