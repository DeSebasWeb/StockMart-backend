package sl.sistemaInventarios.servicio.productoCategoria.interfaces;

import sl.sistemaInventarios.dto.producto.ProductoDTO;
import sl.sistemaInventarios.dto.productoCategoria.ProductoCategoriaCompletoDTO;
import sl.sistemaInventarios.dto.productoCategoria.ProductoCategoriaDTO;
import sl.sistemaInventarios.modelo.productoCategoria.ProductoCategoria;
import sl.sistemaInventarios.modelo.estado.EstadoEnum;

import java.util.List;

public interface IProductoCategoriaLecturaServicio {
    public List<ProductoCategoriaDTO> mostrarTodasCategorias();

    public List<ProductoCategoriaDTO> mostrarCategoriasEstado(EstadoEnum estadoEnum);

    public ProductoCategoria buscarCategoriaPorId(Integer idCategoria);

    public ProductoCategoriaDTO buscarCategoriaPorIdADTO(Integer idCategoria);

    public List<ProductoDTO> productosAsociados(Integer idCategoria);

    public ProductoCategoriaCompletoDTO mostrarDetallesProductoCategoria(Integer idCategoria);
}
