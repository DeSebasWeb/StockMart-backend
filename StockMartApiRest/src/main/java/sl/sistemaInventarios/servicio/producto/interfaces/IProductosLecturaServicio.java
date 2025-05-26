package sl.sistemaInventarios.servicio.producto.interfaces;

import sl.sistemaInventarios.dto.producto.ProductoCompletoDTO;
import sl.sistemaInventarios.dto.producto.ProductoDTO;
import sl.sistemaInventarios.modelo.estado.Estado;
import sl.sistemaInventarios.modelo.estado.EstadoEnum;
import sl.sistemaInventarios.modelo.producto.Producto;

import java.util.List;

public interface IProductosLecturaServicio {
    public List<ProductoDTO> mostrarProductosPorEstado(Integer idEstado);

    public List<ProductoDTO> mostrarTodosLosProductos();

    public Producto buscarProductoPorId(Integer idProducto);

    public ProductoDTO buscarProductoPorIdADTO(Integer idProducto);

    public ProductoCompletoDTO buscarProductoPorIdConDetalles(Integer idProducto);

    public List<ProductoDTO> buscarPorCategoria(String categoria);

    public List<ProductoDTO> productosMasVendidos(int topN);
}
