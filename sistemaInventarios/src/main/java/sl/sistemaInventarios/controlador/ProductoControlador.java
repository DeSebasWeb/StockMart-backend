package sl.sistemaInventarios.controlador;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sl.sistemaInventarios.excepcion.RecursoNoEncontradoExcepcion;
import sl.sistemaInventarios.modelo.Producto;
import sl.sistemaInventarios.servicio.ProductoServicio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PutMapping("/productos/{id}")
    //La notacion PutMapping funciona para  modificar un elemento
    public ResponseEntity<Producto> editarProducto(@PathVariable Integer id, @RequestBody Producto productoRecibido){
        Producto producto = this.productoServicio.buscarProductoPorId(id);
        producto.setDescripcion(productoRecibido.getDescripcion());
        producto.setCantExistencia(productoRecibido.getCantExistencia());
        producto.setPrecio(productoRecibido.getPrecio());
        producto.setCategoria(productoRecibido.getCategoria());
        this.productoServicio.guardarProducto(producto);
        return ResponseEntity.ok(producto);
    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarProducto(@PathVariable Integer id){
        Producto producto = this.productoServicio.buscarProductoPorId(id);
        if (producto == null){
            throw new RecursoNoEncontradoExcepcion("No se ha encontrado ningun recurso");
        }else {
            this.productoServicio.eliminarProducto(producto);
            Map<String, Boolean> respuesta = new HashMap<>();
            respuesta.put("eliminado", Boolean.TRUE);
            return ResponseEntity.ok(respuesta);
        }
    }

    @GetMapping("/productos/buscar/id/{id}")
    public ResponseEntity<Producto> buscarProductoConId(@PathVariable Integer id){
        Producto producto =  this.productoServicio.buscarProductoPorId(id);
        if (producto != null){
            return ResponseEntity.ok(producto);
        }else {
            throw new RecursoNoEncontradoExcepcion("No se ha encontrado el producto esperado");
        }
    }

    @GetMapping("/productos/buscar/categoria/{categoria}")
    public ResponseEntity<List<Producto>> buscarProductosPorCategoria(@PathVariable String categoria){
        List<Producto> productos = this.productoServicio.buscarPorCategoria(categoria);
        if (productos.isEmpty()){
            throw new RecursoNoEncontradoExcepcion("No se ha encontrado ningun producto con esa categoria");
        }else{
            return ResponseEntity.ok(productos);
        }
    }
}
