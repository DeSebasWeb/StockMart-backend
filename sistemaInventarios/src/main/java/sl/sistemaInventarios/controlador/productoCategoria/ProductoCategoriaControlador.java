package sl.sistemaInventarios.controlador.productoCategoria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sl.sistemaInventarios.dto.producto.ProductoDTO;
import sl.sistemaInventarios.modelo.producto.Producto;
import sl.sistemaInventarios.modelo.productoCategoria.ProductoCategoria;
import sl.sistemaInventarios.servicio.producto.clases.ConvertidorProductoDTOServicio;
import sl.sistemaInventarios.servicio.producto.clases.ProductoLecturaServicio;
import sl.sistemaInventarios.servicio.productoCategoria.clases.ProductoCategoriaGestionServicio;
import sl.sistemaInventarios.servicio.productoCategoria.clases.ProductoCategoriaLecturaServicio;

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
            ProductoCategoria productoEliminado = this.productoCategoriaGestionServicio.softDelete(productoCategoria);
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
            this.productoCategoriaGestionServicio.recuperar(productoCategoria);
            return ResponseEntity.ok("Se ha recuperado el producto exitosamente");
        }catch (Exception e){
            return ResponseEntity.status(400).body("Error: "+e);
        }
    }

    @GetMapping("/asociados/{id}")
    public ResponseEntity<List<ProductoDTO>> obtenerProductosPorCategoria(@PathVariable Integer id){
        try {
            ProductoCategoria productoCategoria = new ProductoCategoria();
            productoCategoria.setId(id);
            List<Producto> productos = this.productoCategoriaLecturaServicio.productosAsociados(productoCategoria);
            List<ProductoDTO> productoDTOS = this.convertidorProductoDTOServicio.convertirLista(productos);
            return ResponseEntity.ok(productoDTOS);
        }catch (Exception e){
            throw new RuntimeException("Error: "+e);
        }
    }

    @DeleteMapping("/delete/hard/{id}")
    public ResponseEntity<?> hardDelete(@PathVariable Integer id){
        try {
            ProductoCategoria productoCategoria = new ProductoCategoria();
            productoCategoria.setId(id);
            this.productoCategoriaGestionServicio.hardDelete(productoCategoria);
            return ResponseEntity.ok("Se ha eliminado exitosamente");
        }catch (Exception e){
            return ResponseEntity.status(400).body("Error: "+e);
        }
    }
}