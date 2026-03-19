package com.br.monteaki.model;

import java.time.LocalDateTime;
import java.util.List;

public class Carrinho {

    private Long id;
    private LocalDateTime dataCriacao;
    
    // Relacionamento com Cliente
    private Cliente cliente;
    
}
