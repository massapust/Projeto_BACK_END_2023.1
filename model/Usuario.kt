package com.horadedoar.api.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonManagedReference
import org.springframework.validation.annotation.Validated
import java.util.*
import javax.persistence.*
import javax.validation.constraints.*

@Entity
@Table(name = "usuario")
data class Usuario(
    @Id
    @Column(unique=true, length = 150)
    @Size(min = 7, max = 150)
    var email: String = "",

    @get: NotBlank
    @Column(length = 15)
    @Size(min = 8, max = 15)
    val senha_acesso: String = "",

    val bloqueado: Boolean = false,

    val senha_expirada: Boolean = false,

    @Validated
    @JsonIgnore
    @Basic(optional = true)
    @Column(name = "data_cadastro", columnDefinition = "DATETIME NULL DEFAULT CURRENT_TIMESTAMP")
    val data_cadastro: Date? = Calendar.getInstance().time,

    @Validated
    @JsonIgnore
    @Basic(optional = true)
    @Column(name = "data_atualizacao", columnDefinition = "DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    val data_atualizacao: Date? = Calendar.getInstance().time,

    /*@Validated
    @OneToOne
    @JsonManagedReference
    @JoinColumn(name="email", referencedColumnName = "email")
    val doador: Doador?,*/
)
