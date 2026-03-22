/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.br.monteaki.view;

import com.br.monteaki.model.Produto;
import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;

public class CardProduto extends javax.swing.JPanel {

    private Produto produto;
    private TelaListagemProdutos telaPai;

    public CardProduto(Produto p, TelaListagemProdutos pai) {
        initComponents();
        this.produto = p;        // Agora o card conhece o ID e os dados do produto
        this.telaPai = pai;
        preencherDados();
    }

    private void preencherDados() {
        jLabel2.setText(produto.getNomeProduto());
        jLabel1.setIcon(null);

        // Lógica para carregar a imagem
        try {
            // 1. Defina apenas a PASTA RAIZ onde os arquivos realmente estão
            String pastaRaiz = "C:/Users/kaiqu/OneDrive/Desktop/Nova pasta/Projeto_PI/WEB/monte_aki/media/";
            String caminhoBanco = produto.getImagem(); // Vem como "produtos/foto.png" ou "foto.png"

            if (caminhoBanco == null || caminhoBanco.trim().isEmpty()) {
                jLabel1.setText("Sem foto");
                return;
            }

            // 2. Criar o arquivo. Se no banco já tem "produtos/", certifique-se que a pasta física existe dentro da pastaRaiz
            File file = new File(pastaRaiz + caminhoBanco);

            if (file.exists()) {
                java.awt.image.BufferedImage imgFull = javax.imageio.ImageIO.read(file);

                // AJUSTE DE DIMENSÃO: Se o JLabel for 0 (antes de renderizar), use um tamanho padrão
                int largura = jLabel1.getWidth() > 0 ? jLabel1.getWidth() : 133;
                int altura = jLabel1.getHeight() > 0 ? jLabel1.getHeight() : 76;

                Image dimg = imgFull.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);

                jLabel1.setIcon(new ImageIcon(dimg));
                jLabel1.setText("");
            } else {
                System.out.println("O Java tentou ler este caminho exato: " + file.getAbsolutePath());
                System.out.println("Este arquivo existe fisicamente? " + file.exists());
                jLabel1.setText("Não encontrada");
                // Isso ajudará você a ver no console o caminho exato que o Java está tentando ler
                System.err.println("Caminho tentado: " + file.getAbsolutePath());
            }
        } catch (Exception e) {
            jLabel1.setText("Erro de leitura");
            System.err.println("Erro ao carregar imagem: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        jCheckBox1.setText("jCheckBox1");

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        setMaximumSize(new java.awt.Dimension(150, 190));
        setMinimumSize(new java.awt.Dimension(150, 190));
        setPreferredSize(new java.awt.Dimension(150, 190));
        setRequestFocusEnabled(false);

        jLabel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel2.setText("jLabel2");

        jButton2.setBackground(new java.awt.Color(153, 255, 153));
        jButton2.setText("Editar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(14, 14, 14)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jButton2)))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addGap(39, 39, 39)
                .addComponent(jButton2)
                .addGap(14, 14, 14))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        TelaCadastroProduto telaEdit = new TelaCadastroProduto(this.produto, this.telaPai);
        telaEdit.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
