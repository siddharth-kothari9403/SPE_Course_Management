package Elective_Management.Elective_Management.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(jwtUserDetailsService);
        authenticationProvider.setPasswordEncoder(bcryptEncoder);
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests((configurer)->{
                    configurer.requestMatchers("/authenticate", "/dummy_student", "/dummy_admin", "/dummy_instructor").permitAll()
                            .requestMatchers(HttpMethod.POST,"/register_student", "/register_admin", "/register_instructor").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET,"/student/getAll").hasAnyRole("INSTRUCTOR","ADMIN")
                            .requestMatchers(HttpMethod.GET,"/student/getbyID/**").permitAll()
                            .requestMatchers(HttpMethod.DELETE,"/student/delete/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT,"/student/update").hasAnyRole("ADMIN","STUDENT")
                            .requestMatchers(HttpMethod.POST,"/student/save").hasAnyRole("ADMIN","STUDENT")
                            .requestMatchers(HttpMethod.GET,"/subject/allSubjects").permitAll()
                            .requestMatchers(HttpMethod.GET,"/subject/{code}").permitAll()
                            .requestMatchers(HttpMethod.DELETE,"/subject/delete/**").hasAnyRole("ADMIN")
                            .requestMatchers(HttpMethod.POST,"/subject/save").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT,"/subject/update/**").hasAnyRole("ADMIN","INSTRUCTOR")
                            .requestMatchers(HttpMethod.GET,"/subject/getByInstructorId/{id}").permitAll()
                            .requestMatchers(HttpMethod.GET,"/subject/getInstructor/{id}").permitAll()
                            .requestMatchers(HttpMethod.PUT,"/subject/assignInstructor/{id}").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET,"/studentSubject/getAll").hasAnyRole("ADMIN", "INSTRUCTOR")
                            .requestMatchers(HttpMethod.GET,"/studentSubject/getbyID/**").permitAll()
                            .requestMatchers(HttpMethod.POST,"/studentSubject/save").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE,"/studentSubject/delete/**").permitAll()
                            .requestMatchers(HttpMethod.GET,"/studentSubject/getByInstructor/**").hasAnyRole("ADMIN","INSTRUCTOR", "STUDENT")
                            .requestMatchers(HttpMethod.GET,"/studentSubject/getByStudent/**").permitAll()
                            .requestMatchers(HttpMethod.GET,"/studentSubject/getBySubject/{id}").permitAll()
                            .requestMatchers(HttpMethod.GET, "/instructor/getAll").permitAll()
                            .requestMatchers(HttpMethod.GET, "/instructor/getbyId/**").permitAll()
                            .requestMatchers(HttpMethod.PUT, "/instructor/update").hasAnyRole("INSTRUCTOR","ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/instructor/delete/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.POST, "/instructor/save").hasAnyRole("INSTRUCTOR","ADMIN")
                            .requestMatchers(HttpMethod.GET, "/request/getAll").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/request/getbyID/**").permitAll()
                            .requestMatchers(HttpMethod.GET, "/request/getbyStudent/**").hasAnyRole("STUDENT", "ADMIN")
                            .requestMatchers(HttpMethod.GET, "/request/getbyInstructorId/**").hasAnyRole("INSTRUCTOR", "ADMIN")
                            .requestMatchers(HttpMethod.GET, "/request/getbySubjectId/**").hasAnyRole("INSTRUCTOR", "ADMIN")
                            .requestMatchers(HttpMethod.POST, "/request/save").hasAnyRole("STUDENT", "ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/request/delete/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/studentSubject/accept").hasAnyRole("INSTRUCTOR", "ADMIN")
                            .requestMatchers(HttpMethod.GET, "/student/user/getStudent").hasRole("STUDENT")
                            .requestMatchers(HttpMethod.GET, "/instructor/user/getInstructor").hasRole("INSTRUCTOR")
                            .requestMatchers(HttpMethod.GET, "/studentSubject/getForStudentAndInstructor/**").hasRole("INSTRUCTOR")
                            .requestMatchers(HttpMethod.PUT, "/subject/removeInstructor/**").hasRole("ADMIN")
                            .anyRequest().authenticated();
                }).exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new CorsFilterConfig(), ChannelProcessingFilter.class);

        return http.build();
    }
}

