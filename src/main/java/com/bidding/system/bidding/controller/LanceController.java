/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.bidding.controller;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import com.bidding.system.bidding.model.RequestListLanceDTO;
import com.bidding.system.bidding.model.UserDTO;
import com.bidding.system.bidding.service.LanceService;
import com.bidding.system.bidding.service.TokenService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lances")
public class LanceController {
    @Autowired
    private TokenService tokenService;
            
    @Autowired
    private LanceService service;
    
    @GetMapping("/meus-lances")
    public List<RequestListLanceDTO> listarMeusLance(@RequestHeader("Authorization") String auth){
        String token = auth.replace("Bearer ", "");
        UserDTO user = tokenService.extractClaims(token);
        return service.listarMeusLances(user);
    }
}
