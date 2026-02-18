package com.sistema.blog.blog.dto;

import com.sistema.blog.blog.entities.Comentario;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicacionDTO {

    @NotEmpty
    @Size(min = 2, message = "El titulo de la publicacion deberia tener minimo 2 caracteres")
    private Long id;
    @NotEmpty
    @Size(min = 2, message = "La descripcion deberia tener al menos 10 caracteres")
    private String titulo;
    @NotEmpty
    private String descripcion;

    private String contenido;
    //Esto nos permitira retornar un SET de comentarios
    //Nos va a permitir var todos los comentarios asociados a una publicacion
    private Set<Comentario> comentariosSet;
}
