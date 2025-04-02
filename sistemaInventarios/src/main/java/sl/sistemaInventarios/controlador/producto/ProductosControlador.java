package sl.sistemaInventarios.controlador.producto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sl.sistemaInventarios.modelo.productos.Productos;
import sl.sistemaInventarios.servicio.productos.ProductosServicio;

import java.util.List;

@RestController
@RequestMapping("inventario-app/productos")
@CrossOrigin("http://localhost:4200")
//    http://localhost:8080/inventario-app/productos
public class ProductosControlador {
    private static final Logger logger = LoggerFactory.getLogger(ProductosControlador.class);
    @Autowired
    private ProductosServicio productosServicio;

    @GetMapping("/mostrar")
    public List<Productos> mostrarProductos(){
        List<Productos> productos = this.productosServicio.mostrarProductos();
        logger.info("Porductos obtenidos: ");
        productos.forEach(producto -> {
            logger.info(producto.toString());
        });
        return productos;
    }
}
