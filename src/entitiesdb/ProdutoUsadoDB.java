/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entitiesdb;

import conexao.ConexaoDB;
import entities.ProdutosUsados;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author johnr
 */
public class ProdutoUsadoDB {
    public void create(ProdutosUsados pu) {
        
        Connection con = ConexaoDB.getConexao();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("INSERT INTO produtos_usados (quantidade,pedido_id,produto_id)VALUES(?,?,?)");
            stmt.setInt(1, pu.getQuantidade());
            stmt.setInt( 2,pu.getIdPedido());
            stmt.setInt(3, pu.getIdProduto());

            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null,"Salvo com sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro ao salvar: "+ex);

        }finally{
            ConexaoDB.closeConexao(con, stmt);
        }
        
              
    }
     public int obterUltimoIdPedido(){
        Connection con = ConexaoDB.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        int ultimoIdPedido = -1;

        try {
            stmt = con.prepareStatement("SELECT id FROM pedido ORDER BY id DESC LIMIT 1");

            rs = stmt.executeQuery();

            if (rs.next()) {
                ultimoIdPedido = rs.getInt("id");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao obter o último ID do pedido: " + ex);
        } finally {
            ConexaoDB.closeConexao(con, stmt, rs);
        }

        return ultimoIdPedido;
    }
}
