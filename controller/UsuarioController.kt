package com.horadedoar.api.controller

import com.horadedoar.api.model.Doador
import com.horadedoar.api.model.Usuario
import com.horadedoar.api.model.response.BaseResponse
import com.horadedoar.api.repository.UsuarioRepository

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class UsuarioController(private val usuarioRepository: UsuarioRepository) {

    @PostMapping("/usuario")
    fun criaNovoUsuario(@Valid @RequestBody usuario: Usuario): ResponseEntity<BaseResponse<Usuario>> {
        var usuarioExistente = usuarioRepository.findByEmail(usuario.email)

        usuarioExistente?.let {
            return BaseResponse.createResponse(
                isError = true,
                data = null,
                message = "Usuário ja cadastrado!",
                code = HttpStatus.INTERNAL_SERVER_ERROR
            )
        }

        val _usuarioNovo = usuarioRepository.save(usuario)

        return _usuarioNovo.let { _ ->
            BaseResponse.createResponse(
                isError = false,
                data = null,
                code = HttpStatus.CREATED
            )
        }
    }

    @PutMapping("/usuario/{email}")
    fun atualizaUsuarioPorId(@PathVariable(value = "email") email: String,
                            @Valid @RequestBody novoUsuario: Usuario): ResponseEntity<BaseResponse<Usuario>> {
        return usuarioRepository.findById(email).map { _usuarioExistente ->
            val usuarioAtualizado: Usuario = _usuarioExistente
                .copy(bloqueado = novoUsuario.bloqueado,
                    senha_expirada = novoUsuario.senha_expirada)

            BaseResponse.createResponse(
                isError = false,
                data = usuarioRepository.save((usuarioAtualizado)),
                code = HttpStatus.OK
            )
        }.orElse(ResponseEntity.notFound().build())
    }

    @PostMapping("/usuario/{email}/troca_senha")
    fun atualizaSenhaUsuarioPorId(@PathVariable(value = "email") email: String,
                            @Valid @RequestBody novaSenha: String): ResponseEntity<BaseResponse<Usuario>> {

        return usuarioRepository.findById(email).map { existingUsuario ->
            val senhaAtualizada: Usuario = existingUsuario
                .copy(senha_acesso = novaSenha, data_atualizacao = Calendar.getInstance().time)

            BaseResponse.createResponse(
                isError = false,
                data = usuarioRepository.save((senhaAtualizada)),
                code = HttpStatus.OK
            )
        }.orElse(ResponseEntity.notFound().build())
    }
}