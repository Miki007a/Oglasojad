package Eads.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    private final PasswordEncoder passwordEncoder;
    private final CustomUsernamePasswordAuthenticationProvider authenticationProvider;

    public WebSecurityConfig(PasswordEncoder passwordEncoder,
                             CustomUsernamePasswordAuthenticationProvider authenticationProvider) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationProvider = authenticationProvider;

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/oglas/add").authenticated()
                .antMatchers("**","/resources/**","/static","/oglas/list/**", "/home", "/assets/**", "/register", "/api/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .failureUrl("/login?error=BadCredentials")
                .defaultSuccessUrl("/home", true)
                .and()
                .logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/home")
                .and()
                .exceptionHandling().accessDeniedPage("/access_denied");


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(passwordEncoder.encode("user"))
                .authorities("ROLE_USER")
                .and()
                .withUser("admin")
                .password(passwordEncoder.encode("admin"))
                .authorities("ROLE_ADMIN");
        auth.authenticationProvider(authenticationProvider);


    }


    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        Path uploadPath = Paths.get("src/images");
        System.out.println("resource handler "+uploadPath.toAbsolutePath());
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:/"+uploadPath.toAbsolutePath()+"/");
    }








}
