/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author johnr
 */
public class ConexaoDB {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/dbpetshop";
    private static final String USER = "root";
    private static final String PASS = "1234";
    
    public static Connection getConexao() {
        try {
            Class.forName(DRIVER);
            
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException("Error ao conectar com banco de dados",ex);
        }
    }
    
    public static void closeConexao(Connection con){
    
            try {
                if(con != null){
                    con.close();
    		}
            } catch (SQLException ex) {
                Logger.getLogger(ConexaoDB.class.getName()).log(Level.SEVERE, null, ex);
            }
    		
    }
    
    public static void closeConexao(Connection con, PreparedStatement stmt){
            
        closeConexao(con);
       
        try {
            if(stmt != null){
                stmt.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexaoDB.class.getName()).log(Level.SEVERE, null, ex);
        }
          
    }
    
    public static void closeConexao(Connection con, PreparedStatement stmt,ResultSet rs){
            
        closeConexao(con,stmt);
            
        try {
            
            if(rs != null){
                rs.close();
            }
                
        } catch (SQLException ex) {
                Logger.getLogger(ConexaoDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
