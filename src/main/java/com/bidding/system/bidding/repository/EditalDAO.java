/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.bidding.repository;

import com.bidding.system.bidding.model.EditalDTO;
import com.bidding.system.bidding.model.LancePostDTO;
import com.bidding.system.bidding.model.RequestListEditalDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
                
                edital.setId(rs.getLong("id"));
                edital.setTitulo(rs.getString("titulo"));
                edital.setDescricao(rs.getString("descricao"));
                edital.setDataFechamento(rs.getDate("data_fechamento"));
                if (rs.getDate("data_fechamento").before(new Date()) && !"ENCERRADO".equals(rs.getString("status"))) {
                    stmt = conn.prepareStatement("Update editais set status = 'ENCERRADO' where id = ?");
                    stmt.setLong(1, edital.getId());
                    stmt.executeUpdate();
                    edital.setStatus("ENCERRADO");
                }else{
                    edital.setStatus(rs.getString("status"));
                }
                
                
                editais.add(edital);
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return editais; 
    }
    
    public List<RequestListEditalDTO> listEditaisUrgentes(){
        List<RequestListEditalDTO> editais = new ArrayList<RequestListEditalDTO>();
        try{
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            LocalDateTime agora = LocalDateTime.now();
            LocalDateTime limite = agora.plusHours(48);
            
            stmt = conn.prepareStatement("Select * from editais");
            
            rs = stmt.executeQuery();
            
            while(rs.next()){
                LocalDateTime fechamento = rs.getTimestamp("data_fechamento").toLocalDateTime();

                if ("ABERTO".equals(rs.getString("status"))&& fechamento.isAfter(agora) && fechamento.isBefore(limite)) {
                    RequestListEditalDTO edital = new RequestListEditalDTO();
                    edital.setId(rs.getLong("id"));
                    edital.setTitulo(rs.getString("titulo"));
                    edital.setDescricao(rs.getString("descricao"));
                    edital.setDataFechamento(rs.getDate("data_fechamento"));
                    edital.setStatus(rs.getString("status"));
                    editais.add(edital);
                }
                
                if (rs.getDate("data_fechamento").before(new Date()) && !"ENCERRADO".equals(rs.getString("status"))) {
                    stmt = conn.prepareStatement("Update editais set status = 'ENCERRADO' where id = ?");
                    stmt.setLong(1, rs.getLong("id"));
                    stmt.executeUpdate();
                }                

            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return editais; 
    }
    
    public int registerLance(LancePostDTO lance){
        try{
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = null;
            
            stmt = conn.prepareStatement("Insert into lances (valor, data_lance, id_edital, id_usuario) values (?, ?, ?, ?)");
            
            stmt.setFloat(1, lance.getValor());
            stmt.setDate(2, lance.getDataLance());
            stmt.setLong(3, lance.getIdEdital());
            stmt.setLong(4, lance.getIdUsuario());
            
            return stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return 0;
    }
    
    public boolean editalEncerrado(Long id){
        try{
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            
            stmt = conn.prepareStatement("Select * from editais where id = ?");
            
            stmt.setLong(1, id);
            
            rs = stmt.executeQuery();
            
            if(rs.next()){
                if (rs.getString("status").equals("ENCERRADO")){
                    return true;
                }
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return false;
    }
    
    
    public EditalDTO getById(Long id){
        EditalDTO edital = new EditalDTO();
        try{
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            
            stmt = conn.prepareStatement("Select data_fechamento, status from editais where id = ?");
            stmt.setLong(1, id);
            
            rs = stmt.executeQuery();
            
            if(rs.next()){
                edital.setDataFechamento(rs.getDate("data_fechamento"));
                if (rs.getDate("data_fechamento").before(new Date()) && !"ENCERRADO".equals(rs.getString("status"))) {
                    stmt = conn.prepareStatement("Update editais set status = 'ENCERRADO' where id = ?");
                    stmt.setLong(1, id);
                    stmt.executeUpdate();
                    edital.setStatus("ENCERRADO");
                }else {
                    edital.setStatus(rs.getString("status"));
                }
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return edital; 
    }
    
    public boolean jaRegistrou(Long idUsuario, Long idEdital){
        try{
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            
            stmt = conn.prepareStatement("Select * from lances where id_edital = ? and id_usuario = ?");
            stmt.setLong(1, idEdital);
            stmt.setLong(2, idUsuario);
            
            rs = stmt.executeQuery();
            
            if(rs.next()){
                return true;
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return false;
    }
}
