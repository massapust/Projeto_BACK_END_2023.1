package com.horadedoar.api.repository

import com.horadedoar.api.model.Doador

import org.springframework.data.jpa.repository.JpaRepository


interface DoadorRepository : JpaRepository<Doador, String>{
    fun findByEmail(email: String): Doador?

}