package com.horadedoar.api.controller

import com.horadedoar.api.model.Doador
import com.horadedoar.api.model.Usuario
import com.horadedoar.api.model.request.Login
import com.horadedoar.api.model.response.BaseResponse
import com.horadedoar.api.repository.DoadorRepository
import com.horadedoar.api.repository.UsuarioRepository

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class AutenticacaoController(private val usuarioRepository: UsuarioRepository, private val doadorRepository: DoadorRepository) {

    @PostMapping("/auth")
    fun validaAcesso(@RequestBody login: Login): ResponseEntity<BaseResponse<Doador>> {

        val _acesso = login.email.let{
            usuarioRepository.findByEmail(it)
        }?: run{
            return BaseResponse.createResponse()
        }

        var _doador = doadorRepository.findByEmail(_acesso.email)

        _acesso.let {
            if(_acesso.senha_acesso == login.senha_acesso)
            {
                return BaseResponse.createResponse(
                    isError = false,
                    data = _doador,
                    code = HttpStatus.OK
                )
            }

            return BaseResponse.createResponse()
        }

        return BaseResponse.createResponse()
    }
}