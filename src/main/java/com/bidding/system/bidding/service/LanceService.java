/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.bidding.service;

import com.bidding.system.bidding.model.RequestListLanceDTO;
import com.bidding.system.bidding.model.UserDTO;
import com.bidding.system.bidding.repository.LanceDAO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LanceService {
    @Autowired
    private LanceDAO repository;
    
    @Autowired
    private TokenService tokenService;
    
    public List<RequestListLanceDTO> listarMeusLances(UserDTO user){
        return repository.listarLances(user.getId());
    }
}
