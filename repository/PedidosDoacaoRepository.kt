package com.horadedoar.api.repository

import com.horadedoar.api.model.PedidoDoacao
import org.springframework.data.jpa.repository.JpaRepository

interface PedidosDoacaoRepository: JpaRepository<PedidoDoacao, Long>