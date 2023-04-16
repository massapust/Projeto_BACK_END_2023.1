package com.example.kotlindemo.controller

import com.example.kotlindemo.model.Doador
import com.example.kotlindemo.repository.DoadorRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

/**
 * Created by rajeevkumarsingh on 04/10/17.
 */

@RestController
@RequestMapping("/api")
class DoadorController(private val doadorRepository: DoadorRepository) {

    @GetMapping("/doadors")
    fun getAlldoadors(): List<Doador> =
            doadorRepository.findAll()


    @PostMapping("/doadors")
    fun createNewdoador(@Valid @RequestBody doador: Doador): Doador =
            doadorRepository.save(doador)


    @GetMapping("/doadors/{id}")
    fun getdoadorById(@PathVariable(value = "id") doadorId: Long): ResponseEntity<Doador> {
        return doadorRepository.findById(doadorId).map { doador ->
            ResponseEntity.ok(doador)
        }.orElse(ResponseEntity.notFound().build())
    }

    @PutMapping("/doadors/{id}")
    fun updatedoadorById(@PathVariable(value = "id") doadorId: Long,
                          @Valid @RequestBody newdoador: Doador): ResponseEntity<Doador> {

        return doadorRepository.findById(doadorId).map { existingdoador ->
            val updateddoador: Doador = existingdoador
                    .copy(title = newdoador.title, content = newdoador.content)

            ResponseEntity.ok().body(doadorRepository.save(updateddoador))
        }.orElse(ResponseEntity.notFound().build())

    }

    @DeleteMapping("/doadors/{id}")
    fun deletedoadorById(@PathVariable(value = "id") doadorId: Long): ResponseEntity<Void> {

        return doadorRepository.findById(doadorId).map { doador  ->
            doadorRepository.delete(doador)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())

    }
}