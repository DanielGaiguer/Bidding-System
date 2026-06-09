/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.bidding.service;

import com.bidding.system.bidding.model.EditalDTO;
import com.bidding.system.bidding.model.LancePostDTO;
import com.bidding.system.bidding.model.RequestListEditalDTO;
import com.bidding.system.bidding.model.UserDTO;
import com.bidding.system.bidding.repository.EditalDAO;
import java.time.LocalDate;
import java.util.Date;
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
        if (tokenService.validToken(token)){
            return repository.listEdital();
        }else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Necessario logar conta valida");
        }
    }

    public void registerLance(String token, LancePostDTO lance){
        UserDTO user = tokenService.extractClaims(token);

        if(user.getRole().equals("FORNECEDOR")) {
            EditalDTO edital = repository.getById(lance.getIdEdital());
            String msg = "";

            if (lance.getValor() == 0){
                msg += "Valor não informado.\n";
            }

            if (lance.getIdEdital() == 0){
                msg += "Id do edital nao informado.\n";
            }

            if(repository.editalEncerrado(lance.getIdEdital())){
                msg += "Este edital já esta encerrado.\n";
            }

            if(!edital.getStatus().equals("ABERTO")){
                msg += "O Edital não esta mais aberto.\n";
            }

            if (lance.getDataLance().before(new Date())){
                msg += "Data inválida.\n";
            }

            if(edital.getDataFechamento().before(lance.getDataLance())){
                msg += "Data do lance posterior ao fechamento.\n";
            }

            if(!msg.equals("")){
                throw new ResponseStatusException(
                    HttpStatusCode.valueOf(400),
                    msg
                );
            }

            lance.setIdUsuario(user.getId());

            int linhas = repository.registerLance(lance);

            if (linhas == 0){
                throw new ResponseStatusException(
                    HttpStatusCode.valueOf(500),
                    "Erro ao cadastrar no banco."
                );
            }

        } else {
            throw new ResponseStatusException(
                HttpStatusCode.valueOf(403),
                "Acesso não autorizado"
            );
        }
    }
    
    public boolean jaRegistrou(String token, LancePostDTO lance){
        UserDTO user = tokenService.extractClaims(token);
        return repository.jaRegistrou(user.getId(), lance.getIdEdital());
    }
}
