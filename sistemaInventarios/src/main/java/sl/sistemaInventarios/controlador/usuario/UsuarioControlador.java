package sl.sistemaInventarios.controlador.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sl.sistemaInventarios.modelo.usuario.Usuario;
import sl.sistemaInventarios.servicio.usuario.clases.UsuarioConsultaServicio;
import sl.sistemaInventarios.servicio.usuario.clases.UsuarioGestionServicio;

//Luego de pruebas iniciales, implementar las notaciones de seguridad por metodo para los roles
@RestController
@RequestMapping("inventario-app/users")
@CrossOrigin("http://localhost:4200")
public class UsuarioControlador {
    private final UsuarioGestionServicio usuarioGestionServicio;
    private final UsuarioConsultaServicio usuarioConsultaServicio;

    @Autowired
    public UsuarioControlador(UsuarioGestionServicio usuarioGestionServicio, UsuarioConsultaServicio usuarioConsultaServicio) {
        this.usuarioGestionServicio = usuarioGestionServicio;
        this.usuarioConsultaServicio = usuarioConsultaServicio;
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> guardarUsuario(@RequestBody Usuario usuario){
        try{
            Usuario usuarioGuardado = this.usuarioGestionServicio.guardarUsuario(usuario);
            if (usuarioGuardado != null){
                return ResponseEntity.ok("Usuario guardado con exito");
            }else {
                return ResponseEntity.status(404).body("No se ha podido guardar el usuario");
            }
        }catch (Exception e){
            return ResponseEntity.status(400).body("Error: "+e);
        }
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> softDelete(@PathVariable Integer id){
        try{
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(id);
            Usuario usuarioEliminado = this.usuarioGestionServicio.softDelete(usuario);
            if (usuarioEliminado != null){
                return ResponseEntity.ok("Se ha eliminado el usuario correctamente");
            }else {
                return ResponseEntity.status(404).body("No se ha podido eliminar el usuario");
            }
        }catch (Exception e){
            return ResponseEntity.status(400).body("Error: "+e);
        }
    }

    @DeleteMapping("/delete/hard/{id}")
    public ResponseEntity<?> hardDelete(@PathVariable Integer id){
        try {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(id);
            this.usuarioGestionServicio.hardDelete(usuario);
            return ResponseEntity.ok("Se ha eliminado el usuario definitivamente");
        }catch (Exception e){
            return ResponseEntity.status(400).body("Error: "+ e);
        }
    }

    @GetMapping("/recuperar/{id}")
    public ResponseEntity<?> recuperarUsuario(@PathVariable Integer id){
        try{
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(id);
            Usuario usuarioRecuperado = this.usuarioGestionServicio.recuperar(usuario);
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