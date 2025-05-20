package sl.sistemaInventarios.controlador.producto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sl.sistemaInventarios.dto.producto.ProductoCompletoDTO;
import sl.sistemaInventarios.dto.producto.ProductoDTO;
import sl.sistemaInventarios.modelo.producto.Producto;
import sl.sistemaInventarios.servicio.producto.clases.ProductoGestionServicio;
import sl.sistemaInventarios.servicio.producto.clases.ProductoLecturaServicio;

import java.util.List;

@RestController
@RequestMapping("inventario-app/productos")
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
            return ResponseEntity.status(400).body("Error: "+e);
        }
    }

    @GetMapping("/buscar/{idProducto}")
    public ResponseEntity<?> buscarProductoPorIdEnDTO(@PathVariable Integer idProducto){
       try{
           ProductoDTO productoEncontrado = this.productoLecturaServicio.buscarProductoPorIdADTO(idProducto);
           return ResponseEntity.ok(productoEncontrado);
       }catch (Exception e){
           return ResponseEntity.status(400).body("Error: "+e);
       }
    }

    @GetMapping("/buscar/detalles/{idProducto}")
    public ResponseEntity<?> buscarCategoriaPorIdConDetalles(@PathVariable Integer idProducto){
        try{
            ProductoCompletoDTO productoEncontradoCompleto = this.productoLecturaServicio.buscarProductoPorIdConDetalles(idProducto);
            return ResponseEntity.ok(productoEncontradoCompleto);
        }catch (Exception e){
            return ResponseEntity.status(400).body("Error: "+e);
        }
    }
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

    @GetMapping("/delete/{id}")
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

    @GetMapping("/recuperar/{id}")
    public ResponseEntity<?> recuperarProducto(@PathVariable Integer id){
        try {
            Producto productoEncontrado = this.productoLecturaServicio.buscarProductoPorId(id);
            if (productoEncontrado != null){
                Producto productoRecuperado = this.productoGestionServicio.recuperarProducto(productoEncontrado.getIdProducto());
                return ResponseEntity.ok("El producto ha sido recuperado correctamente");
            }else {
                return ResponseEntity.status(409).body("No se ha encontrado el producto eliminado");
            }
        }catch (Exception e){
            return ResponseEntity.status(400).body("error: "+e);
        }
    }

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
