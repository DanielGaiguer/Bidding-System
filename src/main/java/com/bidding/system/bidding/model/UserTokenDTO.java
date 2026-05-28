/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.bidding.model;


public class UserTokenDTO extends UserDTO {

    private String token;

    public UserTokenDTO(UserDTO user){
        this.setId(user.getId());
        this.setNome(user.getNome());
        this.setEmail(user.getEmail());
        this.setSenha(user.getSenha());
        this.setRole(user.getRole());
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
