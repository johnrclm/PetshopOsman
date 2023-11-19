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
import entities.Produto;

/**
 *
 * @author johnr
 */
public class ProdutoDB {
       
    public void create(Produto p) {
        
        Connection con = ConexaoDB.getConexao();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("INSERT INTO produto (quantidade,preco,nome,marca)VALUES(?,?,?,?)");
            stmt.setString(3, p.getNome());
            stmt.setString(4, p.getMarca());
            stmt.setInt(1, p.getQuantidade());
            stmt.setDouble(2, p.getValor());
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null,"Salvo com sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro ao salvar: "+ex);

        }finally{
            ConexaoDB.closeConexao(con, stmt);
        }
       
        
    }
    
    public List<Produto> listar(){
        
        Connection con = ConexaoDB.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;
            
        List<Produto> produtos = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement("SELECT * FROM produto");
            rs = stmt.executeQuery();
            
            while(rs.next()){
                
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setMarca(rs.getString("marca"));
                produto.setQuantidade(rs.getInt("quantidade"));
                produto.setValor(rs.getDouble("preco"));
                
                produtos.add(produto);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro ao listar: "+ex);
        }finally{
            ConexaoDB.closeConexao(con, stmt, rs);
        }
        
        return produtos;
    }

    
    public void atualizar(Produto p) {
        
        Connection con = ConexaoDB.getConexao();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("UPDATE produto SET quantidade = ?,preco = ?,nome = ?,marca = ? WHERE id = ?");
            stmt.setString(3, p.getNome());
            stmt.setString(4, p.getMarca());
            stmt.setInt(1, p.getQuantidade());
            stmt.setDouble(2, p.getValor());
            stmt.setInt(5, p.getId());
            
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null,"Atualizado com sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro ao atualizar: "+ex);

        }finally{
            ConexaoDB.closeConexao(con, stmt);
        }
       
        
    }
    
    public void excluir (Produto p) {
        
        Connection con = ConexaoDB.getConexao();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("DELETE FROM produto WHERE id = ?");
            stmt.setInt(1, p.getId());
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null,"Excluido com sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro ao excluirr: "+ex);

        }finally{
            ConexaoDB.closeConexao(con, stmt);
        }
       
        
    }
    
}
