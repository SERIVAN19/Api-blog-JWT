package com.sistema.blog.blog.controller;

import com.sistema.blog.blog.dto.ComentarioDTO;
import com.sistema.blog.blog.service.ComentarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class ComentarioController {

    @Autowired
    private ComentarioServicio comentarioServicio;

    //Listar comentarios
    @GetMapping("/publicaciones/{publicacionId}/comentarios")
    public List<ComentarioDTO> listarComentariosPorPublicacionId(@PathVariable Long publicacionId){
        return comentarioServicio.obtenerComentariosPorPublicacionId(publicacionId);
    }

    //Obtener comentarios por id
    @GetMapping("/publicaciones/{publicacionId}/comentarios/{comentarioId}")
    public ResponseEntity<ComentarioDTO> listarComentariosPorId(@PathVariable Long publicacionId, @PathVariable Long comentarioId){
        ComentarioDTO comentarioDTO = comentarioServicio.obtenerComentarioPorId(publicacionId,comentarioId);
        return new ResponseEntity<>(comentarioDTO,HttpStatus.OK);
    }

    //Crear nuevo comentario
    @PostMapping("/publicaciones/{publicacionId}/comentarios")
    public ResponseEntity<ComentarioDTO> guardarComentario(@PathVariable Long publicacionId, @RequestBody ComentarioDTO comentarioDTO){
        return new ResponseEntity<>(comentarioServicio.crearComentario(publicacionId,comentarioDTO)
        ,HttpStatus.CREATED);
    }

    //Actualizar comentarios
    @PutMapping("/publicaciones/{publicacionId}/comentarios/{comentarioId}")
    public ResponseEntity<ComentarioDTO> actualizarComentario(@PathVariable Long publicacionId,@PathVariable Long comentarioId, @RequestBody ComentarioDTO comentarioDTO){
        return new ResponseEntity<>(comentarioServicio.actualizarComentario(publicacionId,comentarioId ,comentarioDTO)
                ,HttpStatus.CREATED);
    }

    //Eliminar comentario
    @DeleteMapping("/publicaciones/{publicacionId}/comentarios/{comentarioId}")
    public ResponseEntity<String> eliminarComentario (@PathVariable Long publicacionId, @PathVariable Long comentarioId){
        comentarioServicio.eliminarComentario(publicacionId, comentarioId);
        return new ResponseEntity<>("Comentario eliminado", HttpStatus.OK);
    }
}
