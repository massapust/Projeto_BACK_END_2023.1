package com.horadedoar.api.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonManagedReference
import org.springframework.validation.annotation.Validated
import java.util.*
import javax.persistence.*
import javax.validation.constraints.*

@Entity
@Table(name = "doador")
data class Doador(
    @Id
    @Column(unique=true, length = 150)
    @Size(min = 7, max = 150)
    @get: NotBlank
    var email: String = "",

    @get: NotBlank
    @Column(length = 250)
    @Size(min = 2, max = 250)
    val nome_completo: String = "",

    @get: NotNull
    val data_nascimento: Date,

    @get: NotBlank
    @Column(length = 3)
    @Size(min = 2, max = 3)
    val tipo_sanguineo: String = "",

    //@get: NotNull
    @Validated
    @JsonIgnoreProperties
    @Column(name = "data_cadastro", columnDefinition = "DATETIME NULL DEFAULT CURRENT_TIMESTAMP")
    @Basic(optional = true)
    val data_cadastro: Date? =  Calendar.getInstance().time,

    //@get: NotNull
    @Validated
    @JsonIgnoreProperties
    @Basic(optional = true)
    @Column(name = "data_atualizacao", columnDefinition = "DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    val data_atualizacao: Date? = Calendar.getInstance().time,

    //@OneToOne
    //@PrimaryKeyJoinColumn(name="email", referencedColumnName="email")
    /*@Validated
    @OneToOne
    @MapsId
    @Basic(optional = true)
    @JsonIgnoreProperties
    @JoinColumn(name ="email", nullable = true)
    val usuario: Usuario?,*/

    /*@Validated
    @JsonManagedReference
    @OneToOne(mappedBy = "doador", cascade = [(CascadeType.ALL)], fetch= FetchType.EAGER)
    var usuario: Usuario?,*/
)
