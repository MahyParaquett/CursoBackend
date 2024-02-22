// package com.autenticacaojwt.gestaodeprojetos.security;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.http.HttpMethod;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// // A classe WebSecurityConfigurerAdapter já está obsoleta, por isso os métodos estão com erros

// @Configuration
// @EnableWebSecurity //Aqui informo que é uma classe de segurança do WebSecurity

// public class WebSecurityConfigObsoleta extends WebSecurityConfigurerAdapter  {
    
//     @Autowired
//     private CustomUserDetailsService customUserDetailsService;

//     @Autowired
//     private JWTAuthenticationFilter jwtAuthenticationFilter;

//     /*
//      * Método que devolve a istância do objeto que sabe devolver o nossso padrão de codificação.
//      * Isso não tem nada a ver com o JWT. 
//      * Aqui será usado para codificar a senha do usuário gerando um hash para salvar no banco.
//      */

//     @Bean
//     public PasswordEncoder passwordEncoder(){
//         return new BCryptPasswordEncoder();
//     }

//     //Método padrão para configurar o nosso custom que o nosso método de codificar senha.
//     @Override
//         public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
//         authenticationManagerBuilder
//         .userDetailsService(customUserDetailsService)
//         .passwordEncoder(passwordEncoder());
//     }

//     @Bean
//     @Override
//     public AuthenticationManager authenticationManagerBean() throws Exception {
//         return super.authenticationManagerBean();
//     }

//       // Metodo que tem a configuração global de acessos e permissoes por rotas.
//     @Override
//     protected void configure(HttpSecurity http) throws Exception {

//      // Parte padrão da configuração, por enquanto ignorar.
//         http
//          .cors().and().csrf().disable()
//          .exceptionHandling()
//          .and()
//          .sessionManagement()
//         .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//         .and()
//         .authorizeRequests()
        
//     //A partir daqui entram as rotas que vão precisar de autenticação ou não

//         .antMatchers(HttpMethod.POST, "/api/usuarios", "/api/usuarios/login").permitAll()
//         .anyRequest().authenticated();

    
//          http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//     }












// }
