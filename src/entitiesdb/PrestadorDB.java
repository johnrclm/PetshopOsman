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
import entities.Prestador;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author johnr
 */
public class PrestadorDB {
       
    public void create(Prestador p) {
        
        Connection con = ConexaoDB.getConexao();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("INSERT INTO prestador (nome,url,email,telefone,endereco,regiao_id)VALUES(?,?,?,?,?,?)");
            stmt.setString(1, p.getNome());
            stmt.setString( 2,p.getUrl());
            stmt.setString(3, p.getEmail());
            stmt.setString(4, p.getTelefone());
            stmt.setString(5, p.getEndereco());
            stmt.setInt(6, p.getRegiao_id());
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null,"Salvo com sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro ao salvar: "+ex);

        }finally{
            ConexaoDB.closeConexao(con, stmt);
        }
       
        
    }
    
    public List<Prestador> listar(){
        
        Connection con = ConexaoDB.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;
            
        List<Prestador> prestadores = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement("SELECT c.*, r.uf FROM prestador c JOIN regiao r ON c.regiao_id = r.id");
            rs = stmt.executeQuery();
            
            while(rs.next()){
                
                Prestador prestador = new Prestador();
                prestador.setId(rs.getInt("id"));
                prestador.setNome(rs.getString("nome"));
                prestador.setUrl(rs.getString("url"));
                prestador.setEmail(rs.getString("email"));
                prestador.setTelefone(rs.getString("telefone"));
                prestador.setEndereco(rs.getString("endereco"));
                prestador.setRegiao_id(rs.getInt("regiao_id"));
                prestador.setUf(rs.getString("uf"));
                                
                
                prestadores.add(prestador);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro ao listar padrao: "+ex);
        }finally{
            ConexaoDB.closeConexao(con, stmt, rs);
        }
        
        return prestadores;
    }

    
    public List<Prestador> listarPrestadoresPorServico(String tipoServico) {
        Connection con = ConexaoDB.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Prestador> prestadores = new ArrayList<>();

        try {

            stmt = con.prepareStatement("SELECT p.*, r.uf, s.* FROM prestador p " +
                               "JOIN servico_prestado_r sp ON p.id = sp.prestador_id " +
                               "JOIN servico s ON sp.servico_id = s.id " +
                               "JOIN regiao r ON p.regiao_id = r.id " +
                               "WHERE s.nomeservico = ?");

            stmt.setString(1, tipoServico);

            rs = stmt.executeQuery();

            while (rs.next()) {
                Prestador prestador = new Prestador();
                prestador.setId(rs.getInt("id"));
                prestador.setNome(rs.getString("nome"));
                prestador.setUrl(rs.getString("url"));
                prestador.setEmail(rs.getString("email"));
                prestador.setTelefone(rs.getString("telefone"));
                prestador.setEndereco(rs.getString("endereco"));
                prestador.setRegiao_id(rs.getInt("regiao_id"));
                prestador.setUf(rs.getString("uf"));
                prestador.setServico(rs.getString("nomeservico"));
                prestador.setValor(rs.getDouble("preco")); // Ajuste aqui para pegar o preço do serviço
                prestadores.add(prestador);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao listar prestadores por serviço: " + ex);
        } finally {
            ConexaoDB.closeConexao(con, stmt, rs);
        }

        return prestadores;
    }
        
     public List<Prestador> listarPrestadoresOrdenadosPorValor() {
        Connection con = ConexaoDB.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Prestador> prestadores = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT p.*, r.uf, s.* FROM prestador p " +
                                       "JOIN servico_prestado_r sp ON p.id = sp.prestador_id " +
                                       "JOIN servico s ON sp.servico_id = s.id " +
                                       "JOIN regiao r ON p.regiao_id = r.id " +
                                       "ORDER BY s.preco DESC");

            rs = stmt.executeQuery();

            while (rs.next()) {
                Prestador prestador = new Prestador();
                prestador.setId(rs.getInt("id"));
                prestador.setNome(rs.getString("nome"));
                prestador.setUrl(rs.getString("url"));
                prestador.setEmail(rs.getString("email"));
                prestador.setTelefone(rs.getString("telefone"));
                prestador.setEndereco(rs.getString("endereco"));
                prestador.setRegiao_id(rs.getInt("regiao_id"));
                prestador.setUf(rs.getString("uf"));
                prestador.setServico(rs.getString("nomeservico"));
                prestador.setValor(rs.getDouble("preco"));
                prestadores.add(prestador);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao listar prestadores: " + ex);
        } finally {
            ConexaoDB.closeConexao(con, stmt, rs);
        }

        return prestadores;
    }

    
    
    
    public void atualizar(Prestador p) {
        
        Connection con = ConexaoDB.getConexao();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("UPDATE prestador SET nome = ?,url = ?,email = ?,telefone = ?,endereco = ?,regiao_id = ? WHERE id = ?");
            stmt.setString(1, p.getNome());
            stmt.setString( 2,p.getUrl());
            stmt.setString(3, p.getEmail());
            stmt.setString(4, p.getTelefone());
            stmt.setString(5, p.getEndereco());
            stmt.setInt(6, p.getRegiao_id());
            stmt.setInt(7, p.getId());
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null,"Atualizado com sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro ao atualizar: "+ex);

        }finally{
            ConexaoDB.closeConexao(con, stmt);
        }
       
        
    }
    
    public void excluir (Prestador c) {
        
        Connection con = ConexaoDB.getConexao();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("DELETE FROM prestador WHERE id = ?");
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
