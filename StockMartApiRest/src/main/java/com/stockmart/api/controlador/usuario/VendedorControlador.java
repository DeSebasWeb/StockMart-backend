package com.stockmart.api.controlador.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.stockmart.api.modelo.usuario.Vendedor;
import com.stockmart.api.servicio.estado.clases.EstadoGestionServicio;
import com.stockmart.api.servicio.usuario.clases.VendedorConsultaServicio;
import com.stockmart.api.servicio.usuario.clases.IVendedorGestionServicio;

import java.util.List;

@RestController
@RequestMapping("inventario-app/vendedor")
@CrossOrigin("http://localhost:4200")
public class VendedorControlador {
    private final VendedorConsultaServicio vendedorConsultaServicio;
    private final IVendedorGestionServicio IVendedorGestionServicio;
    private final EstadoGestionServicio EstadoGestionServicio;

    @Autowired
    public VendedorControlador(VendedorConsultaServicio vendedorConsultaServicio, IVendedorGestionServicio IVendedorGestionServicio, EstadoGestionServicio EstadoGestionServicio) {
        this.vendedorConsultaServicio = vendedorConsultaServicio;
        this.IVendedorGestionServicio = IVendedorGestionServicio;
        this.EstadoGestionServicio = EstadoGestionServicio;
    }

    @PostMapping("/listar")
    public ResponseEntity<?> mostrarVendedores(){
        try{
            List<Vendedor> vendedores = this.vendedorConsultaServicio.mostrarVendedores();
            return ResponseEntity.ok(vendedores);
        }catch (Exception e){
            return ResponseEntity.status(400).body("Error: "+e);
        }
    }
}
