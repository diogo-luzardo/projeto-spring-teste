package com.vendas.vendas.Service.impl;

import com.vendas.vendas.entity.Usuario;
import com.vendas.vendas.excepetion.SenhaInvalidaException;
import com.vendas.vendas.jpaRepository.Usuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UsuarioServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private Usuarios repository;

    @Transactional
    public Usuario salvar(Usuario usuario){
        return repository.save(usuario);
    }

    public UserDetails autenticar (Usuario usuario){
        UserDetails usuarioDetails = loadUserByUsername(usuario.getNome());
        boolean matches = encoder.matches(usuario.getSenha(), usuarioDetails.getPassword());

        if(matches){
            return usuarioDetails;
        }
        throw new SenhaInvalidaException();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = repository.findByNome(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base de dados"));

        String[] roles = usuario.isAdmin() ? new String[]{"ADMIN", "USER"} : new String[]{"USER"};
        return User.builder()
                .username(usuario.getNome())
                .password(usuario.getSenha())
                .roles(roles)
                .build();
    }
}
