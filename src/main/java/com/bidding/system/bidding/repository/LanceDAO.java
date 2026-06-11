/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.bidding.repository;

import com.bidding.system.bidding.model.RequestListLanceDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class LanceDAO {
     public List<RequestListLanceDTO> listarLances(Long userId){
        List<RequestListLanceDTO> lances = new ArrayList<RequestListLanceDTO>();
        try{
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            
            stmt = conn.prepareStatement("SELECT lances.id AS id_lance, lances.valor, lances.data_lance, lances.id_edital, lances.id_usuario, editais.id AS id_edital_real, editais.titulo, editais.status FROM lances INNER JOIN editais ON lances.id_edital = editais.id WHERE lances.id_usuario = ?");
            stmt.setLong(1, userId);
            
            rs = stmt.executeQuery();
            
            while(rs.next()){
                RequestListLanceDTO lance = new RequestListLanceDTO();
                
                lance.setTituloLance(rs.getString("titulo"));
                lance.setValorLance(rs.getFloat("valor"));
                lance.setStatusEdital(rs.getString("status"));
                if("ENCERRADO".equals(lance.getStatusEdital()) && foiVencedor(rs.getLong("id_edital"), rs.getLong("id_lance"))){
                    lance.setVencedor(true);
                }else{
                    lance.setVencedor(false);
                }
                lances.add(lance);
            }
            
        }catch(SQLException e){
            e.printStackTrace();
            return lances;
        }
        
        return lances;
    }
     
     public boolean foiVencedor(Long idEdital, Long idLance) {
        try {
            Connection conn = Conexao.conectar();

            PreparedStatement stmt =
                conn.prepareStatement(
                    "SELECT * FROM lances WHERE id_edital = ?"
                );

            stmt.setLong(1, idEdital);

            ResultSet rs = stmt.executeQuery();

            float menorValor = Float.MAX_VALUE;
            Long menorLanceId = null;

            while (rs.next()) {
                float valor = rs.getFloat("valor");

                if (valor < menorValor) {
                    menorValor = valor;
                    menorLanceId = rs.getLong("id");
                }
            }

            return menorLanceId == idLance;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
