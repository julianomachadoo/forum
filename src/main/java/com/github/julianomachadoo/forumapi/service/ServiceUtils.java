package com.github.julianomachadoo.forumapi.service;

import com.github.julianomachadoo.forumapi.exceptions.DadosNaoEncontradosException;
import com.github.julianomachadoo.forumapi.modelo.Usuario;
import com.github.julianomachadoo.forumapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceUtils {

    @Autowired
    UsuarioRepository usuarioRepository;

    protected Usuario obterUsuario(String emailUsuario) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(emailUsuario);
        if (usuario.isEmpty()) throw new DadosNaoEncontradosException("Usuario não encontrado");
        return usuario.get();
    }
}
