package com.stockmart.api.controlador.sesiones;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.stockmart.api.dto.credenciales.CredencialesRespuestaDTO;
import com.stockmart.api.modelo.usuario.Usuario;
import com.stockmart.api.servicio.usuario.clases.IUsuarioGestionServicio;

import java.util.List;

@Tag(name = "Auth-Controlador", description = "Operaciones de autenticacion.")
@RestController
@RequestMapping("inventario-app/auth")
@CrossOrigin("http://localhost:4200")
public class AuthControlador {
    private final AuthenticationManager authenticationManager;
    private final IUsuarioGestionServicio IUsuarioGestionServicio;

    @Autowired
    public AuthControlador(AuthenticationManager authenticationManager, IUsuarioGestionServicio IUsuarioGestionServicio) {
        this.authenticationManager = authenticationManager;
        this.IUsuarioGestionServicio = IUsuarioGestionServicio;
    }

    @Operation(
            summary = "Inicio de sesion",
            description = "El endpoint manda una Cookie al navegador para autenticarlo en caso de que las credenciales sean correctas, en caso que no el inicio de sesion no va a ser exitoso",
            tags = "auth"
    )@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El inicio de sesion fue exitoso",
                    content =  @Content),
            @ApiResponse(responseCode = "401", description = "Las credenciales son incorrectas",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)

    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody CredencialesRespuestaDTO credencialesRespuestaDTO, HttpServletRequest httpServletRequest){
        try{
            //Aca recibe las credenciales que manda el usuario
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(credencialesRespuestaDTO.getCorreo(), credencialesRespuestaDTO.getPassword());
            //Se autentican que las credenciales sean correctas dentro de la variable authToken con la DB
            Authentication authentication = this.authenticationManager.authenticate(authToken);
            // Se establece el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //Crea una sesion y guarda la autenticacion
            HttpSession session = httpServletRequest.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
            //Devuelve la Cookie
            return ResponseEntity.ok("Inicio de sescion exitoso");
        }catch (Exception e){
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }
    }

    @Operation(
            summary = "Registro de usuario",
            description = "El endpoint recibe la informacion de el nuevo usuario, pero como es un endpoint publico, lo pone inmediatamente como administrador y lo guarda en la DB",
            tags = "auth"
    )@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El usuario ha sido creado de manera exitosa",
                    content =  @Content),
            @ApiResponse(responseCode = "401", description = "El usuario no fue creado por falta de algun dato",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)

    })
    @PostMapping("/registro")
    public ResponseEntity<?> registroUsuario(@RequestBody Usuario usuario){
        Usuario usuarioGuardado = this.IUsuarioGestionServicio.guardarUsuario(usuario);
        if (usuarioGuardado!=null){
            return ResponseEntity.ok("El usuario ha sido creado de manera exitosa");
        }else{
            return ResponseEntity.status(401).body("El usuario no fue creado");
        }
    }

    @Operation(
            summary = "Cerrar la sesion",
            description = "El endpoint le dice al contexto de seguridad que limpie la informacion y elimine la cookie",
            tags = "auth"
    )@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se ha cerrado la sesion correctamente",
                    content =  @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession(false);//No se crea una nueva sesion si no que se obtiene la actual
        if (session!=null){//Se verifica que haya una sesion
            session.invalidate();//Se invalida la sesion
        }
        SecurityContextHolder.clearContext();//Se limpia el contexto de seguridad
        return ResponseEntity.ok("Se ha cerrado la sesion correctamente");
    }
}
