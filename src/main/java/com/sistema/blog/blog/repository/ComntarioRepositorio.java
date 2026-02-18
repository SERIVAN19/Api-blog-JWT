package com.sistema.blog.blog.repository;

import com.sistema.blog.blog.entities.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComntarioRepositorio extends JpaRepository<Comentario, Long> {

    public List<Comentario> findByPublicacionId(Long publicacionId);

}
