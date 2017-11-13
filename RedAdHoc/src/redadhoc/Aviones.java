/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redadhoc;

/**
 *
 * @author gaona
 */
public class Aviones {
    private String ip;
    private float x;
    private float y;
    private float z;

    public Aviones(){
    
    }
    
    public Aviones(String ip, int x, int y, int z) {
        this.ip = ip;
        this.x  = x;
        this.y  = y;
        this.z  = z;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }
    
    
}
