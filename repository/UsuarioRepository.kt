package com.horadedoar.api.repository

import com.horadedoar.api.model.Doador
import com.horadedoar.api.model.Usuario

import org.springframework.data.jpa.repository.JpaRepository


interface UsuarioRepository : JpaRepository<Usuario, String>{
    fun findByEmail(email: String): Usuario?
}