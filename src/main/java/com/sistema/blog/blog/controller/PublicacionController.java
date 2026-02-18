package com.sistema.blog.blog.controller;

import com.sistema.blog.blog.dto.PublicacionDTO;
import com.sistema.blog.blog.dto.PublicacionRespuesta;
import com.sistema.blog.blog.service.PublicacionServicio;
import com.sistema.blog.blog.util.AppConstantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionController {

    @Autowired
    private PublicacionServicio publicacionServicio;

    //Obtener todas las publicaciones con paginacion
    @GetMapping
    public PublicacionRespuesta listarPublicaciones(@RequestParam(value = "numeroPagina",defaultValue = AppConstantes.NUMERO_DE_PAGINA_POR_DEFECTO,required = false) int numeroDePagina,
                                                    @RequestParam(value = "pageSize",defaultValue = AppConstantes.MEDIDA_DE_PAGINA_POR_DEFECTO,required = false)int medidaDeLaPagina,
                                                    @RequestParam(value = "sortBy",defaultValue = AppConstantes.ORDENAR_POR_DEFECTO,required = false)String ordenarPor,
                                                    @RequestParam(value = "sortDir",defaultValue = AppConstantes.ORDENAR_DIRECCION_POR_DEFECTO,required = false)String sorDir){
        return publicacionServicio.obtenerPublicaciones(numeroDePagina,medidaDeLaPagina,ordenarPor,sorDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicacionDTO> obtenerPublicacionPorId(@PathVariable Long id){
        return ResponseEntity.ok(publicacionServicio.obtenerPublicacionPorId(id));
    }

    @PostMapping
    public ResponseEntity<PublicacionDTO> guardarPublicacion(@RequestBody PublicacionDTO publicacionDTO){
        return new ResponseEntity<>(publicacionServicio.crearPublicacion(publicacionDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublicacionDTO> actualizarPublicacion(@RequestBody PublicacionDTO publicacionDTO, @PathVariable Long id){
        PublicacionDTO publicacionRespuesta = publicacionServicio.actualizarPublicacion(publicacionDTO,id);
        return new ResponseEntity<>(publicacionRespuesta,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPublicacion (@PathVariable Long id) {
        publicacionServicio.eliminarPublicacion(id);
        return new ResponseEntity<>("Publicacion eliminada con exito",HttpStatus.OK);
    }
}
