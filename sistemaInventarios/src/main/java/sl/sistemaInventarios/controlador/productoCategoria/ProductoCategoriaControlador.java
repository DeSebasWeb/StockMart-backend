package sl.sistemaInventarios.controlador.productoCategoria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sl.sistemaInventarios.servicio.productoCategoria.clases.ProductoCategoriaGestionServicio;
import sl.sistemaInventarios.servicio.productoCategoria.clases.ProductoCategoriaLecturaServicio;

@RestController
@RequestMapping("categorias/productos/")
@CrossOrigin("http://localhost:4200")
public class ProductoCategoriaControlador {
    private static final Logger logger = LoggerFactory.getLogger(ProductoCategoriaControlador.class);

    private final ProductoCategoriaLecturaServicio productoCategoriaLecturaServicio;
    private final ProductoCategoriaGestionServicio productoCategoriaGestionServicio;

    @Autowired
    public ProductoCategoriaControlador(ProductoCategoriaLecturaServicio productoCategoriaLecturaServicio, ProductoCategoriaGestionServicio productoCategoriaGestionServicio) {
        this.productoCategoriaLecturaServicio = productoCategoriaLecturaServicio;
        this.productoCategoriaGestionServicio = productoCategoriaGestionServicio;
    }



}
