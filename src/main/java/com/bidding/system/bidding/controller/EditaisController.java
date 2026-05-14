/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.bidding.controller;

import com.bidding.system.bidding.model.EditalDTO;
import com.bidding.system.bidding.model.RequestListEditalDTO;
import com.bidding.system.bidding.service.EditalService;
import com.bidding.system.bidding.service.TokenService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/editais")
public class EditaisController {
    @Autowired
    private EditalService service;
    
    @PostMapping
    public String registrerEdital(@RequestHeader("Authorization") String auth, @RequestBody EditalDTO edital){
        String token = auth.replace("Bearer ", "");
        
        service.createEdital(edital, token);
        
        return "Edital Cadastrado com sucesso.";
    }
    
    @GetMapping
    public List<RequestListEditalDTO> listEdital(@RequestHeader("Authorization") String auth){
        String token = auth.replace("Bearer ", "");
        return service.listEditais(token);
    }
}
