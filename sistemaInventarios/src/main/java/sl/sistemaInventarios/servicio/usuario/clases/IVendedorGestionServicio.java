package sl.sistemaInventarios.servicio.usuario.clases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sl.sistemaInventarios.modelo.facturacion.Venta;
import sl.sistemaInventarios.modelo.usuario.Usuario;
import sl.sistemaInventarios.modelo.usuario.Vendedor;
import sl.sistemaInventarios.repositorio.usuario.VendedorRepositorio;

@Service
@Transactional
public class IVendedorGestionServicio implements sl.sistemaInventarios.servicio.usuario.interfaces.IVendedorGestionServicio {
    private final VendedorRepositorio vendedorRepositorio;
    private final VendedorConsultaServicio vendedorConsultaServicio;

    @Autowired
    public IVendedorGestionServicio(VendedorRepositorio vendedorRepositorio, VendedorConsultaServicio vendedorConsultaServicio) {
        this.vendedorRepositorio = vendedorRepositorio;
        this.vendedorConsultaServicio = vendedorConsultaServicio;
    }

    @Override
    public Vendedor guardarDatosVendedor(Vendedor vendedor) {
        if (vendedor.getIdVendedor() != null){
            Vendedor vendedorEncontrado = this.vendedorConsultaServicio.buscarVendedorPorId(vendedor.getIdVendedor());
            vendedorEncontrado.setUsuario(vendedor.getUsuario());
            vendedorEncontrado.setNumeroVentas(vendedor.getNumeroVentas());
            vendedorEncontrado.setZonaTrabajo(vendedor.getZonaTrabajo());
            return this.vendedorRepositorio.save(vendedorEncontrado);
        }else {
            return this.vendedorRepositorio.save(vendedor);
        }
    }

    @Override
    public Vendedor incrementarVenta(Venta venta) {
        Vendedor vendedorEncontrado = this.vendedorConsultaServicio.buscarVendedorPorId(venta.getVendedor().getIdVendedor());
        if (vendedorEncontrado != null){
            vendedorEncontrado.setNumeroVentas(vendedorEncontrado.getNumeroVentas()+1);
            return this.guardarDatosVendedor(vendedorEncontrado);
        }else {
            throw new RuntimeException("El vendedor con ID: "+ vendedorEncontrado.getIdVendedor()+ " no existe");
        }
    }

    @Override
    public Vendedor crearDesdeUsuario(Usuario usuario) {
        if (usuario != null){
            Vendedor vendedor = new Vendedor();
            vendedor.setUsuario(usuario);
            Vendedor vendedorGuardado = this.vendedorRepositorio.save(vendedor);
            return vendedorGuardado;
        }else {
            throw new RuntimeException("No se puede crear el vendedor");
        }
    }
}