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
import java.util.ArrayList;
import java.util.List;
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
    
     public List<Pedido> listar(){
        
        Connection con = ConexaoDB.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;
            
        List<Pedido> pedidos = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement("SELECT * FROM pedido");
            rs = stmt.executeQuery();
            
            while(rs.next()){
                
                Pedido pedido = new Pedido();
                pedido.setId(rs.getInt("id"));
                pedido.setNomePet(rs.getString("nomepet"));
                pedido.setTipoPet(rs.getString("tipopet"));
                pedido.setPortePet(rs.getString("portepet"));
                pedido.setRacaPet(rs.getString("racapet"));
                pedido.setEstadopedido(rs.getString("estadopedido"));
                pedido.setData(rs.getString("data"));
                pedido.setValorTotal(rs.getDouble("valortotal"));
                
                pedidos.add(pedido);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro ao listar: "+ex);
        }finally{
            ConexaoDB.closeConexao(con, stmt, rs);
        }
        
        return pedidos;
    }
    
 
    public List<Pedido> listarPedidosPorId(int pedidoId) {
        Connection con = ConexaoDB.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Pedido> pedidos = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT pedido.id, pedido.nomepet, pedido.tipopet, pedido.portepet, pedido.racapet, "
                    + "pedido.estadopedido, pedido.data, pedido.valortotal, "
                    + "consumidor.nome as consumidor_nome, prestador.nome as prestador_nome, "
                    + "servico.nomeservico as tipo_servico, servico.preco as valor_servico "
                    + "FROM pedido "
                    + "JOIN consumidor ON pedido.consumidor_id = consumidor.id "
                    + "JOIN prestador ON pedido.prestador_id = prestador.id "
                    + "JOIN servico ON pedido.servico_id = servico.id "
                    + "WHERE pedido.id = ?");

            stmt.setInt(1, pedidoId);

            rs = stmt.executeQuery();

            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(rs.getInt("id"));
                pedido.setConsumidor(rs.getString("consumidor_nome"));
                pedido.setPrestador(rs.getString("prestador_nome"));
                pedido.setNomePet(rs.getString("nomepet"));
                pedido.setTipoPet(rs.getString("tipopet"));
                pedido.setPortePet(rs.getString("portepet"));
                pedido.setRacaPet(rs.getString("racapet"));
                pedido.setEstadopedido(rs.getString("estadopedido"));
                pedido.setData(rs.getString("data"));
                pedido.setValorTotal(rs.getDouble("valortotal"));
                pedido.setServico(rs.getString("tipo_servico"));
                pedido.setServicovalor(rs.getDouble("valor_servico"));

                pedidos.add(pedido);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao listar: " + ex);
        } finally {
            ConexaoDB.closeConexao(con, stmt, rs);
        }

        return pedidos;
    }

    
    public double calcularValorTotalPedido(int pedidoId) {
        Connection con = ConexaoDB.getConexao();
       PreparedStatement stmt = null;
       ResultSet rs = null;

       try {
           // Calcular o novo valor total considerando serviço e produtos usados
           String sql = "SELECT COALESCE(SUM(produto.preco * produtos_usados.quantidade), 0) AS valor_produtos_usados "
                      + "FROM produtos_usados "
                      + "LEFT JOIN produto ON produtos_usados.produto_id = produto.id "
                      + "WHERE produtos_usados.pedido_id = ?";
           stmt = con.prepareStatement(sql);
           stmt.setInt(1, pedidoId);
           rs = stmt.executeQuery();

           if (rs.next()) {
               double valorProdutosUsados = rs.getDouble("valor_produtos_usados");

               return valorProdutosUsados + obterValorServicoDoPedido(pedidoId);
           }

       } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "Erro ao listar: " + ex);
       } finally {
           ConexaoDB.closeConexao(con, stmt, rs);
       }

        // Em caso de erro, retorne 0
       return 0;
   }
    public double obterValorServicoDoPedido(int pedidoId) {
        Connection con = ConexaoDB.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Consultar o valor do serviço associado ao pedido
            String sql = "SELECT servico.preco AS valortotal FROM pedido " +
                         "JOIN servico ON pedido.servico_id = servico.id " +
                         "WHERE pedido.id = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, pedidoId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                // Retornar o valor do serviço
                return rs.getDouble("valortotal");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao listar: " + ex);
        } finally {
            ConexaoDB.closeConexao(con, stmt, rs);
        }

        
        return 0;
    }
    
}
