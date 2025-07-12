package com.api.rest.publicaciones.controladores;

import com.api.rest.publicaciones.entidades.Comentario;
import com.api.rest.publicaciones.excepciones.ResourceNotFoundException;
import com.api.rest.publicaciones.repositorios.ComentarioRepository;
import com.api.rest.publicaciones.repositorios.PublicacionRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ComentarioController {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private PublicacionRepository publicacionRepository;

    @GetMapping("/publicaciones/{publicacionId}/comentarios")
    public Page<Comentario> listarComentariosPorPublicacion(@PathVariable(value = "publicacionId") Long publicacionId, Pageable pageable){
        return comentarioRepository.findByPublicacionId(publicacionId, pageable);
    }

    @PostMapping("/publicaciones/{publicacionId}/comentarios")
    public Comentario guardarComentario(@PathVariable(value = "publicacionId") Long publicacionId, @Valid @RequestBody Comentario comentario) {
        return publicacionRepository.findById(publicacionId).map(publicacion -> {
            comentario.setPublicacion(publicacion);
            return comentarioRepository.save(comentario);
        }).orElseThrow(() -> new ResourceNotFoundException("Publicacion con el ID : " + publicacionId + " no encontrada"));
    }



    @DeleteMapping("/publicaciones/{publicacionId}/comentarios/{comentarioId}")
    public ResponseEntity<?> eliminarComentario(@PathVariable(value = "publicacionId") Long publicacionId, @PathVariable(value = "comentarioId") Long comentarioId){
        return comentarioRepository.findByIdAndPublicacionId(comentarioId, publicacionId).map(comentario -> {
            comentarioRepository.delete(comentario);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Comentario con el ID : " + comentarioId + " no encontrado y publicacion con el ID : " + publicacionId + " no encontrada"));
    }

    @PutMapping("/publicaciones/{publicacionId}/comentarios/{comentarioId}")
    public Comentario actualizarComentario(@PathVariable(value = "publicacionId") Long publicacionId,@PathVariable(value = "comentarioId") Long comentarioId,@Valid @RequestBody Comentario comentarioRequest) {
        if(!publicacionRepository.existsById(publicacionId)) {
            throw new ResourceNotFoundException("Publicacion con el ID : " + publicacionId + " no encontrada");
        }

        return comentarioRepository.findById(comentarioId).map(comentario -> {
            comentario.setTexto(comentarioRequest.getTexto());
            return comentarioRepository.save(comentario);
        }).orElseThrow(() -> new ResourceNotFoundException("Comentario con el ID : " + comentarioId + " no encontrado"));
    }
}