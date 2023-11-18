/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entitiesdb;

import conexao.ConexaoDB;
import entities.Consumidor;
import entities.Pedido;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author johnr
 */
public class PedidoDB {
    public void create(Pedido p) {
        
        Connection con = ConexaoDB.getConexao();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("INSERT INTO pedido (nomepet,tipopet,portepet,racapet,estadopedido,consumidor_id,prestador_id,servico_id,valortotal)VALUES(?,?,?,?,?,?,?,?,?)");
            stmt.setString(1, p.getNomePet());
            stmt.setString( 2,p.getTipoPet());
            stmt.setString(3, p.getPortePet());
            stmt.setString(4, p.getRacaPet());
            stmt.setString(5, p.getEstadopedido());
            stmt.setInt(6, p.getConsumidorId());
            stmt.setInt(7,p.getPrestadorId());
            stmt.setInt(8, p.getServicoId());
            stmt.setDouble(9, p.getValorTotal());
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null,"Salvo com sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro ao salvar: "+ex);

        }finally{
            ConexaoDB.closeConexao(con, stmt);
        }
       
        
    }
    
    
}
