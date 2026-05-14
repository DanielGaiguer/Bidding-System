/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.bidding.repository;

import com.bidding.system.bidding.model.EditalDTO;
import com.bidding.system.bidding.model.RequestListEditalDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class EditalDAO {
    public int create(EditalDTO edital){
        try{
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = null;
            
            stmt = conn.prepareStatement("INSERT INTO editais (titulo, descricao, data_fechamento, status) values (?, ?, ?, ?)");
            
            stmt.setString(1, edital.getTitulo());
            stmt.setString(2, edital.getDescricao());
            stmt.setDate(3, edital.getDataFechamento());
            stmt.setString(4, edital.getStatus());
            
            return stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
    
    public List<RequestListEditalDTO> listEdital(){
        List<RequestListEditalDTO> editais = new ArrayList<RequestListEditalDTO>();
        try{
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            
            stmt = conn.prepareStatement("Select * from editais");
            
            rs = stmt.executeQuery();
            
            while(rs.next()){
                RequestListEditalDTO edital = new RequestListEditalDTO();
                
                edital.setTitulo(rs.getString("titulo"));
                edital.setDescricao(rs.getString("descricao"));
                edital.setDataFechamento(rs.getDate("data_fechamento"));
                edital.setStatus(rs.getString("status"));
                
                editais.add(edital);
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return editais; 
    }
}
