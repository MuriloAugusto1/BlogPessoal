package org.generation.blogpessoal.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.generation.blogpessoal.model.UserLogin;
import org.generation.blogpessoal.model.UsuarioModel;
import org.generation.blogpessoal.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;



@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	public UsuarioModel CadastrarUsuario(UsuarioModel usuario) {
		Optional<UsuarioModel> optional = repository.findByUsuario(usuario.getUsuario());
		
		if (optional.isEmpty()) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		String senhaEncoder = encoder.encode(usuario.getSenha());
		usuario.setSenha(senhaEncoder);
		
		return repository.save(usuario);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "usuário já existente");
		}
	}
	
	public Optional<UserLogin> Logar(Optional<UserLogin> usuariologar) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<UsuarioModel> usuario = repository.findByUsuario(usuariologar.get().getUsuario());
		
		if(usuario.isPresent()) {
			if(encoder.matches(usuariologar.get().getSenha(), usuario.get().getSenha())) {
				
				String auth = usuariologar.get().getUsuario() + ":" + usuariologar.get().getSenha();
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodedAuth);
				
				usuariologar.get().setNome(usuario.get().getNome());
				usuariologar.get().setFoto(usuario.get().getFoto());
				usuariologar.get().setSenha(usuario.get().getSenha());
				usuariologar.get().setToken(authHeader);
				
				return usuariologar;
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Senha incorreta!");
			}
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário incorreto!");
		}
	}
}
