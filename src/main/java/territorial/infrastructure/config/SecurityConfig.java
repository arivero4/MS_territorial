package territorial.infrastructure.config;

import territorial.infrastructure.adapter.out.security.JwtAuthenticationAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationAdapter jwtAdapter;

    private static final String[] SWAGGER_PATHS = {
            "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html",
            "/swagger-resources/**", "/webjars/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().configurationSource(corsConfigurationSource()).and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(SWAGGER_PATHS).permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(HttpMethod.GET, "/departamentos/**", "/municipios/**", "/lotes/**", "/lugares/**", "/predios/**", "/cultivos/**", "/plagas/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint((req, res, ex) -> {
                    res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    res.setContentType("application/json");
                    res.getWriter().write("{\"error\":\"No autorizado\",\"message\":\"" + ex.getMessage() + "\"}");
                })
                .accessDeniedHandler((req, res, ex) -> {
                    res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    res.setContentType("application/json");
                    res.getWriter().write("{\"error\":\"Acceso denegado\"}");
                });
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(Arrays.asList("http://localhost:*", "http://127.0.0.1:*"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public OncePerRequestFilter jwtAuthenticationFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request,
                    HttpServletResponse response, FilterChain chain)
                    throws ServletException, IOException {
                String token = extractToken(request);
                if (StringUtils.hasText(token) && jwtAdapter.validarToken(token)) {
                    try {
                        String usuario = jwtAdapter.extraerUsuario(token);
                        List<String> roles = jwtAdapter.extraerRoles(token);
                        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                        for (String r : roles) {
                            String norm = r.replace(" ", "_").toUpperCase();
                            // Original role
                            authorities.add(new SimpleGrantedAuthority("ROLE_" + norm));
                            // Legacy mapping so @PreAuthorize("hasRole('ADMIN')") / ('OPERADOR') still works
                            switch (norm) {
                                case "ADMINISTRADOR":
                                    authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                                    authorities.add(new SimpleGrantedAuthority("ROLE_OPERADOR"));
                                    break;
                                case "PROPIETARIO":
                                    authorities.add(new SimpleGrantedAuthority("ROLE_OPERADOR"));
                                    break;
                                case "PRODUCTOR":
                                    authorities.add(new SimpleGrantedAuthority("ROLE_OPERADOR"));
                                    break;
                                case "ASISTENTE_TECNICO":
                                    authorities.add(new SimpleGrantedAuthority("ROLE_OPERADOR"));
                                    break;
                                default:
                                    break;
                            }
                        }
                        UsernamePasswordAuthenticationToken auth =
                                new UsernamePasswordAuthenticationToken(usuario, null, authorities);
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    } catch (Exception e) {
                        log.warn("No se pudo establecer autenticación: {}", e.getMessage());
                    }
                }
                chain.doFilter(request, response);
            }

            private String extractToken(HttpServletRequest request) {
                String header = request.getHeader("Authorization");
                if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
                    return header.substring(7);
                }
                return null;
            }
        };
    }
}
