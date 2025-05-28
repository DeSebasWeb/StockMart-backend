package com.stockmart.api.controller.usuario;

import com.stockmart.api.dto.usuario.MostrarDatosUsuarioDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.stockmart.api.dto.usuario.BuscarUsuarioDTO;
import com.stockmart.api.entity.usuario.Usuario;
import com.stockmart.api.service.usuario.clases.UsuarioConsultaServicio;
import com.stockmart.api.service.usuario.clases.UsuarioGestionServicio;

import java.util.List;

//Luego de pruebas iniciales, implementar las notaciones de seguridad por metodo para los roles
@Tag(name = "usuario-controlador", description = "Operaciones CRUD sobre usuarios.")
@RestController
@RequestMapping("stockmart/users")
@CrossOrigin("http://localhost:4200")
public class UsuarioControlador {
    private final UsuarioGestionServicio UsuarioGestionServicio;
    private final UsuarioConsultaServicio UsuarioConsultaServicio;

    @Autowired
    public UsuarioControlador(UsuarioGestionServicio UsuarioGestionServicio, UsuarioConsultaServicio UsuarioConsultaServicio) {
        this.UsuarioGestionServicio = UsuarioGestionServicio;
        this.UsuarioConsultaServicio = UsuarioConsultaServicio;
    }

    @Operation(
            summary = "Mapeado de todos los usuarios existentes",
            description = "Este endpoint muestra todos los usuarios existentes en la base de datos",
            tags = "usuario-controlador"
    )@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                    content =  @Content(mediaType = "application/json",
                            schema =  @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "403", description = "Necesita autenticacion para usar este endpoint",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No se ha logrado encontrar usuario con el correo indicado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)

    })
    @GetMapping("/listar")
    public ResponseEntity<?> mostrarUsuarios(){
        try{
            List<Usuario> usuarios = this.UsuarioConsultaServicio.mostrarTodosUsuarios();
            return ResponseEntity.ok(usuarios);
        }catch (Exception e){
            return ResponseEntity.status(400).body("Error: "+e);
        }
    }
    @Operation(
            summary = "Busqueda de un usuario por id",
            description = "Este endpoint permite la busqueda de un usuario en especifico por medio de su ID",
            tags = "usuario-controlador"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                    content =  @Content(mediaType = "application/json",
                            schema =  @Schema(implementation = MostrarDatosUsuarioDTO.class))),
            @ApiResponse(responseCode = "403", description = "Necesita autenticacion para usar este endpoint",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No se ha logrado encontrar usuario con el correo indicado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id){
        try {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(id);
            Usuario usuarioEncontrado = this.UsuarioConsultaServicio.buscarUsuarioPorId(usuario);
            if (usuarioEncontrado != null){
                return ResponseEntity.ok(usuarioEncontrado);
            }else {
                return ResponseEntity.status(404).body("No se ha encontrado el usuario");
            }
        }catch (Exception e){
            return ResponseEntity.status(400).body("Error: "+e);
        }
    }

    @Operation(
            summary = "Busqueda de usuario por medio de su correo",
            description = "Este endpoint permite encontrar un usuario en especifico por medio de su correo",
            tags = {"usuario-controlador"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                    content =  @Content(mediaType = "application/json",
                    schema =  @Schema(implementation = MostrarDatosUsuarioDTO.class))),
            @ApiResponse(responseCode = "403", description = "Necesita autenticacion para usar este endpoint",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No se ha logrado encontrar usuario con el correo indicado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)

    })
    @PostMapping("/buscar")
    public ResponseEntity<?> buscarPorCedula(@RequestBody BuscarUsuarioDTO correo){
        try{
            Usuario usuarioEncontrado = this.UsuarioConsultaServicio.buscarUsuarioPorCorreo(correo.getCorreo());
            if (usuarioEncontrado !=null){
                return ResponseEntity.ok(usuarioEncontrado);
            }else {
                return ResponseEntity.status(404).body("No se ha logrado encontrar usuario con el correo indicado");
            }
        }catch (Exception e){
            return ResponseEntity.status(400).body("Error: "+e);
        }
    }

    @Operation(
            summary = "Guarda un nuevo usuario en la base de datos",
            description = "Este endpoint permite guardar un nuevo usuario con sus datos básicos como nombre, email y rol. No se permite duplicados por email.",
            tags = {"usuario-controlador"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario creado exitosamente",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Necesita autenticacion para usar este endpoint",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Datos inválidos o email duplicado o cedula duplicada",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content)
    })
    @PostMapping("/guardar")
    public ResponseEntity<?> guardarUsuario(@RequestBody Usuario usuario){
        try{
            Usuario usuarioGuardado = this.UsuarioGestionServicio.guardarUsuario(usuario);
            if (usuarioGuardado != null){
                return ResponseEntity.ok("Usuario guardado con exito");
            }else {
                return ResponseEntity.status(404).body("No se ha podido guardar el usuario");
            }
        }catch (Exception e){
            return ResponseEntity.status(500).body("Error: "+e);
        }
    }

    @Operation(
            summary = "Eliminacion logica sin hacer eliminacion permanente",
            description = "Este endpoint permite eliminar los usuarios de manera logica, es decir, cambia el estado del usuario a INACTIVO.",
            tags = {"usuario-controlador"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Error",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Necesita autenticacion para usar este endpoint",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content)
    })
    @GetMapping("/delete/{id}")
    public ResponseEntity<?> softDelete(@PathVariable Integer id){
        try{
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(id);
            Usuario usuarioEliminado = this.UsuarioGestionServicio.softDelete(usuario);
            if (usuarioEliminado != null){
                return ResponseEntity.ok("Se ha eliminado el usuario correctamente");
            }else {
                return ResponseEntity.status(404).body("No se ha podido eliminar el usuario");
            }
        }catch (Exception e){
            return ResponseEntity.status(400).body("Error: "+e);
        }
    }

    @Operation(
            summary = "Eliminacion permanente",
            description = "Este endpoint permite eliminar los usuarios de manera permanente.",
            tags = {"usuario-controlador"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Error",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Necesita autenticacion para usar este endpoint",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content)
    })
    @DeleteMapping("/delete/hard/{id}")
    public ResponseEntity<?> hardDelete(@PathVariable Integer id){
        try {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(id);
            this.UsuarioGestionServicio.hardDelete(usuario);
            return ResponseEntity.ok("Se ha eliminado el usuario definitivamente");
        }catch (Exception e){
            return ResponseEntity.status(400).body("Error: "+ e);
        }
    }

    @Operation(
            summary = "Recuperacion de Usuario",
            description = "Este endpoint permite recuperar a los usuarios que hallan sido eliminados logicamente, es decir que tengan un estado INACTIVO.",
            tags = {"usuario-controlador"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario recuperado correctamente",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Error",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Necesita autenticacion para usar este endpoint",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content)
    })
    @GetMapping("/recuperar/{id}")
    public ResponseEntity<?> recuperarUsuario(@PathVariable Integer id){
        try{
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(id);
            Usuario usuarioRecuperado = this.UsuarioGestionServicio.recuperar(usuario);
            if (usuarioRecuperado != null){
                return ResponseEntity.ok("Se ha recuperado el usuario correctamente");
            }else {
                return ResponseEntity.status(404).body("Ha ocurriodo un error al recuperar el usuario");
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error: "+e);
        }
    }
}