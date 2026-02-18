package com.sistema.blog.blog.service;


import com.sistema.blog.blog.dto.PublicacionDTO;
import com.sistema.blog.blog.dto.PublicacionRespuesta;


public interface PublicacionServicio {

    public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO);

    public PublicacionRespuesta obtenerPublicaciones(int numeroDePgina, int medidaDePgina,String ordenarPor, String sortDir);

    public PublicacionDTO obtenerPublicacionPorId(Long id);

    public PublicacionDTO actualizarPublicacion (PublicacionDTO publicacionDTO, Long id);

    public void eliminarPublicacion(Long id);
}
