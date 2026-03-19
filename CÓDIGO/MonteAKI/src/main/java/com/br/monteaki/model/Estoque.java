package com.br.monteaki.model;

public class Estoque {
    
    private Long id;
    private int qntProd;
    private float valor;
    private Produto produto;
    
    public void addEstoque(int quantidade) {
		this.qntProd += quantidade;
	}

	public void atualizarEstoque(int novaQuantidade) {
		this.qntProd = novaQuantidade;
	}

	// Getters e Setters
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

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

}
