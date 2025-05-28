package com.stockmart.api.controller.productoCategoria;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.stockmart.api.dto.producto.ProductoDTO;
import com.stockmart.api.dto.productoCategoria.ProductoCategoriaDTO;
import com.stockmart.api.entity.productoCategoria.ProductoCategoria;
import com.stockmart.api.service.producto.clases.ConvertidorProductoDTOServicio;
import com.stockmart.api.service.producto.clases.ProductoLecturaServicio;
import com.stockmart.api.service.productoCategoria.clases.ProductoCategoriaGestionServicio;
import com.stockmart.api.service.productoCategoria.clases.ProductoCategoriaLecturaServicio;

import java.util.List;

@Tag(name = "Categoria-controlador", description = "Operaciones CRUD sobre categorias.")
@RestController
@RequestMapping("stockmart/categorias")
@CrossOrigin("http://localhost:4200")
public class ProductoCategoriaControlador {
    private static final Logger logger = LoggerFactory.getLogger(ProductoCategoriaControlador.class);
    private final ProductoCategoriaLecturaServicio productoCategoriaLecturaServicio;
    private final ProductoCategoriaGestionServicio productoCategoriaGestionServicio;
    private final ProductoLecturaServicio productoLecturaServicio;
    private final ConvertidorProductoDTOServicio convertidorProductoDTOServicio;

    @Autowired
    public ProductoCategoriaControlador(ConvertidorProductoDTOServicio convertidorProductoDTOServicio, ProductoCategoriaLecturaServicio productoCategoriaLecturaServicio, ProductoLecturaServicio productoLecturaServicio, ProductoCategoriaGestionServicio productoCategoriaGestionServicio) {
        this.productoCategoriaLecturaServicio = productoCategoriaLecturaServicio;
        this.productoCategoriaGestionServicio = productoCategoriaGestionServicio;
        this.productoLecturaServicio = productoLecturaServicio;
        this.convertidorProductoDTOServicio = convertidorProductoDTOServicio;
    }

    @Operation(
            summary = "Mapeado de todos las categoria existentes",
            description = "Este endpoint muestra todos las categorias existentes en la base de datos",
            tags = "Categoria-controlador"
    )@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categorias que han sido encontradas en la DB",
                    content =  @Content(mediaType = "application/json",
                            schema =  @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "403", description = "Necesita autenticacion para usar este endpoint",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No se ha podido encontrar las categorias", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/mostrar")
    public ResponseEntity<?> mapeoCategorias(){
        try{
            List<ProductoCategoriaDTO> categorias = this.productoCategoriaLecturaServicio.mostrarTodasCategorias();
            return ResponseEntity.ok(categorias);
        }catch (Exception e){
            return ResponseEntity.status(400).body("Error: "+e);
        }
    }
    @Operation(
            summary = "Guardado de categorias",
            description = "Este endpoint almacena la categoria enviada por medio del archivo JSON en la base de datos",
            tags = "Categoria-controlador"
    )@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La categoria se ha guardado correctamente", content =  @Content),
            @ApiResponse(responseCode = "403", description = "Necesita autenticacion para usar este endpoint", content = @Content),
            @ApiResponse(responseCode = "404", description = "No se ha podido guardar la categoria", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping("/guardar")
    public ResponseEntity<?> guardarCategoria(@RequestBody ProductoCategoria productoCategoria){
        try {
            ProductoCategoria categoriaGuardada = this.productoCategoriaGestionServicio.guardarOActualizarCategoria(productoCategoria);
            if (categoriaGuardada == null){
                return ResponseEntity.status(404).body("No se ha podido guardar la categoria");
            }else {
                return ResponseEntity.ok("La categoria se ha guardado correctamente");
            }
        }catch (Exception e){
            return ResponseEntity.status(500).body("error: "+e);
        }
    }

    @Operation(
            summary = "Actualizar la categoria",
            description = "Este endpoint actualiza la categoria enviada por medio del archivo JSON en la base de datos",
            tags = "Categoria-controlador"
    )@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La categoria se ha actualizado correctamente", content =  @Content),
            @ApiResponse(responseCode = "403", description = "Necesita autenticacion para usar este endpoint", content = @Content),
            @ApiResponse(responseCode = "404", description = "No se ha podido actualizado la categoria", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PutMapping("/guardar")
    public ResponseEntity<?> actualizarCategoria(@PathVariable Integer idCategoria, @RequestBody ProductoCategoria productoCategoria){
        try {
            ProductoCategoria categoriaGuardada = this.productoCategoriaGestionServicio.guardarOActualizarCategoria(productoCategoria);
            if (categoriaGuardada == null){
                return ResponseEntity.status(404).body("No se ha podido actualizar la categoria");
            }else {
                return ResponseEntity.ok("La categoria se ha actualizado correctamente");
            }
        }catch (Exception e){
            return ResponseEntity.status(500).body("Error: "+e);
        }
    }

    @Operation(
            summary = "Eliminacion logica de una categoria",
            description = "Este endpoint realiza una eliminacion logica a una categoria. Pero antes, para no hacer consultas ni movimientos inecesarios en la DB, hace una validacion para saber si la categoria seleccionada se encuentre en estado: ACTIVO, y asi proseguir con el proceso. en caso que se encuentre en un estado diferente, no prosigue con el proceso. ",
            tags = "Categoria-controlador"
    )@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La categoria se ha actualizado correctamente", content =  @Content),
            @ApiResponse(responseCode = "403", description = "Necesita autenticacion para usar este endpoint", content = @Content),
            @ApiResponse(responseCode = "404", description = "No se ha podido actualizado la categoria", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PutMapping("/delete/{id}")
    public  ResponseEntity<?> softDelete(@PathVariable Integer id){
        try{
            ProductoCategoria productoCategoria = new ProductoCategoria();
            productoCategoria.setId(id);
            ProductoCategoria productoEliminado = this.productoCategoriaGestionServicio.softDelete(id);
            if(productoEliminado == null){
                return ResponseEntity.status(404).body("No se ha podido eliminar la categoria");
            }else {
                return ResponseEntity.ok("Se ha eliminado la categoria correctamente");
            }
        }catch (Exception e){
            return ResponseEntity.status(400).body("error: "+e);
        }
    }

    @Operation(
            summary = "Recuperacion de una categoria",
            description = "Este endpoint actualiza el estado de una categoria, haciendo la respectiva validacion para saber si la categoria realmente se encuentra en estado INACTIVO y asi no hacer ninguna consulta inecesaria.",
            tags = "Categoria-controlador"
    )@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se ha recuperado el producto exitosamente", content =  @Content),
            @ApiResponse(responseCode = "400", description = "No se ha podido recuperar el producto, verifique el estado del mismo", content = @Content),
            @ApiResponse(responseCode = "403", description = "Necesita autenticacion para usar este endpoint", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PutMapping("/recuperar/{id}")
    public ResponseEntity<?> recuperar(@PathVariable Integer id){
        try{
            ProductoCategoria productoCategoria = new ProductoCategoria();
            productoCategoria.setId(id);
            ProductoCategoria productoCategoriaRecuperado = this.productoCategoriaGestionServicio.recuperar(id);
            if (productoCategoriaRecuperado!= null){
                return ResponseEntity.ok("Se ha recuperado el producto exitosamente");
            }else {
                return ResponseEntity.status(400).body("No se ha podido recuperar el producto, verifique el estado del mismo");
            }
        }catch (Exception e){
            return ResponseEntity.status(500).body("Error: "+e);
        }
    }

    @Operation(
            summary = "Productos asociados por la categoria",
            description = "Este endpoint actualiza la categoria enviada por medio del archivo JSON en la base de datos",
            tags = "Categoria-controlador"
    )@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se encuentran todos los productos relacionados a una categoria",
                    content =  @Content(mediaType = "application/json", schema =  @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "403", description = "Necesita autenticacion para usar este endpoint", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/{id}/productos")
    public ResponseEntity<?> obtenerProductosPorCategoria(@PathVariable Integer id){
        try {
            List<ProductoDTO> productos = this.productoCategoriaLecturaServicio.productosAsociados(id);
            return ResponseEntity.ok(productos);
        }catch (Exception e){
            return ResponseEntity.status(400).body("Error: "+e);
        }
    }

    @Operation(
            summary = "Eliminar una categoria",
            description = "Este endpoint verifica el estado de la categoria para asi poder eliminarla, en caso que su estado sea diferente a INACTIVO, no procedera a hacerle la orden a la DB, adicional a eso, en caso que se elimine una categoria, se eliminaran todos los productos relacionados",
            tags = "Categoria-controlador"
    )@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se ha eliminado correctamente la categoria", content =  @Content),
            @ApiResponse(responseCode = "403", description = "Necesita autenticacion para usar este endpoint", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @DeleteMapping("/delete/hard/{id}")
    public ResponseEntity<?> hardDelete(@PathVariable Integer id){
        try {
            ProductoCategoria productoCategoria = new ProductoCategoria();
            productoCategoria.setId(id);
            this.productoCategoriaGestionServicio.hardDelete(id);
            return ResponseEntity.ok("Se ha eliminado exitosamente");
        }catch (Exception e){
            return ResponseEntity.status(400).body("Error: "+e);
        }
    }
}