package com.autenticacaojwt.gestaodeprojetos.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.util.Collections;

import org.springframework.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    //Método principal onde toda a requisição bate antes de chegar no nosso endpoint.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
            
            //Pego o token de dentro da requisição
            String token = obterToken(request);

            //Pego o id do usuário que esta dentro do token.
            Optional<Long> id = jwtService.obterIdDoUsuario(token);

            if(id.isPresent()){
        
                //Pego o usuario dono do token pelo seu Id
                UserDetails usuario = customUserDetailsService.obterUsuarioPorId(id.get());

                //Nesse ponto verificamos se o usuário esta autenticado ou não.
                //Aqui poderia validar as permissões.
                UsernamePasswordAuthenticationToken autenticacao =
                new UsernamePasswordAuthenticationToken(usuario, null, Collections.emptyList());

                //Mudando a autenticação para a propria requisição, deixando a autenticação mais atualizada
                autenticacao.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //Repasso a autenticação para o contexto do security
                //O Spring assume a partir daqui
                SecurityContextHolder.getContext().setAuthentication(autenticacao);
            }

            //Método padrão para filtrar as regras do usuário
            filterChain.doFilter(request, response);
        }
    
    private String obterToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");

        //verifica se veio alguma coisa sem ser espaços em brancos dentro do token
        if(!StringUtils.hasText(token)){
            return null;
        }

        //Retorna o token a partir da 7 posição, porque antes do token em si temos o tipo que é "bearer"
        return token.substring(7);
    }
}
