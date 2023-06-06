package com.horadedoar.api.controller

import com.horadedoar.api.model.Doador
import com.horadedoar.api.model.PedidoDoacao
import com.horadedoar.api.model.response.BaseResponse
import com.horadedoar.api.repository.DoadorRepository
import com.horadedoar.api.repository.PedidosDoacaoRepository

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class PedidoDoacaoController(private val pedidosDoacaoRepository: PedidosDoacaoRepository, private val doadorRepository: DoadorRepository) {

    @PostMapping("/pedido_doacao/{email}")
    fun criaNovoPedidoDoacao(@PathVariable(value = "email") email: String,
                             @Valid @RequestBody pedidoDoacao: PedidoDoacao): ResponseEntity<BaseResponse<PedidoDoacao>> {
        var doadorLocalizado = email.let { doadorRepository.findByEmail(it) } ?: run{
            return BaseResponse.createResponse(
                isError = true,
                data = null,
                message = "Doador não informado!",
                code = HttpStatus.BAD_REQUEST
            )
        }

        pedidoDoacao.doador = doadorLocalizado

        val _pedidoDoacao = pedidosDoacaoRepository.save(pedidoDoacao)

        return _pedidoDoacao.let { _ ->
            BaseResponse.createResponse(
                isError = false,
                data = _pedidoDoacao,
                code = HttpStatus.CREATED
            )
        }
    }

    @PutMapping("/pedido_doacao/{id}")
    fun atualizaPedidoDoacaoPorId(
        @PathVariable(value = "id") id: Long,
        @Valid @RequestBody novoPedidoDoacao: PedidoDoacao
    ): ResponseEntity<BaseResponse<PedidoDoacao>> {

        return pedidosDoacaoRepository.findById(id).map { _pedidoDoacao ->
            val _update: PedidoDoacao = _pedidoDoacao
                .copy(
                    mensagem = novoPedidoDoacao.mensagem,
                    nome_hospital = novoPedidoDoacao.nome_hospital,
                    nome_paciente = novoPedidoDoacao.nome_paciente,
                    tipo_sanguineo = novoPedidoDoacao.tipo_sanguineo,
                    setor_intermacao = novoPedidoDoacao.setor_intermacao
                )

            BaseResponse.createResponse(
                isError = false,
                data = pedidosDoacaoRepository.save(_update),
                code = HttpStatus.OK
            )
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/pedido_doacao/{id}")
    fun excluiPedidoDoacaoPorId(@PathVariable(value = "id") id: Long): ResponseEntity<BaseResponse<Nothing>>? {

        return pedidosDoacaoRepository.findById(id).map { _existing ->
            pedidosDoacaoRepository.delete(_existing)

            BaseResponse.createResponse(
                isError = false,
                data = null,
                code = HttpStatus.OK
            )
        }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/pedido_doacao")
    fun getAllPedidosDoacao(): ResponseEntity<BaseResponse<List<PedidoDoacao>>> = BaseResponse.createResponse(
        isError = false,
        data = pedidosDoacaoRepository.findAll(),
        code = HttpStatus.OK
    )
}