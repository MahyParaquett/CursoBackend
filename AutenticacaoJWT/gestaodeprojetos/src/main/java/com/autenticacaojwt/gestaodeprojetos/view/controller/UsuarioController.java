package com.autenticacaojwt.gestaodeprojetos.view.controller;

import org.springframework.web.bind.annotation.RestController;

import com.autenticacaojwt.gestaodeprojetos.model.Usuario;
import com.autenticacaojwt.gestaodeprojetos.service.UsuarioService;
import com.autenticacaojwt.gestaodeprojetos.view.model.usuario.LoginRequest;
import com.autenticacaojwt.gestaodeprojetos.view.model.usuario.LoginResponse;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@CrossOrigin("*") //Permite que acessem o controller de qualquer origem (http)
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    
    @Autowired
    private UsuarioService servicoUsuario;

    @GetMapping 
    public List<Usuario> obterTodos(){
        return servicoUsuario.obterTodos();
    }

    @GetMapping("/{id}")
    public Optional<Usuario> obter(@PathVariable("id") Long id){
        return servicoUsuario.obterPorId(id);
    }

    @PostMapping
    public Usuario adcionar (@RequestBody Usuario usuario){
        return servicoUsuario.adicionar(usuario);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request){
        return servicoUsuario.logar(request.getEmail(), request.getSenha());
    }
}