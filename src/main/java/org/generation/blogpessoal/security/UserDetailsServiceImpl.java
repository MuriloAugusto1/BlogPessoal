package org.generation.blogpessoal.security;

import java.util.Optional;

import org.generation.blogpessoal.model.UsuarioModel;
import org.generation.blogpessoal.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UsuarioModel> user = repository.findByUsuario(username);

		if (user.isPresent()) {
			return new UserDetailsImpl(user.get());
		} else {
			throw new UsernameNotFoundException("Usuario não existe");
		}
	}
}
