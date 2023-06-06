package com.horadedoar.api.controller

import com.horadedoar.api.model.Doador
import com.horadedoar.api.model.LocaisDoacao
import com.horadedoar.api.model.response.BaseResponse
import com.horadedoar.api.repository.LocaisDoacaoRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class LocaisDoacaoController(private val locaisDoacaoRepository: LocaisDoacaoRepository) {

    @GetMapping("/locais_doacao")
    fun getAllLocaisDoacao(): ResponseEntity<BaseResponse<List<LocaisDoacao>>> = BaseResponse.createResponse(
        isError = false,
        data = locaisDoacaoRepository.findAll(),
        code = HttpStatus.OK
    )
}