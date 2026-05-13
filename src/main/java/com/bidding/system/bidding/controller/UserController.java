/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.bidding.controller;


import com.bidding.system.bidding.model.UserDTO;
import com.bidding.system.bidding.model.UserRequestDTO;
import com.bidding.system.bidding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author gaigu
 */
@RestController
@RequestMapping("/api/auth")
public class UserController {
    
    @Autowired
    private UserService service;
    
    @PostMapping("/register")
    public String register(@RequestBody UserDTO user){
        service.register(user);
        return "Cadastro feito com sucesso.";
    }
    
    @PostMapping("/login")
    public String login(@RequestBody UserRequestDTO user){
        return service.logar(user);
    }
    
}
