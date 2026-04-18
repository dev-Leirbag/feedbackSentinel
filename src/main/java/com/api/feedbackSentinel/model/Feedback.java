package com.api.feedbackSentinel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_feedback")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "texto_original", nullable = false, length = 1000)
    private String textoOriginal;

    @Column(name = "sentimento", nullable = false, length = 100)
    private String sentimento;

    @Column(name = "categoria", nullable = false, length = 100)
    private String categoria;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

}
