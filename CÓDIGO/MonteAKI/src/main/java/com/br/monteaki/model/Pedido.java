package com.br.monteaki.model;

public class Pedido {

    private Long id;
    private int qntProd;
    private float valor;
    private String dataEntrada;
    private String dataSaida;
    
// Relacionamento com Cliente
    private Cliente cliente;
    
// Relacionamento com Carrinho 
    private Carrinho carrinho;

// Relacionamento com Estoque
    private Estoque estoque;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQntProd() {
        return qntProd;
    }

    public void setQntProd(int qntProd) {
        this.qntProd = qntProd;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(String dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public String getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(String dataSaida) {
        this.dataSaida = dataSaida;
    }

    public Estoque getEstoque() {
        return estoque;
    }

    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }

}
