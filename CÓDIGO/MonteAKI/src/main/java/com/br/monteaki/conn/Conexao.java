package com.br.monteaki.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    public static Connection getConexao() throws SQLException {
        final String SERVIDOR = "jdbc:sqlserver://127.0.0.1:1433;databaseName=MonteAki;encrypt=false;trustServerCertificate=true";
        final String USUARIO = "sa";
        final String SENHA = "pw_user_app";

        return DriverManager.getConnection(SERVIDOR, USUARIO, SENHA);

    }
    // Método para testar a conexão

    public static void testarConexao() {
        try (Connection con = getConexao()) {
            System.out.println("Conexão com o banco de dados foi bem-sucedida!");
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados:");
            System.out.println(e.getMessage());
            e.printStackTrace(); // Imprime detalhes da exceção
        }
    }
}
