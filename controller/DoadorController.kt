package com.horadedoar.api.controller

import com.horadedoar.api.model.Doador
import com.horadedoar.api.model.response.BaseResponse
import com.horadedoar.api.repository.DoadorRepository

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class DoadorController(private val doadorRepository: DoadorRepository) {

    @PostMapping("/doador")
    fun criaNovoDoador(@Valid @RequestBody doador: Doador): ResponseEntity<BaseResponse<Doador>> {

        var doadorExistente = doadorRepository.findByEmail(doador.email)

        doadorExistente?.let {
            return BaseResponse.createResponse(
                isError = true,
                data = null,
                message = "Doador ja cadastrado!",
                code = HttpStatus.INTERNAL_SERVER_ERROR
            )
        }

        val _doador = doadorRepository.save(doador)

        return _doador.let { _ ->
            BaseResponse.createResponse(
                isError = false,
                data = _doador,
                code = HttpStatus.CREATED
            )
        }
    }

    @PutMapping("/doador")
    fun atualizaDoadorPorId(
        @Valid @RequestBody doador: Doador
    ): ResponseEntity<BaseResponse<Doador>> {
        return doadorRepository.findById(doador.email).map { _doadorExistente ->
            val _doadorAtualizado: Doador = _doadorExistente
                .copy(
                    nome_completo = doador.nome_completo,
                    data_nascimento = doador.data_nascimento,
                    tipo_sanguineo = doador.tipo_sanguineo
                )

            BaseResponse.createResponse(
                isError = false,
                data = doadorRepository.save(_doadorAtualizado),
                code = HttpStatus.OK
            )
        }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/doador")
    fun getAllDoadores(): ResponseEntity<BaseResponse<List<Doador>>> = BaseResponse.createResponse(
        isError = false,
        data = doadorRepository.findAll(),
        code = HttpStatus.OK
    )
}