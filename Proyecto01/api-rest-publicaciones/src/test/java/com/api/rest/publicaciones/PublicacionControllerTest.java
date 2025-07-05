package com.api.rest.publicaciones;

import com.api.rest.publicaciones.controladores.PublicacionController;
import com.api.rest.publicaciones.entidades.Publicacion;
import com.api.rest.publicaciones.excepciones.ResourceNotFoundException;
import com.api.rest.publicaciones.repositorios.PublicacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PublicacionControllerTest {

    @InjectMocks
    private PublicacionController publicacionController;

    @Mock
    private PublicacionRepository publicacionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test: listarPublicaciones debe devolver una página con publicaciones
    @Test
    void listarPublicaciones_devuelvePaginaDePublicaciones() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Publicacion> page = new PageImpl<>(Arrays.asList(new Publicacion(), new Publicacion()));
        when(publicacionRepository.findAll(pageable)).thenReturn(page);

        Page<Publicacion> resultado = publicacionController.listarPublicaciones(pageable);

        assertNotNull(resultado);
        assertEquals(2, resultado.getContent().size());
        verify(publicacionRepository, times(1)).findAll(pageable);
    }

    // Test: guardarPublicacion debe retornar la publicación guardada
    @Test
    void guardarPublicacion_devuelvePublicacionGuardada() {
        Publicacion publicacion = new Publicacion();
        publicacion.setTitulo("Titulo de prueba");
        when(publicacionRepository.save(publicacion)).thenReturn(publicacion);

        Publicacion resultado = publicacionController.guardarPublicacion(publicacion);

        assertNotNull(resultado);
        assertEquals("Titulo de prueba", resultado.getTitulo());
        verify(publicacionRepository, times(1)).save(publicacion);
    }

    // Test: actualizarPublicacion debe actualizar y devolver la publicación modificada
    @Test
    void actualizarPublicacion_devuelvePublicacionActualizada() {
        Long id = 1L;
        Publicacion existente = new Publicacion();
        existente.setId(id);

        Publicacion actualizada = new Publicacion();
        actualizada.setTitulo("Nuevo Titulo");
        actualizada.setDescripcion("Nueva descripcion");
        actualizada.setContenido("Nuevo contenido");

        when(publicacionRepository.findById(id)).thenReturn(Optional.of(existente));
        when(publicacionRepository.save(any())).thenReturn(existente);

        Publicacion resultado = publicacionController.actualizarPublicacion(id, actualizada);

        assertNotNull(resultado);
        assertEquals("Nuevo Titulo", resultado.getTitulo());
        verify(publicacionRepository).save(existente);
    }

    // Test: eliminarPublicacion debe eliminar correctamente si existe
    @Test
    void eliminarPublicacion_eliminaCorrectamente() {
        Long id = 1L;
        Publicacion publicacion = new Publicacion();
        when(publicacionRepository.findById(id)).thenReturn(Optional.of(publicacion));

        ResponseEntity<?> respuesta = publicacionController.eliminarPublicacion(id);

        assertEquals(200, respuesta.getStatusCodeValue());
        verify(publicacionRepository).delete(publicacion);
    }

    // Test: eliminarPublicacion debe lanzar excepción si no existe la publicación
    @Test
    void eliminarPublicacion_lanzaExcepcionSiNoExiste() {
        Long id = 999L;
        when(publicacionRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> publicacionController.eliminarPublicacion(id));
        verify(publicacionRepository, never()).delete(any());
    }
}
