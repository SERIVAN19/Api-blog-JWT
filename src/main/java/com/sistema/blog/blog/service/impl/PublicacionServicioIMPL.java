package com.sistema.blog.blog.service.impl;

import com.sistema.blog.blog.dto.PublicacionDTO;
import com.sistema.blog.blog.dto.PublicacionRespuesta;
import com.sistema.blog.blog.entities.Publicacion;
import com.sistema.blog.blog.exceptions.ResourceNotFoundException;
import com.sistema.blog.blog.repository.PublicacionRepositorio;
import com.sistema.blog.blog.service.PublicacionServicio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicacionServicioIMPL implements PublicacionServicio {

    //Inyeccion de ModelMapper
    @Autowired
    private ModelMapper modelMapper;

    //Inyeccion del repositorio publicacion
    @Autowired
    private PublicacionRepositorio publicacionRepositorio;

    //Crear una nueva publicacion
    @Override
    public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO) {

        Publicacion publicacion = mapearEntidad(publicacionDTO);
        Publicacion nuevaPublicacion = publicacionRepositorio.save(publicacion);

        PublicacionDTO publicacionRespuesta = mapearDTO(nuevaPublicacion);
        return publicacionRespuesta;
    }

    //Listar publicaciones
    @Override
    public PublicacionRespuesta obtenerPublicaciones(int numeroDePgina, int medidaDePgina, String ordenarPor, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(ordenarPor).ascending():Sort.by(ordenarPor).descending();
        Pageable pageable = PageRequest.of(numeroDePgina, medidaDePgina, sort);
        Page<Publicacion> publicacions = publicacionRepositorio.findAll(pageable);

        List<Publicacion> listaPublicaciones = publicacions.getContent();
        List<PublicacionDTO> contenido = listaPublicaciones.stream().map(publicacion -> mapearDTO(publicacion)).collect(Collectors.toList());

        PublicacionRespuesta publicacionRespuesta = new PublicacionRespuesta();
        publicacionRespuesta.setContenido(contenido);
        publicacionRespuesta.setNumeroPagina(numeroDePgina);
        publicacionRespuesta.setMedidaPagina(publicacions.getNumber());
        publicacionRespuesta.setMedidaPagina(publicacions.getSize());
        publicacionRespuesta.setTotalElementos(publicacions.getTotalElements());
        publicacionRespuesta.setTotalPaginas(publicacions.getTotalPages());
        publicacionRespuesta.setUltima(publicacions.isLast());

        return publicacionRespuesta;
    }

    //Buscar publicacion por id
    @Override
    public PublicacionDTO obtenerPublicacionPorId(Long id) {
        Publicacion publicacion = publicacionRepositorio
                .findById(id).orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));
        return mapearDTO(publicacion);
    }

    //Actualizar publicacion
    @Override
    public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO, Long id) {
        Publicacion publicacion = publicacionRepositorio
                .findById(id).orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));

        publicacion.setTitulo(publicacionDTO.getTitulo());
        publicacion.setDescripcion(publicacionDTO.getDescripcion());
        publicacion.setContenido(publicacionDTO.getContenido());

        Publicacion publicacionActualizada = publicacionRepositorio.save(publicacion);
        return mapearDTO(publicacionActualizada);
    }

    //Eliminar publicacion
    @Override
    public void eliminarPublicacion(Long id) {
        Publicacion publicacion = publicacionRepositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));
        publicacionRepositorio.delete(publicacion);
    }

    //MAPEO usando ModelMapper


    //Convierte de entidad a DTO
    private PublicacionDTO mapearDTO(Publicacion publicacion){
        //Usando ModelMapper
        PublicacionDTO publicacionDTO = modelMapper.map(publicacion, PublicacionDTO.class);
        /*PublicacionDTO publicacionDTO = new PublicacionDTO();
        publicacionDTO.setId(publicacion.getId());
        publicacionDTO.setTitulo(publicacion.getTitulo());
        publicacionDTO.setDescripcion(publicacion.getDescripcion());
        publicacionDTO.setContenido(publicacion.getContenido());*/
        return publicacionDTO;
    }

    //Convierte de DTO a entidad
    private Publicacion mapearEntidad(PublicacionDTO publicacionDTO){
        Publicacion publicacion = modelMapper.map(publicacionDTO, Publicacion.class);
        /*Publicacion publicacion = new Publicacion();
        publicacion.setTitulo(publicacionDTO.getTitulo());
        publicacion.setDescripcion(publicacionDTO.getDescripcion());
        publicacion.setContenido(publicacionDTO.getContenido());*/
        return publicacion;
    }
}