package com.autenticacaojwt.gestaodeprojetos.security;

import java.util.Date;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.autenticacaojwt.gestaodeprojetos.model.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTService {
    // chave que é capaz de descriptografar e criptografar o token
    private static final String chavePrivadaJWT = "secretKey";

    /**
     * Método para gerar o token JWT
     * @param autentication Autenticação do usuário
     * @return Token
    */
    public String gerarToken(Authentication authentication) {

        //Aqui pode variar com a regra de negócio
        // um dia em milisegundos
        int tempoExpiracao = 86400000; 

        //Criação da data de expiração para o token com base no tempo de expiração. 
        //Ele pega a data atual e soma mais um dia em milissegundos
        Date dataExpiracao = new Date(new Date().getTime() + tempoExpiracao);

        //Aqui pegamos o usuario atual da autenticação
        Usuario usuario = (Usuario)authentication.getPrincipal();

        //Aqui juntamos todos os dados e geramos um token do JWT
        return Jwts.builder()
                .setSubject(usuario.getId().toString()) //id do usuario
                .setIssuedAt(new Date()) //dia de inicio
                .setExpiration(dataExpiracao) //dia da expiração
                .signWith(SignatureAlgorithm.HS512, chavePrivadaJWT) //tipo de criptografia e quem lê
                .compact();
    }

    /**
     * Método para retornar o id do usuário dono do token
     * @param token Token do usuário
     * @return id
    */
    public Optional<Long> obterIdDoUsuario(String token){
        try {
            //Retorna as permissões do token
            Claims claims = parse(token).getBody();

            //Retorna o id de dentro do token se tiver, se não retorna null.
            return Optional.ofNullable(Long.parseLong(claims.getSubject()));
            
        } catch (Exception e) {
            //Se não encontrar nada, devolve um optional null.
            return Optional.empty();        
        }
    }

    //Método que sabe descobrir de dentro do token com base na chave privada quais são as permissões do usuário.
    private Jws<Claims> parse(String token) {
        return Jwts.parser().setSigningKey(chavePrivadaJWT).parseClaimsJws(token);
    }












}




