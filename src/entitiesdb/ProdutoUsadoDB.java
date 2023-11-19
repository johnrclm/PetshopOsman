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
import java.util.ArrayList;
import java.util.List;
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
           
            stmt = con.prepareStatement("INSERT INTO produtos_usados (quantidade, preco, pedido_id, produto_id) VALUES (?, ?, ?, ?)");
            stmt.setInt(1, pu.getQuantidade());
            stmt.setDouble(2, pu.getPreco()); // 
            stmt.setInt(3, pu.getIdPedido());
            stmt.setInt(4, pu.getIdProduto());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Salvo com sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex);

        } finally {
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
     
    public List<ProdutosUsados> listarProdutosUsados() {
        Connection con = ConexaoDB.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<ProdutosUsados> listaProdutosUsados = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT id, nome, marca, preco FROM produto");
            rs = stmt.executeQuery();

            while (rs.next()) {
                int idProduto = rs.getInt("id");
                String nomeProduto = rs.getString("nome");
                String marcaProduto = rs.getString("marca");
                double precoProduto = rs.getDouble("preco");

                // Obtendo a quantidade da tabela "produtos_usados" com base no ID do produto
                PreparedStatement stmtProdutosUsados = con.prepareStatement("SELECT quantidade FROM produtos_usados WHERE produto_id = ?");
                stmtProdutosUsados.setInt(1, idProduto);
                ResultSet rsProdutosUsados = stmtProdutosUsados.executeQuery();

                while (rsProdutosUsados.next()) {
                    int quantidade = rsProdutosUsados.getInt("quantidade");

                    double precoTotal = precoProduto * quantidade;

                    ProdutosUsados produtosUsados = new ProdutosUsados();
                    produtosUsados.setIdProduto(idProduto);
                    produtosUsados.setNome(nomeProduto);
                    produtosUsados.setMarca(marcaProduto);
                    produtosUsados.setPrecoUnitario(precoProduto);
                    produtosUsados.setQuantidade(quantidade);

                    // Definindo o preço total corretamente
                    produtosUsados.setPreco(precoTotal);

                    listaProdutosUsados.add(produtosUsados);
                }

                ConexaoDB.closeConexao(null, stmtProdutosUsados, rsProdutosUsados);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos usados: " + ex);
        } finally {
            ConexaoDB.closeConexao(con, stmt, rs);
        }

        return listaProdutosUsados;
    }
    
    public List<ProdutosUsados> listarProdutosUsadosPorPedido(int pedidoId) {
        Connection con = ConexaoDB.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<ProdutosUsados> listaProdutosUsados = new ArrayList<>();

        try {
            // Seleciona os produtos usados em um pedido específico
            String sql = "SELECT produto.id, produto.nome, produto.marca, produto.preco, produtos_usados.quantidade " +
                         "FROM produto " +
                         "JOIN produtos_usados ON produto.id = produtos_usados.produto_id " +
                         "WHERE produtos_usados.pedido_id = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, pedidoId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int idProduto = rs.getInt("id");
                String nomeProduto = rs.getString("nome");
                String marcaProduto = rs.getString("marca");
                double precoProduto = rs.getDouble("preco");
                int quantidade = rs.getInt("quantidade");

                double precoTotal = precoProduto * quantidade;

                ProdutosUsados produtosUsados = new ProdutosUsados();
                produtosUsados.setIdProduto(idProduto);
                produtosUsados.setNome(nomeProduto);
                produtosUsados.setMarca(marcaProduto);
                produtosUsados.setPrecoUnitario(precoProduto);
                produtosUsados.setQuantidade(quantidade);
                produtosUsados.setPreco(precoTotal);

                listaProdutosUsados.add(produtosUsados);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos usados: " + ex);
        } finally {
            ConexaoDB.closeConexao(con, stmt, rs);
        }

        return listaProdutosUsados;
    }
    
    public static List<ProdutosUsados> listarProdutosPorPedido(int pedidoId) {
        Connection con = ConexaoDB.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<ProdutosUsados> listaProdutosUsados = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT produto_id, pedido_id, quantidade, preco FROM produtos_usados WHERE pedido_id = ?");
            stmt.setInt(1, pedidoId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int produtoId = rs.getInt("produto_id");
                int pedidoIdResultado = rs.getInt("pedido_id");
                int quantidade = rs.getInt("quantidade");
                double preco = rs.getDouble("preco");

                ProdutosUsados produtosUsados = new ProdutosUsados();
                produtosUsados.setIdProduto(produtoId);
                produtosUsados.setIdPedido(pedidoIdResultado);
                produtosUsados.setQuantidade(quantidade);
                produtosUsados.setPreco(preco);

                listaProdutosUsados.add(produtosUsados);
            }
        } catch (SQLException ex) {
            // Trate a exceção conforme necessário
        } finally {
            ConexaoDB.closeConexao(con, stmt, rs);
        }

        return listaProdutosUsados;
    }

     
  
}
