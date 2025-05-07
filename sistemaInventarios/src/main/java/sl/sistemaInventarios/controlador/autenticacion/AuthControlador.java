package sl.sistemaInventarios.controlador.autenticacion;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sl.sistemaInventarios.dto.CredencialesRespuesta;

@RestController
@RequestMapping("inventario-app/auth")
public class AuthControlador {
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthControlador(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody CredencialesRespuesta credencialesRespuesta, HttpServletRequest httpServletRequest){
        try{
            //Aca recibe las credenciales que manda el usuario
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(credencialesRespuesta.getCorreo(), credencialesRespuesta.getPassword());
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
}
