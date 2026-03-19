package com.br.monteaki.model;

import java.util.List;

public class Cliente {

    private Long id;
    private String endereco;
    private String email;
    private String infoEnvio;

// Relacionamento com CarrinhoDeCompras
    private Carrinho carrinhoDeCompras;

// Relacionamento com Pedido
    private List<Pedido> pedidos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInfoEnvio() {
        return infoEnvio;
    }

    public void setInfoEnvio(String infoEnvio) {
        this.infoEnvio = infoEnvio;
    }

    public Carrinho getCarrinhoDeCompras() {
        return carrinhoDeCompras;
    }

    public void setCarrinhoDeCompras(Carrinho carrinhoDeCompras) {
        this.carrinhoDeCompras = carrinhoDeCompras;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

}
