
package com.bidding.system.bidding.repository;

import com.bidding.system.bidding.model.UserDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {
    
    public void register(UserDTO user){
        try{
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = null;
            stmt = conn.prepareStatement("INSERT INTO usuarios (nome, email, senha, role ) VALUES (?, ?, ?, ?)");
            
            stmt.setString(1, user.getNome());
            stmt.setString(2, user.getEmail());
            stmt.setString(4, user.getSenha());
            stmt.setString(4, user.getRole());
            
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas == 0){
                throw new SQLException("Falha na atualizacao, nenhuma linha foi afetadas.");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public UserDTO login(String email, String senha){
        UserDTO user = new UserDTO();
        try{
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            
            stmt = conn.prepareStatement("SELECT * FROM usuarios WHERE email = ? and senha = ?");
            
            stmt.setString(1, email);
            stmt.setString(2, senha);
            
            rs = stmt.executeQuery();
            
            if (rs.next()){
                user.setId(rs.getLong("id"));
                user.setNome(rs.getString("nome"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return user;
    }
}
