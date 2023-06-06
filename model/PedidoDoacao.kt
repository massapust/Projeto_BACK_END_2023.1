package com.horadedoar.api.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.validation.annotation.Validated
import java.util.*
import javax.persistence.*
import javax.validation.constraints.*

@Entity
@Table(name = "pedido_doacao")
data class PedidoDoacao(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @get: NotBlank
    @Column(length = 80)
    @Size(min = 2, max = 80)
    val nome_paciente: String = "",

    @get: NotBlank
    @Column(length = 80)
    @Size(min = 2, max = 80)
    val nome_hospital: String = "",

    @get: NotBlank
    @Column(length = 80)
    @Size(min = 2, max = 80)
    val setor_intermacao: String = "",

    @get: NotBlank
    @Column(length = 3)
    @Size(min = 2, max = 3)
    val tipo_sanguineo: String = "",

    @get: NotBlank
    @Column(length = 500)
    @Size(min = 10, max = 500)
    val mensagem: String = "",

    @Validated
    @JsonIgnoreProperties
    @Basic(optional = true)
    @Column(name = "data_cadastro", columnDefinition = "DATETIME NULL DEFAULT CURRENT_TIMESTAMP")
    val data_cadastro: Date? = Calendar.getInstance().time,

    @Validated
    @JsonIgnoreProperties
    @Basic(optional = true)
    @Column(
        name = "data_atualizacao",
        columnDefinition = "DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"
    )
    val data_atualizacao: Date? = Calendar.getInstance().time,

    //@JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY, targetEntity = Doador::class)
    @JoinColumn(name = "email")
    var doador: Doador? = null
)
