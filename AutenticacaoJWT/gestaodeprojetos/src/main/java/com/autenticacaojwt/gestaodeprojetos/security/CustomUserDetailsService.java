package com.autenticacaojwt.gestaodeprojetos.security;

import java.util.Optional;
import java.util.function.Supplier;

// import java.util.Optional;
// import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.autenticacaojwt.gestaodeprojetos.model.Usuario;
import com.autenticacaojwt.gestaodeprojetos.repository.UsuarioRepository;

@Service
public class CustomUserDetailsService implements  UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Usuario usuario = getUser(() -> usuarioRepository.findByEmail(email));
        return usuario;
    }

    public UserDetails obterUsuarioPorId(Long id) {
        Usuario usuario = getUser(() -> usuarioRepository.findById(id));
        return usuario;
    }

    private Usuario getUser(Supplier<Optional<Usuario>> supplier) {
        // Esse método serve para quando não encontrar um usuário mandar esse exceção.
        return supplier.get().orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

}
