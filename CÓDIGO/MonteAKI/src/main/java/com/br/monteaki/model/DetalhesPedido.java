package com.br.monteaki.model;
 
import java.util.List;
 
public class DetalhesPedido {
 
    private Long id;
    private Usuario usuario;
    private List<Produto> produtos;
    private double total;
 
    // Construtor vazio
    public DetalhesPedido() {
    }
 
    // Construtor com parâmetros
    public DetalhesPedido(Long id, Usuario usuario, List<Produto> produtos, double total) {
        this.id = id;
        this.usuario = usuario;
        this.produtos = produtos;
        this.total = total;
    }
 
    // Getters e Setters
    public Long getId() {
        return id;
    }
 
    public void setId(Long id) {
        this.id = id;
    }
 
    public Usuario getUsuario() {
        return usuario;
    }
 
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
 
    public List<Produto> getProdutos() {
        return produtos;
    }
 
    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
 
    public double getTotal() {
        return total;
    }
 
    public void setTotal(double total) {
        this.total = total;
    }
}