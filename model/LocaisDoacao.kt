package com.horadedoar.api.model

import javax.persistence.*
import javax.validation.constraints.*

@Entity @Table(name = "locais_doacao")
data class LocaisDoacao(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @get: NotBlank
    @Column(length = 250)
    @Size(min = 2, max = 250)
    val nome_local: String = "",

    @get: NotBlank
    @Column(length = 350)
    @Size(min = 2, max = 350)
    val endereco_completo: String = "",

    @get: NotBlank
    @Column(length = 11)
    @Size(min = 9, max = 11)
    val telefone: String = "",

    @get: Null
    @Column(length = 80)
    @Size(min = 10, max = 80)
    val dias_horarios_doacao: String = "",
)

