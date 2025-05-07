package sl.sistemaInventarios.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ConfiguracionSeguridad {
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf.disable())// 1. Desactiva CSRF (porque Angular no lo maneja por defecto)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))// 2. Manejo de sesiones
                .authorizeHttpRequests(auth -> auth.requestMatchers("/auth/login","/auth/registro","/public/**").permitAll(). //3. Rutas publicas
                        anyRequest().authenticated())//4. Todo lo demas necesita autenticacion
                .formLogin(AbstractHttpConfigurer::disable)// 5. No usaremos login por formulario
                .httpBasic(AbstractHttpConfigurer::disable); // 6. No usaremos HTTP Basic (solo JSON)

        //Autorizo solo al administrador a que vea las paginas con la url admin
        http.authorizeHttpRequests(auth -> auth.requestMatchers("/admin/**").hasRole("ADMINISTRADOR")
                //Autorizo solo al administrador acceder al modulo de estadistica
                .requestMatchers("/estadistica/**").hasRole("ADMINISTRADOR")
                //Autorizo solo a el administrador y al vendedor a acceder al modulo de ventas
                .requestMatchers("/ventas/**").hasAnyRole("ADMINISTRADOR","VENDEDOR").
                anyRequest().authenticated()
        );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }
}
