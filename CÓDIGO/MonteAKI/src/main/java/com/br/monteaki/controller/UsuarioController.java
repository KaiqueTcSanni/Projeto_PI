package com.br.monteaki.controller;

import com.br.monteaki.conn.Conexao;
import com.br.monteaki.model.Usuario;
import java.sql.*;

public class UsuarioController {

// Método para cadastrar um usuário usando um objeto Usuario
// Modificado para retornar boolean e verificar duplicidade
    public boolean cadastrar(Usuario usuario) throws SQLException {
        // Verificar se já existe um usuário com este e-mail
        if (buscarPorEmail(usuario.getEmail()) != null) {
            System.out.println("Usuário já cadastrado com o email: " + usuario.getEmail());
            return false; // Indica que o cadastro não foi realizado pois o usuário já existe
        }

        // Inserir os dados na tabela tbl_funcionarios
        String sql = "INSERT INTO tbl_funcionarios (nomeCompleto, cargo, dataIngresso, status_usuario, email, senha) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexao.getConexao(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNomeCompleto()); // nomeCompleto
            stmt.setString(2, usuario.getCargo()); // cargo
            stmt.setDate(3, Date.valueOf(usuario.getDataIngresso())); // dataIngresso
            stmt.setBoolean(4, usuario.isStatusUsuario()); // status_usuario
            stmt.setString(5, usuario.getEmail()); // email
            stmt.setString(6, usuario.getSenha()); // senha

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Usuário '" + usuario.getNomeCompleto() + "' cadastrado com sucesso!");
                return true; // Cadastro realizado com sucesso
            } else {
                System.out.println("Falha ao cadastrar o usuário '" + usuario.getNomeCompleto() + "'. Nenhuma linha afetada.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar usuário: " + e.getMessage());
            throw e; // Re-lança a exceção para ser tratada pelo chamador
        }
    }

// Método para login, retorna um objeto Usuario se as credenciais forem válidas
    public Usuario login(String email, String senha) throws SQLException {
        String sql = "SELECT * FROM tbl_funcionarios WHERE email = ? AND senha = ?";
        try (Connection con = Conexao.getConexao(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getLong("id_funcionarios")); // id_funcionarios
                usuario.setNomeCompleto(rs.getString("nomeCompleto")); // nomeCompleto
                usuario.setCargo(rs.getString("cargo")); // cargo
                usuario.setDataIngresso(rs.getDate("dataIngresso").toLocalDate()); // dataIngresso
                usuario.setStatusUsuario(rs.getBoolean("status_usuario")); // status_usuario
                usuario.setEmail(rs.getString("email")); // email
                usuario.setSenha(rs.getString("senha")); // senha
                return usuario;
            } else {
                return null; // Retorna null se não encontrar o usuário
            }
        }
    }

    // Método para buscar por nome de usuário pelo email
    public String buscarPorEmail(String email) throws SQLException {
        String sql = "SELECT nomeCompleto FROM tbl_funcionarios WHERE email = ?";
        try (Connection con = Conexao.getConexao(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nomeCompleto"); // Retorna o nome completo do usuário
            } else {
                return null; // Retorna null se não encontrar o email
            }
        }
    }

// Método para limpar todos os usuários
    public void limparUsuarios() throws SQLException {
        try (Connection con = Conexao.getConexao(); Statement stmt = con.createStatement()) {
            stmt.execute("DELETE FROM tbl_funcionarios");
            System.out.println("Todos os usuários foram limpos da tabela tbl_funcionarios.");
        }
    }

}
