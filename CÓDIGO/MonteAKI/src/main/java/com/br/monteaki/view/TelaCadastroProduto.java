package com.br.monteaki.view;

import com.br.monteaki.controller.ProdutoController;
import com.br.monteaki.model.Produto;
import java.awt.BorderLayout;
import java.awt.Image;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class TelaCadastroProduto extends javax.swing.JFrame {

    private File arquivoSelecionado;
    private String caminhoRelativo;
    private JLabel lblFotoPrevia;
    private final String PASTA_MEDIA_DJANGO = "C:/Users/kaique.tcsanni/Downloads/MonteAki (1)/MonteAki/MonteAki/WEB/monte_aki/media/";
    private TelaListagemProdutos telaListagem;
    private Produto produtoEmEdicao = null;

    public javax.swing.JTextField getTxtTipo() {
        return jTextField2; // Certifique-se de que o campo de 'Tipo' é o jTextField2
    }

    public TelaCadastroProduto(Produto p, TelaListagemProdutos lista) {
        initComponents();
        this.getContentPane().setBackground(java.awt.Color.WHITE);
        configurarEstiloTela();
        configurarExibicaoFoto();

        this.produtoEmEdicao = p;
        this.telaListagem = lista;

        if (p != null) {
            preencherParaEdicao(p); // Este método agora cuida de tudo
            jButton2.setText("Atualizar");
        } else {
            jButton2.setText("Salvar");
        }
    }

    private void preencherCampos() {
        if (produtoEmEdicao != null) {
            jTextField1.setText(produtoEmEdicao.getNomeProduto());
            jTextField2.setText(produtoEmEdicao.getTipo_produto());
            jTextField2.setEditable(false);
            jTextField3.setText(produtoEmEdicao.getFornecedor());
            jTextArea2.setText(produtoEmEdicao.getDescProduto());
            jTextField4.setText(String.valueOf(produtoEmEdicao.getValorProduto()));

            // Atualizar os labels de Preview
            lblPreviewNome.setText(produtoEmEdicao.getNomeProduto());
            lblPreviewTipo.setText(produtoEmEdicao.getTipo_produto());
            lblPreviewFornecedor.setText(produtoEmEdicao.getFornecedor());
            lblPreviewValor.setText("Valor R$: " + produtoEmEdicao.getValorProduto());
            lblPreviewDescricao.setText("<html><body style='width: 350px'>" + produtoEmEdicao.getDescProduto() + "</body></html>");

            // Carregar a imagem atual
            if (produtoEmEdicao.getImagem() != null && !produtoEmEdicao.getImagem().isEmpty()) {
                this.caminhoRelativo = produtoEmEdicao.getImagem();
                File foto = new File(PASTA_MEDIA_DJANGO + produtoEmEdicao.getImagem());
                if (foto.exists()) {
                    exibirImagem(foto.getAbsolutePath());
                }
            }
        }
    }

    public TelaCadastroProduto(String nomeUsuario, Produto p) {
        initComponents();
        configurarEstiloTela();
        configurarExibicaoFoto();

        if (nomeUsuario != null) {
            jLabel3.setText(nomeUsuario); // Nome do usuário no topo
        }

        if (p != null) {
            preencherParaEdicao(p);
        }
    }

    // Sobrecarga para facilitar chamadas de novo produto
    public TelaCadastroProduto(String nomeUsuario) {
        this(nomeUsuario, null);
    }

    private void configurarEstiloTela() {
        getContentPane().setBackground(new java.awt.Color(255, 255, 255));
        lblPreviewDescricao.setText("<html><body style='width: 300px'>Descrição do Produto: </body></html>");
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void configurarExibicaoFoto() {
        lblFotoPrevia = new JLabel();
        lblFotoPrevia.setHorizontalAlignment(SwingConstants.CENTER);
        lblFotoPrevia.setVerticalAlignment(SwingConstants.CENTER);
        jPanel1.setLayout(new BorderLayout());
        jPanel1.add(lblFotoPrevia, BorderLayout.CENTER);
    }

    private void exibirImagem(String caminhoAbsoluto) {
        ImageIcon icon = new ImageIcon(caminhoAbsoluto);
        Image img = icon.getImage().getScaledInstance(jPanel1.getWidth(), jPanel1.getHeight(), Image.SCALE_SMOOTH);
        lblFotoPrevia.setIcon(new ImageIcon(img));
    }

    private void preencherParaEdicao(Produto p) {
        this.produtoEmEdicao = p;

        // Preenche os campos de texto
        jTextField1.setText(p.getNomeProduto());
        jTextField2.setText(p.getTipo_produto());
        jTextField3.setText(p.getFornecedor()); // <--- AQUI ESTAVA O SEGREDO
        jTextArea2.setText(p.getDescProduto());
        jTextField4.setText(String.valueOf(p.getValorProduto()));

        // Atualiza Labels de Preview (A parte visual da direita)
        lblPreviewNome.setText(p.getNomeProduto());
        lblPreviewTipo.setText(p.getTipo_produto());
        lblPreviewFornecedor.setText(p.getFornecedor());
        lblPreviewValor.setText("Valor R$: " + p.getValorProduto());
        lblPreviewDescricao.setText("<html><body style='width: 350px'>" + p.getDescProduto() + "</body></html>");

        // Carrega a foto no Painel de Preview
        if (p.getImagem() != null && !p.getImagem().isEmpty()) {
            this.caminhoRelativo = p.getImagem();
            File fotoFisica = new File(PASTA_MEDIA_DJANGO + p.getImagem());
            if (fotoFisica.exists()) {
                exibirImagem(fotoFisica.getAbsolutePath());
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        TelaImg = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lblPreviewNome = new javax.swing.JLabel();
        lblPreviewTipo = new javax.swing.JLabel();
        lblPreviewFornecedor = new javax.swing.JLabel();
        lblPreviewDescricao = new javax.swing.JLabel();
        lblPreviewValor = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtBoasVindas = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(1350, 850));
        setResizable(false);

        TelaImg.setBackground(new java.awt.Color(245, 245, 245));
        TelaImg.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        TelaImg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jPanel1.setMinimumSize(new java.awt.Dimension(489, 305));
        jPanel1.setPreferredSize(new java.awt.Dimension(489, 305));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        TelaImg.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 390, 290));

        lblPreviewNome.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblPreviewNome.setText("Nome do Produto");
        TelaImg.add(lblPreviewNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 450, -1));

        lblPreviewTipo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblPreviewTipo.setText("Tipo do Produto");
        TelaImg.add(lblPreviewTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, 450, -1));

        lblPreviewFornecedor.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblPreviewFornecedor.setText("Fornecedor");
        TelaImg.add(lblPreviewFornecedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 450, -1));

        lblPreviewDescricao.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        lblPreviewDescricao.setText("Descrição do Produto ");
        lblPreviewDescricao.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        TelaImg.add(lblPreviewDescricao, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, 450, 160));

        lblPreviewValor.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblPreviewValor.setText("Valor R$:");
        TelaImg.add(lblPreviewValor, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 620, 450, 32));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel12.setText("Prévia da imagem no site   ");
        TelaImg.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setText("Cadastro de Produtos");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("Nome do Produto");

        jTextField1.setBackground(new java.awt.Color(245, 245, 245));
        jTextField1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("Tipo do Produto ");

        jTextField2.setBackground(new java.awt.Color(245, 245, 245));
        jTextField2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setText("Fornecedor ");

        jTextField3.setBackground(new java.awt.Color(245, 245, 245));
        jTextField3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField3KeyReleased(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setText("Descrição do Produto ");

        jTextArea2.setBackground(new java.awt.Color(245, 245, 245));
        jTextArea2.setColumns(20);
        jTextArea2.setLineWrap(true);
        jTextArea2.setRows(5);
        jTextArea2.setWrapStyleWord(true);
        jTextArea2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jTextArea2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextArea2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextArea2KeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(jTextArea2);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setText("Valor");

        jTextField4.setBackground(new java.awt.Color(245, 245, 245));
        jTextField4.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jTextField4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField4KeyReleased(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setText("Imagem do Produto ");

        jButton1.setBackground(new java.awt.Color(0, 102, 153));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setText("Upload");
        jButton1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton1.setBorderPainted(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(66, 222, 90));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setText("Salvar");
        jButton2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton2.setBorderPainted(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(106, 13, 173));
        jPanel3.setFocusCycleRoot(true);
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("MONTEAKI");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(114, 15, 120, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Mini_Logo.png"))); // NOI18N
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 7, 80, 49));
        jPanel3.add(txtBoasVindas, new org.netbeans.lib.awtextra.AbsoluteConstraints(1091, 22, 254, 25));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/user.png"))); // NOI18N
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1053, 15, -1, -1));

        jLabel13.setText("HOME");
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 20, -1, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setText("Salver Produto ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 1351, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(310, 310, 310)
                        .addComponent(jLabel4))
                    .addComponent(jLabel5)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(166, 166, 166)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(170, 170, 170)
                        .addComponent(jLabel11)
                        .addGap(89, 89, 89)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(170, 170, 170)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(187, 187, 187)
                .addComponent(TelaImg, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(48, 48, 48)
                        .addComponent(jLabel5)
                        .addGap(5, 5, 5)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addComponent(jLabel8)
                        .addGap(15, 15, 15)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(jLabel9)
                        .addGap(5, 5, 5)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10))
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(TelaImg, javax.swing.GroupLayout.PREFERRED_SIZE, 690, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(49, Short.MAX_VALUE))
        );

        jTextField1.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void limparCampos() {
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField2.setEditable(true);
        jTextField3.setText("");
        jTextArea2.setText("");
        jTextField4.setText("R$:");
        lblFotoPrevia.setIcon(null);
        arquivoSelecionado = null;
        caminhoRelativo = null;
        produtoEmEdicao = null;
        jButton2.setText("Salvar");
    }

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            arquivoSelecionado = chooser.getSelectedFile();
            exibirImagem(arquivoSelecionado.getAbsolutePath());
            // Define o padrão Django: "resources/nome_da_imagem.jpg"
            caminhoRelativo = "resources/" + arquivoSelecionado.getName();
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        // TODO add your handling code here:
        String texto = jTextField1.getText().trim();
        if (texto.isEmpty()) {
            lblPreviewNome.setText("Nome do Produto"); // Texto padrão quando apaga tudo
        } else {
            lblPreviewNome.setText(texto);
        }
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
        // TODO add your handling code here:
        String texto = jTextField2.getText().trim();
        if (texto.isEmpty()) {
            lblPreviewTipo.setText("Tipo do Produto");
        } else {
            lblPreviewTipo.setText(texto);
        }
    }//GEN-LAST:event_jTextField2KeyReleased

    private void jTextField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyReleased
        // TODO add your handling code here:
        String texto = jTextField3.getText().trim();
        if (texto.isEmpty()) {
            lblPreviewFornecedor.setText("Fornecedor");
        } else {
            lblPreviewFornecedor.setText(texto);
        }
    }//GEN-LAST:event_jTextField3KeyReleased

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased
        // TODO add your handling code here:
        // 1. Remove tudo que não for número
        String original = jTextField4.getText().replaceAll("[^\\d]", "");

        // 2. Limita a 7 dígitos (5 esquerda + 2 direita)
        if (original.length() > 7) {
            original = original.substring(0, 7);
        }

        // 3. Formata visualmente para o usuário: 00000,00
        String formatado = "";
        if (original.length() > 2) {
            String parteInteira = original.substring(0, original.length() - 2);
            String parteDecimal = original.substring(original.length() - 2);
            formatado = parteInteira + "," + parteDecimal;
        } else {
            formatado = original;
        }

        // Atualiza o campo de digitação (mantendo o R$: se desejar)
        jTextField4.setText(formatado);

        // 4. Atualiza o PREVIEW com o R$:
        lblPreviewValor.setText("Valor R$: " + formatado);
    }//GEN-LAST:event_jTextField4KeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            // 1. Captura e Limpeza dos campos
            String nome = jTextField1.getText().trim();
            String tipo = jTextField2.getText().trim(); // Este campo define o "Grupo" no banco
            String fornecedor = jTextField3.getText().trim();
            String valorTexto = jTextField4.getText().replace("R$:", "").replace("R$", "").replace(" ", "").replace(",", ".").trim();

            // 2. VALIDAÇÃO OBRIGATÓRIA (Campos e Grupo)
            if (nome.isEmpty() || tipo.isEmpty() || fornecedor.isEmpty() || valorTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Atenção: Nome, Tipo, Fornecedor e Valor são obrigatórios!",
                        "Campos Incompletos",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 3. VALIDAÇÃO DA IMAGEM (OBRIGATÓRIA)
            boolean temImagemJaSalva = (this.produtoEmEdicao != null && this.produtoEmEdicao.getImagem() != null);
            if (arquivoSelecionado == null && !temImagemJaSalva) {
                JOptionPane.showMessageDialog(this, "Erro: Selecione uma imagem para o produto!");
                return;
            }

            // 4. MONTA O OBJETO PRODUTO (Novo ou Edição)
            Produto p = (this.produtoEmEdicao == null) ? new Produto() : this.produtoEmEdicao;

            // 5. TRATAMENTO DA IMAGEM
            if (arquivoSelecionado != null) {
                File destino = new File(PASTA_MEDIA_DJANGO + "resources/" + arquivoSelecionado.getName());
                if (!destino.getParentFile().exists()) {
                    destino.getParentFile().mkdirs();
                }
                Files.copy(arquivoSelecionado.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                p.setImagem("resources/" + arquivoSelecionado.getName());
            }

            // 6. SETA OS DADOS (O 'tipo' aqui garante a inserção no grupo correto)
            p.setNomeProduto(nome);
            p.setTipo_produto(tipo);
            p.setFornecedor(fornecedor);
            p.setDescProduto(jTextArea2.getText());

            try {
                p.setValorProduto((float) Double.parseDouble(valorTexto));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Valor inválido.");
                return;
            }

            // 7. PERSISTÊNCIA
            ProdutoController controller = new ProdutoController();
            if (p.getId() == null) {
                controller.cadastrar(p); // INSERT INTO tbl_produtos
                JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!");
            } else {
                controller.atualizar(p); // UPDATE tbl_produtos WHERE id_produto = ?
                JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!");
            }

            // 8. ATUALIZAÇÃO DA INTERFACE (Filtrada pela galeria)
            if (this.telaListagem != null) {
                this.telaListagem.atualizarGaleria();
                this.telaListagem.setVisible(true);
            }

            // --- LOGICA DE RESET PARA MANTER A SESSÃO ---
            // 9. Reset das variáveis de controle para permitir NOVO cadastro
            this.produtoEmEdicao = null;
            this.arquivoSelecionado = null;

            // 10. Limpeza seletiva dos campos
            jTextField1.setText(""); // Limpa Nome
            jTextField3.setText(""); // Limpa Fornecedor
            jTextField4.setText("R$: "); // Reseta Valor
            jTextArea2.setText(""); // Limpa Descrição

            // NOTA: jTextField2 (Tipo) NÃO é limpo para manter o usuário na mesma "Sessão" de cadastro.
            // Reset do Preview de Imagem (ajuste o nome do seu JLabel de imagem se necessário)
            // jLabelPreview.setIcon(null); 
            // O cursor volta para o Nome para agilizar o próximo cadastro
            jTextField1.requestFocus();

            // --- NOVA LÓGICA DE RETORNO ---
            JOptionPane.showMessageDialog(this, "Operação realizada com sucesso! Retornando...");

            this.dispose(); // Fecha esta janela de cadastro/edição
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage());
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextArea2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea2KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea2KeyTyped

    private void jTextArea2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea2KeyReleased
        // TODO add your handling code here:
        String texto = jTextArea2.getText().trim();

        if (texto.isEmpty()) {
            lblPreviewDescricao.setText("<html><body style='width: 350px'>Descrição do Produto: </body></html>");
        } else {
            // Limita a 255 caracteres como você já faz
            if (texto.length() > 255) {
                texto = texto.substring(0, 255);
                jTextArea2.setText(texto);
            }
            lblPreviewDescricao.setText("<html><body style='width: 350px'>" + texto + "</body></html>");
        }
    }//GEN-LAST:event_jTextArea2KeyReleased

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        // TODO add your handling code here:
        if (this.telaListagem != null) {
            this.telaListagem.setVisible(true);
        }
        this.dispose();
    }//GEN-LAST:event_jLabel13MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new TelaCadastroProduto("").setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel TelaImg;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JLabel lblPreviewDescricao;
    private javax.swing.JLabel lblPreviewFornecedor;
    private javax.swing.JLabel lblPreviewNome;
    private javax.swing.JLabel lblPreviewTipo;
    private javax.swing.JLabel lblPreviewValor;
    private javax.swing.JLabel txtBoasVindas;
    // End of variables declaration//GEN-END:variables
}
