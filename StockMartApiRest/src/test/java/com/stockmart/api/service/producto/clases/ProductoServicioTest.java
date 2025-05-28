package com.stockmart.api.service.producto.clases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import com.stockmart.api.entity.estado.Estado;
import com.stockmart.api.entity.estado.EstadoEnum;
import com.stockmart.api.entity.producto.Producto;
import com.stockmart.api.repository.producto.ProductoRepositorio;
import com.stockmart.api.service.estado.clases.EstadoGestionServicio;

@ExtendWith(MockitoExtension.class)
public class ProductoServicioTest {
    @Mock
    private ProductoRepositorio productoRepositorio;

    @Mock
    private EstadoGestionServicio estadoServicio;

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
