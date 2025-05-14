package sl.sistemaInventarios.controlador.sesiones;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sl.sistemaInventarios.dto.credenciales.CredencialesRespuestaDTO;
import sl.sistemaInventarios.modelo.usuario.Usuario;
import sl.sistemaInventarios.servicio.usuario.clases.UsuarioGestionServicio;

@RestController
@RequestMapping("inventario-app/auth")
@CrossOrigin("http://localhost:4200")
public class AuthControlador {
    private final AuthenticationManager authenticationManager;
    private final UsuarioGestionServicio usuarioGestionServicio;

    @Autowired
    public AuthControlador(AuthenticationManager authenticationManager, UsuarioGestionServicio usuarioGestionServicio) {
        this.authenticationManager = authenticationManager;
        this.usuarioGestionServicio = usuarioGestionServicio;
    }

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

    @PostMapping("/registro")
    public ResponseEntity<?> registroUsuario(@RequestBody Usuario usuario){
        Usuario usuarioGuardado = this.usuarioGestionServicio.guardarUsuario(usuario);
        if (usuarioGuardado!=null){
            return ResponseEntity.ok("El usuario ha sido creado de manera exitosa");
        }else{
            return ResponseEntity.status(401).body("El usuario no fue creado");
        }
    }


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
