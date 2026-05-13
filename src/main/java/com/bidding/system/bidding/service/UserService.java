/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.bidding.service;

import com.bidding.system.bidding.model.UserDTO;
import com.bidding.system.bidding.model.UserRequestDTO;
import com.bidding.system.bidding.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
    @Autowired
    private UserDAO repository;
    
    @Autowired
    private TokenService tokenService;
    
    public void register(UserDTO user){
        String msg = "";
        if (user.getNome().equals("")){
            msg = "Nome não preenchido";
        }else if (user.getEmail().equals("")){
            msg = "Email não preenchido";
        }else if (user.getSenha().equals("")){
            msg = "Senha não preenhcida";
        }else if (user.getRole().equals("")){
            user.setRole("FORNECEDOR");
        }
        
        if (!msg.equals("")){
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), msg);
        }
        
        repository.register(user);
    }
    
    public String logar(UserRequestDTO user){
        String msg = "";
        
        if (user.getEmail().equals("")){
            msg = "Email não preenchido";
        }else if (user.getSenha().equals("")){
            msg = "Senha não preenhcida";
        }
        
        if (!msg.equals("")){
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), msg);
        }
        
        UserDTO dataLogger = repository.login(user.getEmail(), user.getSenha());
        return tokenService.generateToken(dataLogger);
    }
}
