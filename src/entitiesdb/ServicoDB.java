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
import entities.Servico;

/**
 *
 * @author johnr
 */
public class ServicoDB {
       
    public void create(Servico s) {
        
        Connection con = ConexaoDB.getConexao();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("INSERT INTO servico (nomeservico,descricao,preco)VALUES(?,?,?)");
            stmt.setString(1, s.getNome());
            stmt.setString(2, s.getDescricao());
            stmt.setDouble(3, s.getValor());
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null,"Salvo com sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro ao salvar: "+ex);

        }finally{
            ConexaoDB.closeConexao(con, stmt);
        }
       
        
    }
    
    public List<Servico> listar(){
        
        Connection con = ConexaoDB.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;
            
        List<Servico> servicos = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement("SELECT * FROM servico");
            rs = stmt.executeQuery();
            
            while(rs.next()){
                
                Servico servico = new Servico();
                servico.setId(rs.getInt("id"));
                servico.setNome(rs.getString("nomeservico"));
                servico.setDescricao(rs.getString("descricao"));
                servico.setValor(rs.getDouble("preco"));
                
                servicos.add(servico);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro ao listar: "+ex);
        }finally{
            ConexaoDB.closeConexao(con, stmt, rs);
        }
        
        return servicos;
    }
    
    public List<Servico> listarServicosPorPrestador(int idPrestador){
        
        Connection con = ConexaoDB.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Servico> servicos = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT s.* FROM servico_prestado_r sp "
                    + "JOIN prestador p ON sp.prestador_id = p.id "
                    + "JOIN servico s ON sp.servico_id = s.id "
                    + "WHERE p.id = ?");
            stmt.setInt(1, idPrestador); // Configura o parâmetro com o ID do prestador
            rs = stmt.executeQuery();

            while(rs.next()){

                Servico servico = new Servico();
                servico.setId(rs.getInt("id"));
                servico.setNome(rs.getString("nomeservico"));
                servico.setDescricao(rs.getString("descricao"));
                servico.setValor(rs.getDouble("preco"));

                servicos.add(servico);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao listar: " + ex);
        } finally {
            ConexaoDB.closeConexao(con, stmt, rs);
        }

        return servicos;
    }

    
    public List<Servico> listarEmOrdemCrescentePorValor() {
        Connection con = ConexaoDB.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Servico> servicos = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM servico ORDER BY preco ASC");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Servico servico = new Servico();
                servico.setId(rs.getInt("id"));
                servico.setNome(rs.getString("nomeservico"));
                servico.setDescricao(rs.getString("descricao"));
                servico.setValor(rs.getDouble("preco"));

                servicos.add(servico);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao listar em ordem crescente por valor: " + ex);
        } finally {
            ConexaoDB.closeConexao(con, stmt, rs);
        }

        return servicos;
    }
    
    public void atualizar(Servico s) {
        
        Connection con = ConexaoDB.getConexao();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("UPDATE servico SET preco = ?,nomeservico = ?,descricao = ? WHERE id = ?");
            stmt.setString(2, s.getNome());
            stmt.setString(3, s.getDescricao());
            stmt.setDouble(1, s.getValor());
            stmt.setInt(4, s.getId());
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null,"Atualizado com sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro ao atualizar: "+ex);

        }finally{
            ConexaoDB.closeConexao(con, stmt);
        }
       
        
    }
    
    public void excluir (Servico s) {
        
        Connection con = ConexaoDB.getConexao();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("DELETE FROM servico WHERE id = ?");
            stmt.setInt(1, s.getId());
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null,"Excluido com sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro ao excluirr: "+ex);

        }finally{
            ConexaoDB.closeConexao(con, stmt);
        }
       
        
    }
    
}
