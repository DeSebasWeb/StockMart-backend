package com.stockmart.api.controller.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.stockmart.api.entity.usuario.Vendedor;
import com.stockmart.api.service.estado.clases.EstadoGestionServicio;
import com.stockmart.api.service.usuario.clases.VendedorConsultaServicio;
import com.stockmart.api.service.usuario.clases.VendedorGestionServicio;

import java.util.List;

@RestController
@RequestMapping("stockmart/vendedor")
@CrossOrigin("http://localhost:4200")
public class VendedorControlador {
    private final VendedorConsultaServicio vendedorConsultaServicio;
    private final VendedorGestionServicio VendedorGestionServicio;
    private final EstadoGestionServicio EstadoGestionServicio;

    @Autowired
    public VendedorControlador(VendedorConsultaServicio vendedorConsultaServicio, VendedorGestionServicio VendedorGestionServicio, EstadoGestionServicio EstadoGestionServicio) {
        this.vendedorConsultaServicio = vendedorConsultaServicio;
        this.VendedorGestionServicio = VendedorGestionServicio;
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
