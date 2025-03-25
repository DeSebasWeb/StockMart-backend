package sl.sistemaInventarios.controlador;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sl.sistemaInventarios.excepcion.RecursoNoEncontradoExcepcion;
import sl.sistemaInventarios.modelo.Producto;
import sl.sistemaInventarios.servicio.ProductoServicio;

import java.util.List;

@RestController
//http://localhost:8080/inventario-app
@RequestMapping("inventario-app")
@CrossOrigin(value = "http://localhost:4200")
public class ProductoControlador {
    private static final Logger logger = LoggerFactory.getLogger(ProductoControlador.class);
    @Autowired
    private ProductoServicio productoServicio;

//    http://localhost:8080/inventario-app/productos
    @GetMapping("/productos")
    public List<Producto> obtenerProductos(){
        List<Producto> productos = productoServicio.mostrarProductos();
        logger.info("Productos obtenidos: ");
        productos.forEach(producto -> logger.info(producto.toString()));
        return productos;
    }
    @PostMapping("/productos")
    public Producto agregarProducto(@RequestBody Producto producto){
        logger.info("Producto a agregar "+producto);
        return this.productoServicio.guardarProducto(producto);
    }
    //metodo para encontrar un nuevo producto, donde el GetMapping se le pone una variable {id}
    @GetMapping("/productos/{id}")
    //La notacion PathVariable es el que obtiene la variable de la peticion http
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable int id){
        Producto producto = this.productoServicio.buscarProductoPorId(id);
        if (producto!= null){
            logger.info("Usuario encontrado" + producto.toString());
            return ResponseEntity.ok(producto);
        }
        else {
            //Cree una nueva clase donde se pasan las excepciones para mostrarlas
            throw new RecursoNoEncontradoExcepcion("No se ha encontrado ningun producto");
        }
    }


}
