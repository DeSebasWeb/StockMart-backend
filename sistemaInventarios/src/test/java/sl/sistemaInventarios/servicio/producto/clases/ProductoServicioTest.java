package sl.sistemaInventarios.servicio.producto.clases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import sl.sistemaInventarios.modelo.estado.Estado;
import sl.sistemaInventarios.modelo.estado.EstadoEnum;
import sl.sistemaInventarios.modelo.producto.Producto;
import sl.sistemaInventarios.repositorio.producto.ProductoRepositorio;
import sl.sistemaInventarios.servicio.estado.clases.EstadoServicio;

@ExtendWith(MockitoExtension.class)
public class ProductoServicioTest {
    @Mock
    private ProductoRepositorio productoRepositorio;

    @Mock
    private EstadoServicio estadoServicio;

    @InjectMocks
    private ProductoLecturaServicio productoServicio;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void softDeleteTest(){
        Producto producto = new Producto();
        producto.setIdProducto(1);
        Estado estadoActivo = new Estado();
        estadoActivo.setIdEstado(1);
        estadoActivo.setEstado(EstadoEnum.ACTIVO);

        producto.setEstado(estadoActivo);


    }


}
