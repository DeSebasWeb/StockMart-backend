package com.stockmart.api.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.stockmart.api.entity.estado.Estado;
import com.stockmart.api.entity.tipoUsuario.TipoUsuario;


@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MostrarDatosUsuarioDTO {
    private Integer idUsuario;

    private String nombre;

    private String apellido;

    private Long cedula;

    private String correo;

    private TipoUsuario tipoUsuario;

    private Estado estado;
}
