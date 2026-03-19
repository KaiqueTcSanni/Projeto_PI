package com.br.monteaki.controller;

import com.br.monteaki.conn.Conexao;
import com.br.monteaki.model.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoController {

    public void cadastrar(Produto produto) throws SQLException {
        String sql = "INSERT INTO tbl_produtos (nome_produto, tipo_produto, fornecedor, descricao, valor, foto_produto) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = Conexao.getConexao(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, produto.getNomeProduto());
            stmt.setString(2, produto.getTipo_produto());
            stmt.setString(3, produto.getFornecedor());
            stmt.setString(4, produto.getDescProduto());
            stmt.setDouble(5, produto.getValorProduto());
            stmt.setString(6, produto.getImagem());
            stmt.executeUpdate();
        }
    }

    public List<Produto> listarTodos() {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM tbl_produtos";

        try (Connection con = Conexao.getConexao(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Produto p = new Produto();
                p.setId((long) rs.getInt("id_produto"));
                p.setNomeProduto(rs.getString("nome_produto"));
                p.setTipo_produto(rs.getString("tipo_produto"));

                // --- AJUSTE AQUI: Adicionado o campo fornecedor que faltava ---
                p.setFornecedor(rs.getString("fornecedor"));

                p.setValorProduto(rs.getFloat("valor"));
                p.setDescProduto(rs.getString("descricao"));
                p.setImagem(rs.getString("foto_produto"));

                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar: " + e.getMessage());
        }
        return lista;
    }

    public void atualizar(Produto produto) throws SQLException {
        String sql = "UPDATE tbl_produtos SET nome_produto = ?, tipo_produto = ?, fornecedor = ?, descricao = ?, valor = ?, foto_produto = ? WHERE id_produto = ?";

        try (Connection con = Conexao.getConexao(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, produto.getNomeProduto());
            stmt.setString(2, produto.getTipo_produto());
            stmt.setString(3, produto.getFornecedor());
            stmt.setString(4, produto.getDescProduto());
            stmt.setDouble(5, produto.getValorProduto());
            stmt.setString(6, produto.getImagem());
            stmt.setLong(7, produto.getId());

            stmt.executeUpdate();
        }
    }

    public void excluir(Long id) throws SQLException {
        String sql = "DELETE FROM tbl_produtos WHERE id_produto = ?";
        try (Connection con = Conexao.getConexao(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    public Produto buscarPorId(long id) {
        String sql = "SELECT * FROM tbl_produtos WHERE id_produto = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Produto p = new Produto();
                    p.setId((long) rs.getInt("id_produto"));
                    p.setNomeProduto(rs.getString("nome_produto"));
                    p.setTipo_produto(rs.getString("tipo_produto"));

                    // --- AJUSTE AQUI: Adicionado o campo fornecedor também na busca por ID ---
                    p.setFornecedor(rs.getString("fornecedor"));

                    p.setValorProduto(rs.getFloat("valor"));
                    p.setDescProduto(rs.getString("descricao"));
                    p.setImagem(rs.getString("foto_produto"));
                    return p;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar: " + e.getMessage());
        }
        return null;
    }

    public List<Produto> listarPorTipo(String tipo) {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM tbl_produtos WHERE tipo_produto = ?";

        try (Connection con = Conexao.getConexao(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, tipo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Produto p = new Produto();
                    p.setId((long) rs.getInt("id_produto"));
                    p.setNomeProduto(rs.getString("nome_produto"));
                    p.setTipo_produto(rs.getString("tipo_produto"));
                    p.setFornecedor(rs.getString("fornecedor"));
                    p.setValorProduto(rs.getFloat("valor"));
                    p.setDescProduto(rs.getString("descricao"));
                    p.setImagem(rs.getString("foto_produto"));
                    lista.add(p);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar por tipo: " + e.getMessage());
        }
        return lista;
    }
}
