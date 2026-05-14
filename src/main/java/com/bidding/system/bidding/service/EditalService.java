/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.bidding.service;

import com.bidding.system.bidding.model.EditalDTO;
import com.bidding.system.bidding.model.RequestListEditalDTO;
import com.bidding.system.bidding.model.UserDTO;
import com.bidding.system.bidding.repository.EditalDAO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EditalService {
    @Autowired
    private EditalDAO repository;
    
    @Autowired
    private TokenService tokenService;
    
    public void createEdital(EditalDTO edital, String token){
        UserDTO userLogado = tokenService.extractClaims(token);
        
        if(userLogado.getRole().equals("COMPRADOR")) {
            String msg = "";
        
            if (edital.getTitulo().equals("")){
                msg += "Titulo não preenchido./n";
            } 
            if (edital.getDescricao().equals("")){
                msg += "Decrição não preenchida./n";
            }
            if (edital.getDataFechamento() == null){
                msg += "Data não preenchida.";
            }

            if(!msg.equals("")){
                throw new ResponseStatusException(HttpStatusCode.valueOf(400), msg);
            }
            
            edital.setStatus("ABERTO");
            int linhas = repository.create(edital);
            if (linhas == 0){
                throw new ResponseStatusException(HttpStatusCode.valueOf(500), "Erro ao cadastrar no banco de dados.");
            } 
        }else{
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Acesso não autorizado");
        }  
    }
    
    public List<RequestListEditalDTO> listEditais(String token){
        return repository.listEdital();
    }
}
