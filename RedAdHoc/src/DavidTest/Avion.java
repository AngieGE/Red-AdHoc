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
    
    int zona = 0;
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
                posx = (float) (Math.random() * 1000)   + 29000;
                posy = (float) (Math.random() * 30000)  - 0;
                posz = (float) (Math.random() * 1000)   + 5500;
                break;
            case 2:
                posx = (float) (Math.random() * 1000)   - 0;
                posy = (float) (Math.random() * 30000)  - 0;
                posz = (float) (Math.random() * 1000)   + 5500;
                break;
            case 3:
                posx = (float) (Math.random() * 30000)  - 0;
                posy = (float) (Math.random() * 1000)   - 0;
                posz = (float) (Math.random() * 1000)   + 5500;
                break;
            case 4:
                posx = (float) (Math.random() * 30000)  - 0;
                posy = (float) (Math.random() * 1000)   + 29000;
                posz = (float) (Math.random() * 1000)   + 5500; 
                break;
        }
       
        System.out.println("Primera X: " + posx + " Primera y: " + posy + " Primera Z: " + posz);
        
       if(posx > 29000 && posx < 30000 && posy > 15000 && posy < 30000)
           zona = 1;
       if(posx > 29000 && posx < 30000 && posy > 0 && posy < 15000)
           zona = 2;
       if(posx > 0 && posx < 1000 && posy > 15000 && posy < 30000)
           zona = 3;
       if(posx > 0 && posx < 1000 && posy > 0 && posy < 15000)
           zona = 4;
       if(posx > 1 && posx < 15000 && posy > 0 && posy < 1000)
           zona = 5;
       if(posx > 15000 && posx < 30000 && posy > 0 && posy < 1000)
           zona = 6;
       if(posx > 0 && posx < 15000 && posy > 29000 && posy < 30000)
           zona = 7;
       if(posx > 15000 && posx < 30000 && posy > 29000 && posy < 30000)
           zona = 8;
       
        System.out.println("Zona " + zona);
       
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
        
        
        
        if(zona == 1){
            if(posx>=20500)
                aclx = (float) (Math.pow(velfx, 2) - Math.pow(velx, 2) )/ ( Math.abs(20500-posx) * 2 );
            else{
            System.out.println("Entre else zona1 x");
            aclx = 0;
            velx = 0;
            posx = 20556;
            }
            
            if(posy >= 15000)
                acly = (float) (Math.pow(velfy, 2) - Math.pow(vely, 2) )/ ( Math.abs(15000-posy) * 2 );
                        else{
                System.out.println("Entre else zona1 y");
                acly = 0;
                vely = 0;
                posy = 15000;
            }
        }
        
        if(zona == 2){
            if(posx >= 20500)
                aclx = (float) (Math.pow(velfx, 2) - Math.pow(velx, 2) )/ ( Math.abs(20500-posx) * 2 );
            else{
                System.out.println("Entre else zona2 x");
                aclx = 0;
                velx = 0;
                posx = 20556;
            }
            if(posy <= 15000)
                acly = (float) (Math.pow(velfy, 2) - Math.pow(vely, 2) )/ ( Math.abs(15000-posx) * 2 );
            else{
                System.out.println("Entre else zona2 y");
                acly = 0;
                vely = 0;
                posy = 15000;
            }
        }
        
        if(zona == 3){
            if(posx <= 9500)
                aclx = (float) (Math.pow(velfx, 2) - Math.pow(velx, 2) )/ ( Math.abs(9500-posx) * 2 );
            else{
                System.out.println("Entre else zona3 z");
                aclx = 0;
                velx = 0;
                posx = 5556;
            }
            if(posy >= 15000)
                acly = (float) (Math.pow(velfy, 2) - Math.pow(vely, 2) )/ ( Math.abs(15000-posy) * 2 );
            else{
                System.out.println("Entre else zona31 y");
                acly = 0;
                vely = 0;
                posy = 15000;
            }
        }
        
        if(zona == 4){
            if(posx <= 9500)
                aclx = (float) (Math.pow(velfx, 2) - Math.pow(velx, 2) )/ ( Math.abs(9500 -posx) * 2 );
            else{
                System.out.println("Entre else zona 4 x");
                aclx = 0;
                velx = 0;
                posx = 5556;
            }
            if(posy <= 15000)
                acly = (float) (Math.pow(velfy, 2) - Math.pow(vely, 2) )/ ( Math.abs(15000-posy) * 2 );
            else{
                System.out.println("Entre else zona4 y");
                acly = 0;
                vely = 0;
                posy = 15000;
            }
        }
        
        if(zona == 5){
            if(posx <= 15000)
                aclx = (float) (Math.pow(velfx, 2) - Math.pow(velx, 2) )/ ( Math.abs(15000-posx) * 2 );
            else{
                System.out.println("Entre else zona5 x");
                aclx = 0;
                velx = 0;
                //posx = 15000;
            }
            if(posy <= 9500)
            acly = (float) (Math.pow(velfy, 2) - Math.pow(vely, 2) )/ ( Math.abs(9500-posy) * 2 );
            else{
                System.out.println("Entre else zona5 y");
                acly = 0;
                vely = 0;
                posy = 5556;
            }
        }
        
        if(zona == 6){
            if(posx >= 15000)
                aclx = (float) (Math.pow(velfx, 2) - Math.pow(velx, 2) )/ ( Math.abs(15000-posx) * 2 );
            else{
                System.out.println("Entre else zona6 x");
                aclx = 0;
                velx = 0;
                posx = 15000;
            }
            if(posy <= 9500)
                acly = (float) (Math.pow(velfy, 2) - Math.pow(vely, 2) )/ ( Math.abs(9500-posy) * 2 );
            else{
                System.out.println("Entre else zona6 y");
                acly = 0;
                vely = 0;
                posy = 5556;
            }
        }

        if(zona == 7){
            if(posx <= 15000)
                aclx = (float) (Math.pow(velfx, 2) - Math.pow(velx, 2) )/ ( Math.abs(15000-posx) * 2 );
            else{
                System.out.println("Entre else zona7 x");
                aclx = 0;
                velx = 0;
                posx = 15000;
            }
            if(posy >= 20500)
                acly = (float) (Math.pow(velfy, 2) - Math.pow(vely, 2) )/ ( Math.abs(20500-posy) * 2 );
            else{
                System.out.println("Entre else zona7 y");
                acly = 0;
                vely = 0;
                posy = 20556;
            }
        }
        
        if(zona == 8 ){
            if(posx >= 15000)
                aclx = (float) (Math.pow(velfx, 2) - Math.pow(velx, 2) )/ ( Math.abs(15000-posx) * 2 );
            else{
                System.out.println("Entre else zona8 x ");
                aclx = 0;
                velx = 0;
                posx = 15000;
            }
            if(posy >=20500)
                acly = (float) (Math.pow(velfy, 2) - Math.pow(vely, 2) )/ ( Math.abs(20500-posy) * 2 );
            else{
                System.out.println("Entre else zona8 y");
                acly = 0;
                vely = 0;
                posy = 20556;
            }
        }
        
        //aclx = aclx * -1;
        //acly = acly * -1;
        
    }
    
    public void detener(){
        control = false;
    }
    
    
}
