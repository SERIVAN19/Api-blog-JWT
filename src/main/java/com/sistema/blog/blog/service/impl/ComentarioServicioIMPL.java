package com.sistema.blog.blog.service.impl;

import com.sistema.blog.blog.dto.ComentarioDTO;
import com.sistema.blog.blog.entities.Comentario;
import com.sistema.blog.blog.entities.Publicacion;
import com.sistema.blog.blog.exceptions.ResourceNotFoundException;
import com.sistema.blog.blog.exceptions.blogAppExeption;
import com.sistema.blog.blog.repository.ComntarioRepositorio;
import com.sistema.blog.blog.repository.PublicacionRepositorio;
import com.sistema.blog.blog.service.ComentarioServicio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComentarioServicioIMPL implements ComentarioServicio {

    //Inyeccion de ModelMapper
    @Autowired
    private ModelMapper modelMapper;
    //Inyeccion del repositorio comentario
    @Autowired
    private ComntarioRepositorio comntarioRepositorio;
    //Inyeccion del repositorio publicacion
    @Autowired
    private PublicacionRepositorio publicacionRepositorio;



    @Override
    public ComentarioDTO crearComentario(Long publicacionId, ComentarioDTO comentarioDTO) {
        Comentario comentario = mapearEntidad(comentarioDTO);
        Publicacion publicacion = publicacionRepositorio.findById(publicacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));
        comentario.setPublicacion(publicacion);
        Comentario nuevoComentario = comntarioRepositorio.save(comentario);
        return mapearDTO(nuevoComentario);
    }

    @Override
    public List<ComentarioDTO> obtenerComentariosPorPublicacionId(Long publicacionId) {
        List<Comentario> comentarios = comntarioRepositorio.findByPublicacionId(publicacionId);
        return comentarios.stream().map(comentario -> mapearDTO(comentario)).collect(Collectors.toList());
    }

    @Override
    public ComentarioDTO obtenerComentarioPorId(Long publicacionId, Long comentarioId) {
        Publicacion publicacion = publicacionRepositorio.findById(publicacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));
        Comentario comentario = comntarioRepositorio.findById(comentarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentarioId));
        if(!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new blogAppExeption(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicacion");
        }
        return mapearDTO(comentario);
    }

    @Override
    public ComentarioDTO actualizarComentario(Long publicacionId, Long comentarioId, ComentarioDTO solicitudDeComentario) {
        Publicacion publicacion = publicacionRepositorio.findById(publicacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));
        Comentario comentario = comntarioRepositorio.findById(comentarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentarioId));
        if(!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new blogAppExeption(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicacion");
        }
        comentario.setNombre(solicitudDeComentario.getNombre());
        comentario.setEmail(solicitudDeComentario.getEmail());
        comentario.setCuerpo(solicitudDeComentario.getCuerpo());

        Comentario comentarioActualizado = comntarioRepositorio.save(comentario);
        return mapearDTO(comentarioActualizado);
    }

    @Override
    public void eliminarComentario(Long publicacionId, Long comentarioId) {
        Publicacion publicacion = publicacionRepositorio.findById(publicacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));
        Comentario comentario = comntarioRepositorio.findById(comentarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentarioId));
        if(!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new blogAppExeption(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicacion");
        }
        comntarioRepositorio.delete(comentario);
    }

    //MAPEO usando ModelMapper

    //Mapeando comentarioDTO
    private ComentarioDTO mapearDTO(Comentario comentario){
        ComentarioDTO comentarioDTO = modelMapper.map(comentario ,ComentarioDTO.class);
        /*ComentarioDTO comentarioDTO = new ComentarioDTO();
        comentarioDTO.setId(comentario.getId());
        comentarioDTO.setNombre(comentario.getNombre());
        comentarioDTO.setEmail(comentario.getEmail());
        comentarioDTO.setCuerpo(comentario.getCuerpo());*/
        return comentarioDTO;
    }
    //Mapeando entidad
    private Comentario mapearEntidad(ComentarioDTO comentarioDTO){
        Comentario comentario = modelMapper.map(comentarioDTO, Comentario.class);
        /*  Comentario comentario = new Comentario();
        comentario.setId(comentarioDTO.getId());
        comentario.setNombre(comentarioDTO.getNombre());
        comentario.setEmail(comentarioDTO.getEmail());
        comentario.setCuerpo(comentarioDTO.getCuerpo()); */
        return comentario;
    }
}
