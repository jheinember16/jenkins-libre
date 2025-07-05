package com.api.rest.publicaciones.controladores;

import com.api.rest.publicaciones.entidades.Publicacion;
import com.api.rest.publicaciones.excepciones.ResourceNotFoundException;
import com.api.rest.publicaciones.repositorios.PublicacionRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PublicacionController {

    @Autowired
    private PublicacionRepository publicacionRepository;

    @GetMapping("/publicaciones")
    public Page<Publicacion> listarPublicaciones(Pageable pageable){
        return publicacionRepository.findAll(pageable);
    }

    @PostMapping("/publicaciones")
    public Publicacion guardarPublicacion(@Valid @RequestBody Publicacion publicacion){
        return publicacionRepository.save(publicacion);
    }

    @GetMapping("/ping-server")
    public String ping() {
        return "Probando Servidor activo!";
    }






}