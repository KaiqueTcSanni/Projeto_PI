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
        jButton1.setText("Editar");
        jButton1.setBackground(new java.awt.Color(106, 13, 173));
        jButton1.setForeground(java.awt.Color.WHITE);

        // Lógica para carregar a imagem
        try {
            // 1. Defina apenas a PASTA RAIZ onde os arquivos realmente estão
            String pastaRaiz = "C:\\Users\\kaiqu\\Downloads\\Imagens PNG\\Imagens PNG\\";
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        setMaximumSize(new java.awt.Dimension(150, 190));
        setMinimumSize(new java.awt.Dimension(150, 190));
        setPreferredSize(new java.awt.Dimension(150, 190));
        setRequestFocusEnabled(false);

        jLabel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel2.setText("jLabel2");

        jButton1.setBackground(new java.awt.Color(106, 13, 173));
        jButton1.setText("jButton1");
        jButton1.setContentAreaFilled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jButton1)))
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addGap(35, 35, 35)
                .addComponent(jButton1)
                .addGap(78, 78, 78))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        // Abre a tela de cadastro em modo edição
        TelaCadastroProduto telaEdit = new TelaCadastroProduto(this.produto, this.telaPai);
        telaEdit.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
