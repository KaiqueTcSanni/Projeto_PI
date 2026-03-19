
package com.br.monteaki.model;

public class Produto {

    private Long id;
    private String nomeProduto;
    private int qntProduto;
    private float valorProduto;
    private String descProduto;
    private String imagem;
    private String tipo_produto;
    private String fornecedor;

    public Produto() {
    }

    public Produto(Long id, String nomeProduto, int qntProduto, float valorProduto, String descProduto, String imagem, String tipo_produto, String fornecedor) {
        this.id = id;
        this.nomeProduto = nomeProduto;
        this.qntProduto = qntProduto;
        this.valorProduto = valorProduto;
        this.descProduto = descProduto;
        this.imagem = imagem;
        this.tipo_produto = tipo_produto;
        this.fornecedor = fornecedor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public int getQntProduto() {
        return qntProduto;
    }

    public void setQntProduto(int qntProduto) {
        this.qntProduto = qntProduto;
    }

    public float getValorProduto() {
        return valorProduto;
    }

    public void setValorProduto(float valorProduto) {
        this.valorProduto = valorProduto;
    }

    public String getDescProduto() {
        return descProduto;
    }

    public void setDescProduto(String descProduto) {
        this.descProduto = descProduto;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getTipo_produto() {
        return tipo_produto;
    }

    public void setTipo_produto(String tipo_produto) {
        this.tipo_produto = tipo_produto;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }
}

