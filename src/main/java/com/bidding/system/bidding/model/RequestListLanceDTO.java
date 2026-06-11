/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.bidding.model;

import java.sql.Date;

/**
 *
 * @author gaigu
 */
public class RequestListLanceDTO {
    private String tituloLance;
    private float valorLance;
    private String statusEdital;
    private boolean vencedor;

    public RequestListLanceDTO() {
    }

    public RequestListLanceDTO(String tituloLance, float valorLance, String statusEdital, boolean vencedor) {
        this.tituloLance = tituloLance;
        this.valorLance = valorLance;
        this.statusEdital = statusEdital;
        this.vencedor = vencedor;
    }

    public String getTituloLance() {
        return tituloLance;
    }

    public void setTituloLance(String tituloLance) {
        this.tituloLance = tituloLance;
    }

    public float getValorLance() {
        return valorLance;
    }

    public void setValorLance(float valorLance) {
        this.valorLance = valorLance;
    }

    public String getStatusEdital() {
        return statusEdital;
    }

    public void setStatusEdital(String statusEdital) {
        this.statusEdital = statusEdital;
    }

    public boolean isVencedor() {
        return vencedor;
    }

    public void setVencedor(boolean vencedor) {
        this.vencedor = vencedor;
    }
    
    
}
