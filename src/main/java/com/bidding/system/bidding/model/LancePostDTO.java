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
public class LancePostDTO {
    private float valor;
    private Date dataLance;
    private Long idEdital;
    private Long idUsuario;

    public LancePostDTO() {
    }

    public LancePostDTO(float valor, Date dataLance, Long idEdital, Long idUsuario) {
        this.valor = valor;
        this.dataLance = dataLance;
        this.idEdital = idEdital;
        this.idUsuario = idUsuario;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public Date getDataLance() {
        return dataLance;
    }

    public void setDataLance(Date dataLance) {
        this.dataLance = dataLance;
    }

    public Long getIdEdital() {
        return idEdital;
    }

    public void setIdEdital(Long idEdital) {
        this.idEdital = idEdital;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
          
    
}
