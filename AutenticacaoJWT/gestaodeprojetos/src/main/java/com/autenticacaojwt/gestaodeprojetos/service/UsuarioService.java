package com.autenticacaojwt.gestaodeprojetos.service;

import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.autenticacaojwt.gestaodeprojetos.repository.UsuarioRepository;
import com.autenticacaojwt.gestaodeprojetos.security.JWTService;
import com.autenticacaojwt.gestaodeprojetos.view.model.usuario.LoginResponse;
import com.autenticacaojwt.gestaodeprojetos.model.Usuario;

@Service
public class UsuarioService {

    private static final String headerPrefix = "Bearer";
    
    @Autowired
    private UsuarioRepository repositorioUsuario;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public List<Usuario> obterTodos(){
        return repositorioUsuario.findAll();
    }

    public Optional<Usuario> obterPorId(long id){
        return repositorioUsuario.findById(id);
    }

    public Optional<Usuario> obterPorEmail(String email){
        return repositorioUsuario.findByEmail(email);
    }

    public Usuario adicionar(Usuario usuario){
        usuario.setId(null);

        if(obterPorEmail(usuario.getEmail()).isPresent()){
            //aqui caberia uma exceção informando que o usuário ja existe
            throw new InputMismatchException("Já existe um usuário cadestrado com o email: " + usuario.getEmail());
        }

        //Condicando a senha pra não ficar publico, gerando um hash
        String senha = passwordEncoder.encode(usuario.getSenha());

        usuario.setSenha(senha);

        return repositorioUsuario.save(usuario);
    }

    public LoginResponse logar(String email, String senha){
        
        //Loga e devolve a autenticação, se der errado já traz a exceção.
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(email, senha, Collections.emptyList()));
        
        //Aqui passo a nova autenticação para o Spring cuidar
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //Gerando o token
        //Bearer a123456klop...(estrutura de um token)
        String token = headerPrefix + jwtService.gerarToken(authentication);

        Usuario usuario = repositorioUsuario.findByEmail(email).get();

        return new LoginResponse(token, usuario);
    }

}
