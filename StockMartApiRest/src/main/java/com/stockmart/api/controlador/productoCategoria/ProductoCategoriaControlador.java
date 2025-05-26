package com.stockmart.api.controlador.productoCategoria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.stockmart.api.dto.producto.ProductoDTO;
import com.stockmart.api.dto.productoCategoria.ProductoCategoriaDTO;
import com.stockmart.api.modelo.productoCategoria.ProductoCategoria;
import com.stockmart.api.servicio.producto.clases.ConvertidorProductoDTOServicio;
import com.stockmart.api.servicio.producto.clases.ProductoLecturaServicio;
import com.stockmart.api.servicio.productoCategoria.clases.ProductoCategoriaGestionServicio;
import com.stockmart.api.servicio.productoCategoria.clases.ProductoCategoriaLecturaServicio;

import java.util.List;

@RestController
@RequestMapping("inventario-app/categorias/productos")
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

    @GetMapping("/mostrar")
    public ResponseEntity<?> mapeoCategorias(){
        try{
            List<ProductoCategoriaDTO> categorias = this.productoCategoriaLecturaServicio.mostrarTodasCategorias();
            return ResponseEntity.ok(categorias);
        }catch (Exception e){
            return ResponseEntity.status(400).body("Error: "+e);
        }
    }
    @PostMapping("/guardar")
    public ResponseEntity<?> guardarOActualizarCategoria(@RequestBody ProductoCategoria productoCategoria){
        try {
            ProductoCategoria productoGuardado = this.productoCategoriaGestionServicio.guardarOActualizarCategoria(productoCategoria);
            if (productoGuardado == null){
                return ResponseEntity.status(404).body("No se ha podido guardar la categoria");
            }else {
                return ResponseEntity.ok("La categoria se ha guardado correctamente");
            }
        }catch (Exception e){
            return ResponseEntity.status(400).body("error: "+e);
        }
    }

    @GetMapping("/delete/{id}")
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

    @GetMapping("/recuperar/{id}")
    public ResponseEntity<?> recuperar(@PathVariable Integer id){
        try{
            ProductoCategoria productoCategoria = new ProductoCategoria();
            productoCategoria.setId(id);
            this.productoCategoriaGestionServicio.recuperar(id);
            return ResponseEntity.ok("Se ha recuperado el producto exitosamente");
        }catch (Exception e){
            return ResponseEntity.status(400).body("Error: "+e);
        }
    }

    @GetMapping("/asociados/{id}")
    public ResponseEntity<List<ProductoDTO>> obtenerProductosPorCategoria(@PathVariable Integer id){
        try {
            List<ProductoDTO> productos = this.productoCategoriaLecturaServicio.productosAsociados(id);
            return ResponseEntity.ok(productos);
        }catch (Exception e){
            throw new RuntimeException("Error: "+e);
        }
    }

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