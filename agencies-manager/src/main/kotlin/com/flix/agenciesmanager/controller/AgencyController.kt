package com.flix.agenciesmanager.controller

import com.flix.agenciesmanager.service.AgencyService
import com.flix.agenciesmanager.model.Agency
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/agencies")
@CrossOrigin(origins = ["http://localhost:8888"])
class AgencyController(val agencyService: AgencyService) {

    @GetMapping
    fun getAllAgencies(): Collection<Agency> = agencyService.getAllAgencies()

    @GetMapping("/{id}")
    fun getAgency(@PathVariable id: String): Agency = agencyService.getAgency(id).get()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createAgency(@RequestBody newAgency: Agency): Agency = agencyService.createAgency(newAgency)

    @PutMapping("/{id}")
    fun updateAgency(@PathVariable id: String, @RequestBody agency: Agency): Agency =
        agencyService.updateAgency(agency)

    @DeleteMapping("/{id}")
    fun deleteAgency(@PathVariable id: String) = agencyService.removeAgency(id)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(exception: IllegalArgumentException): ResponseEntity<String> {
        return ResponseEntity.badRequest().body(exception.message)
    }
}