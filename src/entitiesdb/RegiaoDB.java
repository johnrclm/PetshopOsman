/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entitiesdb;

import conexao.ConexaoDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import entities.Regiao;

/**
 *
 * @author johnr
 */
public class RegiaoDB {
       
    
    public List<Regiao> listar(){
        
        Connection con = ConexaoDB.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;
            
        List<Regiao> regioes = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement("SELECT * FROM regiao");
            rs = stmt.executeQuery();
            
            while(rs.next()){
                
                Regiao regiao = new Regiao();
                regiao.setId(rs.getInt("id"));
                regiao.setNome(rs.getString("nome"));
                regiao.setUf(rs.getString("uf"));
                regioes.add(regiao);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro ao listar: "+ex);
        }finally{
            ConexaoDB.closeConexao(con, stmt, rs);
        }
        
        return regioes;
    }
        
}
