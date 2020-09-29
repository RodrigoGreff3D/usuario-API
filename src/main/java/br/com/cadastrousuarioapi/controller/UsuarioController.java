package br.com.cadastrousuarioapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cadastrousuarioapi.model.Usuario;
import br.com.cadastrousuarioapi.model.UsuarioDTO;
import br.com.cadastrousuarioapi.repository.UsuarioRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@GetMapping(value = "/{id}")
	public ResponseEntity<UsuarioDTO> listId(@PathVariable(value = "id") Long id) {
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		return new ResponseEntity<UsuarioDTO>(new UsuarioDTO(usuario.get()), HttpStatus.OK);

	}

	@GetMapping(value = "/")
	@CacheEvict(value = "cachelista", allEntries = true)
	@CachePut("cachelista")
	public ResponseEntity<List<Usuario>> listAll() throws InterruptedException {
		List<Usuario> list = usuarioRepository.findAll();

		return new ResponseEntity<List<Usuario>>(list, HttpStatus.OK);
	}

	@PostMapping(value = "/")
	public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {
		for (int i = 0; i < usuario.getTelefones().size(); i++) {
			usuario.getTelefones().get(i).setUsuario(usuario);
		}

		String senhacriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(senhacriptografada);
		Usuario usuarioSalvo = usuarioRepository.save(usuario);

		return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK);

	}

	@PutMapping(value = "/")
	public ResponseEntity<Usuario> editar(@RequestBody Usuario usuario) {
		for (int i = 0; i < usuario.getTelefones().size(); i++) {
			usuario.getTelefones().get(i).setUsuario(usuario);
		}

		Usuario userTemp = usuarioRepository.findUserByLogin(usuario.getLogin());

		if (!userTemp.getSenha().equals(usuario.getSenha())) {/* Senhas diferentes */
			String senhacriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());
			usuario.setSenha(senhacriptografada);
		}

		Usuario editado = usuarioRepository.save(usuario);
		return new ResponseEntity<Usuario>(editado, HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public String delete(@PathVariable(value = "id") Long id) {
		usuarioRepository.deleteById(id);
		return "ok";
	}

}
