package com.stockmart.api.controller.producto;

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
import com.stockmart.api.dto.producto.ProductoCompletoDTO;
import com.stockmart.api.dto.producto.ProductoDTO;
import com.stockmart.api.entity.producto.Producto;
import com.stockmart.api.service.producto.clases.ProductoGestionServicio;
import com.stockmart.api.service.producto.clases.ProductoLecturaServicio;

import java.util.List;

@Tag(name = "Producto-controlador", description = "Operaciones CRUD sobre productos.")
@RestController
@RequestMapping("stockmart/productos")
@CrossOrigin("http://localhost:4200")
//Luego de pruebas iniciales, implementar las notaciones de seguridad por metodo para los roles
//    http://localhost:8080/inventario-app/productos
public class ProductoControlador {
    private static final Logger logger = LoggerFactory.getLogger(ProductoControlador.class);
    private final ProductoLecturaServicio productoLecturaServicio;
    private final ProductoGestionServicio productoGestionServicio;

    @Autowired
    public ProductoControlador(ProductoLecturaServicio productoLecturaServicio, ProductoGestionServicio productoGestionServicio) {
        this.productoLecturaServicio = productoLecturaServicio;
        this.productoGestionServicio = productoGestionServicio;
    }

    @Operation(
            summary = "Mapeado de todos los productos existentes",
            description = "Este endpoint muestra todos los productos existentes en la base de datos",
            tags = "Producto-controlador"
    )@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Productos que han sido encontrados",
                    content =  @Content(mediaType = "application/json",
                            schema =  @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "403", description = "Necesita autenticacion para usar este endpoint",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No se ha podido encontrar los productos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)

    })
    @GetMapping("/mostrar")
    public ResponseEntity<?> mapeoProductos(){
        try{
            List<ProductoDTO> productos = this.productoLecturaServicio.mostrarTodosLosProductos();
            if (productos.isEmpty()){
                logger.info("No hay productos para mostrar");
                return ResponseEntity.status(404).body("No se ha podido encontrar los productos");
            }else{
                return ResponseEntity.ok(productos);
            }
        }catch (Exception e){
            return ResponseEntity.status(500).body("Error: "+e);
        }
    }

    @Operation(
            summary = "Busqueda de un producto con su id",
            description = "Este endpoint muestra el producto con el id indicado en la base de datos",
            tags = "Producto-controlador"
    )@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "",
                    content =  @Content(mediaType = "application/json",
                            schema =  @Schema(implementation = ProductoDTO.class))),
            @ApiResponse(responseCode = "403", description = "Necesita autenticacion para usar este endpoint",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)

    })
    @GetMapping("/buscar/{idProducto}")
    public ResponseEntity<?> buscarProductoPorIdEnDTO(@PathVariable Integer idProducto){
       try{
           ProductoDTO productoEncontrado = this.productoLecturaServicio.buscarProductoPorIdADTO(idProducto);
           return ResponseEntity.ok(productoEncontrado);
       }catch (Exception e){
           return ResponseEntity.status(500).body("Error: "+e);
       }
    }

    @Operation(
            summary = "Busqueda de un producto con su Estado",
            description = "Este endpoint muestra productos filtrando su estado, es decir, si los productos a buscar estan marcados como ACTIVOS solo esos se van a mostrar",
            tags = "Producto-controlador"
    )@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "",
                    content =  @Content(mediaType = "application/json",
                            schema =  @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "403", description = "Necesita autenticacion para usar este endpoint",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)

    })
    @GetMapping("/estado/{idEstado}")
    public ResponseEntity<?> mostrarProductosPorEstado(@PathVariable Integer idEstado){
        try {
            List<ProductoDTO> productoDTOS = this.productoLecturaServicio.mostrarProductosPorEstado(idEstado);
            return ResponseEntity.ok(productoDTOS);
        }catch (Exception e){
            return ResponseEntity.status(500).body("Error: "+e);
        }
    }

    @Operation(
            summary = "Busqueda de un producto por su categoria",
            description = "Este endpoint muestra productos filtrando su categoria, es decir, si los productos a buscar estan categorizados como bebida solo esos se van a mostrar",
            tags = "Producto-controlador"
    )@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "",
                    content =  @Content(mediaType = "application/json",
                            schema =  @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "403", description = "Necesita autenticacion para usar este endpoint",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)

    })
    @GetMapping("/categoria/{nombreCategoria}")
    public ResponseEntity<?> buscarPorCategoria(@PathVariable String nombreCategoria){
        try {
            List<ProductoDTO> productosEncontradosDTOs = this.productoLecturaServicio.buscarPorCategoria(nombreCategoria);
            return ResponseEntity.ok(productosEncontradosDTOs);
        }catch (Exception e){
            return ResponseEntity.status(400).body("Error: "+e);
        }
    }

    @Operation(
            summary = "Busqueda de un producto con todos sus detalles",
            description = "Este endpoint muestra un producto con el id indicado en la base de datos con la diferencia que muestra todoso sus detalles, es decir muestra cuando se creo, cuando se modifico y cuando se desactivo",
            tags = "Producto-controlador"
    )@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "",
                    content =  @Content(mediaType = "application/json",
                            schema =  @Schema(implementation = ProductoCompletoDTO.class))),
            @ApiResponse(responseCode = "403", description = "Necesita autenticacion para usar este endpoint",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)

    })
    @GetMapping("/buscar/detalles/{idProducto}")
    public ResponseEntity<?> buscarProductoPorIdConDetalles(@PathVariable Integer idProducto){
        try{
            ProductoCompletoDTO productoEncontradoCompleto = this.productoLecturaServicio.buscarProductoPorIdConDetalles(idProducto);
            return ResponseEntity.ok(productoEncontradoCompleto);
        }catch (Exception e){
            return ResponseEntity.status(500).body("Error: "+e);
        }
    }
    @Operation(
            summary = "Guarda o actualiza",
            description = "Este endpoint guarda o actualiza cualquier producto que se haya enviado por el JSON, para guardar un nuevo producto, es necesario que no contenga ningun ID, en caso de actualizar un producto, especificar el id del mismo.",
            tags = "Producto-controlador"
    )@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "",
                    content =  @Content(mediaType = "application/json",
                            schema =  @Schema(implementation = ProductoCompletoDTO.class))),
            @ApiResponse(responseCode = "403", description = "Necesita autenticacion para usar este endpoint",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping("/guardar/producto")
    public ResponseEntity<?> guardarOActualizarProducto(@RequestBody Producto producto){
        try {
            Producto productoGuardado = this.productoGestionServicio.guardarOActualizarProducto(producto);
            if (productoGuardado == null){
                return ResponseEntity.status(404).body("No se ha podido procesar el dato enviado");
            }else{
                return ResponseEntity.ok("Se ha guardado correctamente el producto");
            }
        }catch (Exception e){
            return ResponseEntity.status(404).body("error: "+ e);
        }
    }

    @Operation(
            summary = "Recuperacion de un producto",
            description = "Este endpoint realiza una verificacion del producto que se quiere reactivar, es decir que si el producto se encuentra en estado: INACTIVO, en caso que sea asi, cambia su estado a Activo, en caso de que no, no permite la reactivacion",
            tags = "Producto-controlador"
    )@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "",
                    content =  @Content(mediaType = "application/json",
                            schema =  @Schema(implementation = ProductoCompletoDTO.class))),
            @ApiResponse(responseCode = "403", description = "Necesita autenticacion para usar este endpoint",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "No se ha encontrado el producto previamente eliminado",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Error: ",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })

    @PutMapping("/recuperar/{id}")
    public ResponseEntity<?> recuperarProducto(@PathVariable Integer id){
        try {
            Producto productoEncontrado = this.productoLecturaServicio.buscarProductoPorId(id);
            if (productoEncontrado != null){
                Producto productoRecuperado = this.productoGestionServicio.recuperarProducto(productoEncontrado.getIdProducto());
                return ResponseEntity.ok("El producto ha sido recuperado correctamente");
            }else {
                return ResponseEntity.status(409).body("No se ha encontrado el producto previamente eliminado");
            }
        }catch (Exception e){
            return ResponseEntity.status(400).body("error: "+e);
        }
    }

    @Operation(
            summary = "Soft Delete a un producto",
            description = "Este endpoint realiza una eliminacion logica, cambiando su estado a INACTIVO",
            tags = "Producto-controlador"
    )@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "",
                    content =  @Content(mediaType = "application/json",
                            schema =  @Schema(implementation = ProductoCompletoDTO.class))),
            @ApiResponse(responseCode = "403", description = "Necesita autenticacion para usar este endpoint",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PutMapping("/delete/{id}")
    public ResponseEntity<?> softDelete(@PathVariable Integer id){
        try{
            Producto productoEncontrado = this.productoLecturaServicio.buscarProductoPorId(id);
            if (productoEncontrado != null){
                Producto productoEliminado = this.productoGestionServicio.softDelete(productoEncontrado.getIdProducto());
                if (productoEliminado!=null){
                    return ResponseEntity.ok("Se ha eliminado correctamente el producto");
                }else{
                    return ResponseEntity.status(400).body("Ya esta eliminado el producto");
                }
            }else {
                return ResponseEntity.status(404).body("No hay ningun dato");
            }
        }catch (Exception e){
            return ResponseEntity.status(400).body("error: "+e);
        }
    }

    @Operation(
            summary = "Hard delete de un producto",
            description = "Este endpoint realiza una verificacion antes de hacer un hard delete, primero verifica que el producto a eliminar se encuentra en estado INACTIVO, si es asi, procede con el Hard Delete, en caso que no, no lo permite.",
            tags = "Producto-controlador"
    )@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "",
                    content =  @Content(mediaType = "application/json",
                            schema =  @Schema(implementation = ProductoCompletoDTO.class))),
            @ApiResponse(responseCode = "403", description = "Necesita autenticacion para usar este endpoint",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @DeleteMapping("/delete/hard/{id}")
    public ResponseEntity<?> hardDelete(@PathVariable Integer id){
        try {
            this.productoGestionServicio.hardDelete(id);
            return ResponseEntity.ok("El producto se ha eliminado correctamente");
        }catch (Exception e){
            return ResponseEntity.status(400).body("error: "+ e);
        }
    }
}