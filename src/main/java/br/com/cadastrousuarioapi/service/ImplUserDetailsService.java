package br.com.cadastrousuarioapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.cadastrousuarioapi.model.Usuario;
import br.com.cadastrousuarioapi.repository.UsuarioRepository;

@Service
public class ImplUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Usuario usuario = usuarioRepository.findUserByLogin(username);

		if (usuario == null) {
			throw new UsernameNotFoundException("Usuario não foi encontrado");
		}

		return new User(usuario.getLogin(), usuario.getSenha(), usuario.getAuthorities());
	}

}
