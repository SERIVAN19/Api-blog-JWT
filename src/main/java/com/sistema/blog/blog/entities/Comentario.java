package com.sistema.blog.blog.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comentarios")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String cuerpo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publicacion_id", nullable = false)//Crea el atributo en la tabla de
    @ToString.Exclude // Evita ciclos infinitos
    @EqualsAndHashCode.Exclude // Evita ciclos infinitos
    private Publicacion publicacion;
}
