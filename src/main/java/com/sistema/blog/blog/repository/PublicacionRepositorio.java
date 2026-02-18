package com.sistema.blog.blog.repository;

import com.sistema.blog.blog.entities.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicacionRepositorio extends JpaRepository<Publicacion, Long> {

}
