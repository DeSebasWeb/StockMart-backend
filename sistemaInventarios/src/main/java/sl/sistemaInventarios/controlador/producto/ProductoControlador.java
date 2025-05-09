package sl.sistemaInventarios.controlador.producto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public List<Producto> mapeoProductos(){
        List<Producto> productos = this.productoLecturaServicio.mostrarTodosLosProductos();
        if (productos.isEmpty()){
            logger.info("No hay productos para mostrar");
            return null;
        }else{
            return productos;
        }
    }

    @PostMapping("/agregar")
    public ResponseEntity<?> agregarProducto(@RequestBody Producto producto){
        try {
            Producto productoGuardado = this.productoGestionServicio.guardarProducto(producto);
            if (productoGuardado == null){
                return ResponseEntity.status(404).body("No se ha podido procesar el dato enviado");
            }else{
                return ResponseEntity.ok("Se ha guardado correctamente el producto");
            }
        }catch (Exception e){
            return ResponseEntity.status(404).body("error: "+ e.toString());
        }
    }
}
