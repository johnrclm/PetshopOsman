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
import entities.Consumidor;
import entities.Regiao;

/**
 *
 * @author johnr
 */
public class ConsumidorDB {
       
    public void create(Consumidor c) {
        
        Connection con = ConexaoDB.getConexao();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("INSERT INTO consumidor (nome,idade,email,endereco,regiao_id)VALUES(?,?,?,?,?)");
            stmt.setString(1, c.getNome());
            stmt.setInt( 2,c.getIdade());
            stmt.setString(3, c.getEmail());
            stmt.setString(4, c.getEndereco());
            stmt.setInt(5, c.getRegiao_id());
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null,"Salvo com sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro ao salvar: "+ex);

        }finally{
            ConexaoDB.closeConexao(con, stmt);
        }
       
        
    }
    
    public List<Consumidor> listar(){
        
        Connection con = ConexaoDB.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;
            
        List<Consumidor> consumidores = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement("SELECT c.*, r.uf FROM consumidor c JOIN regiao r ON c.regiao_id = r.id");
            rs = stmt.executeQuery();
            
            while(rs.next()){
                
                Consumidor consumidor = new Consumidor();
                consumidor.setId(rs.getInt("id"));
                consumidor.setNome(rs.getString("nome"));
                consumidor.setIdade(rs.getInt("idade"));
                consumidor.setEmail(rs.getString("email"));
                consumidor.setEndereco(rs.getString("endereco"));
                consumidor.setRegiao_id(rs.getInt("regiao_id"));
                consumidor.setUf(rs.getString("uf"));
                                
                
                consumidores.add(consumidor);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro ao listar: "+ex);
        }finally{
            ConexaoDB.closeConexao(con, stmt, rs);
        }
        
        return consumidores;
    }
    
    public List<Consumidor> listarEmOrdemCrescentePorNome() {
        Connection con = ConexaoDB.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Consumidor> consumidores = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT c.*, r.uf FROM consumidor c " +
                                       "JOIN regiao r ON c.regiao_id = r.id " +
                                       "ORDER BY c.nome ASC");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Consumidor consumidor = new Consumidor();
                consumidor.setId(rs.getInt("id"));
                consumidor.setNome(rs.getString("nome"));
                consumidor.setIdade(rs.getInt("idade"));
                consumidor.setEmail(rs.getString("email"));
                consumidor.setEndereco(rs.getString("endereco"));
                consumidor.setRegiao_id(rs.getInt("regiao_id"));
                consumidor.setUf(rs.getString("uf"));

                consumidores.add(consumidor);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao listar em ordem crescente por idade: " + ex);
        } finally {
            ConexaoDB.closeConexao(con, stmt, rs);
        }

        return consumidores;
    }

    
    public List<Consumidor> listarConsumidorPorRegiao(String regiao){
        
        Connection con = ConexaoDB.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;
            
        List<Consumidor> consumidores = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement("SELECT c.*, r.uf FROM consumidor c JOIN regiao r ON c.regiao_id = r.id WHERE r.uf = ?");
            stmt.setString(1, regiao);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                
                Consumidor consumidor = new Consumidor();
                consumidor.setId(rs.getInt("id"));
                consumidor.setNome(rs.getString("nome"));
                consumidor.setIdade(rs.getInt("idade"));
                consumidor.setEmail(rs.getString("email"));
                consumidor.setEndereco(rs.getString("endereco"));
                consumidor.setRegiao_id(rs.getInt("regiao_id"));
                consumidor.setUf(rs.getString("uf"));
                                
                
                consumidores.add(consumidor);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro ao listar: "+ex);
        }finally{
            ConexaoDB.closeConexao(con, stmt, rs);
        }
        
        return consumidores;
    }
    
    public void atualizar(Consumidor c) {
        
        Connection con = ConexaoDB.getConexao();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("UPDATE consumidor SET nome = ?,idade = ?,email = ?,endereco = ?,regiao_id = ? WHERE id = ?");
            stmt.setString(1, c.getNome());
            stmt.setInt( 2,c.getIdade());
            stmt.setString(3, c.getEmail());
            stmt.setString(4, c.getEndereco());
            stmt.setInt(5, c.getRegiao_id());
            stmt.setInt(6, c.getId());
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null,"Atualizado com sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro ao atualizar: "+ex);

        }finally{
            ConexaoDB.closeConexao(con, stmt);
        }
       
        
    }
    
    public void excluir (Consumidor c) {
        
        Connection con = ConexaoDB.getConexao();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("DELETE FROM consumidor WHERE id = ?");
            stmt.setInt(1, c.getId());
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null,"Excluido com sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro ao excluirr: "+ex);

        }finally{
            ConexaoDB.closeConexao(con, stmt);
        }
       
        
    }
    
}
