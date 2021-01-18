package br.ufscar.dc.dsw.controller;

import java.io.IOException;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufscar.dc.dsw.domain.Paciente;
import br.ufscar.dc.dsw.service.spec.IPacienteService;
import br.ufscar.dc.dsw.service.spec.IUsuarioService;

@RestController
public class PacienteRestController {
	
	@Autowired
	private IUsuarioService serviceUsuario;

	@Autowired
	private IPacienteService servicePaciente;

	private boolean isJSONValid(String jsonInString) {
		try {
			return new ObjectMapper().readTree(jsonInString) != null;
		} catch (IOException e) {
			return false;
		}
	}

	private void parse(Paciente paciente, JSONObject json) {
		Object id = json.get("id");
		if (id != null) {
			if (id instanceof Integer) {
				paciente.setId(((Integer) id).longValue());
			} else {
				paciente.setId((Long) id);
			}
		}

		paciente.setCPF((String) json.get("cpf"));
		paciente.setNome((String) json.get("nome"));
		paciente.setUsername((String) json.get("username"));
		paciente.setPassword((String) json.get("password"));
		paciente.setRole((String) json.get("role"));
		paciente.setEnabled(true);
		paciente.setGenero((String) json.get("genero"));
		paciente.setDataNascimento((String) json.get("dataNascimento"));
		paciente.setTelefone((String) json.get("telefone"));
	}

	@PostMapping(path = "/pacientes")
	@ResponseBody
	public ResponseEntity<Paciente> cria(@RequestBody JSONObject json, BCryptPasswordEncoder encoder) {
		try {
			if (isJSONValid(json.toString())) {
				Paciente paciente = new Paciente();
				parse(paciente, json);
				paciente.setPassword(encoder.encode(paciente.getPassword()));
				serviceUsuario.salvar(paciente);
				return ResponseEntity.ok(paciente);
			} else {
				return ResponseEntity.badRequest().body(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}
	
	@GetMapping(path = "/pacientes")
	public ResponseEntity<List<Paciente>> lista() {
		List<Paciente> lista = servicePaciente.buscarTodos();
		if (lista.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping(path = "/pacientes/{id}")
	public ResponseEntity<Paciente> listaPorId(@PathVariable("id") Long id) {
		Paciente paciente = servicePaciente.buscarPorId(id);
		if (paciente == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(paciente);
	}
	
	@PutMapping(path = "/pacientes/{id}")
	public ResponseEntity<Paciente> atualiza(@PathVariable("id") Long id, @RequestBody JSONObject json) {
		try {
			if (isJSONValid(json.toString())) {
				Paciente paciente = servicePaciente.buscarPorId(id);
				if (paciente == null) {
					return ResponseEntity.notFound().build();
				} else {
					parse(paciente, json);
					serviceUsuario.salvar(paciente);
					return ResponseEntity.ok(paciente);
				}
			} else {
				return ResponseEntity.badRequest().body(null);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}
	
	@DeleteMapping(path = "/pacientes/{id}")
	public ResponseEntity<Boolean> remove(@PathVariable("id") Long id) {
		Paciente paciente = servicePaciente.buscarPorId(id);
		if (paciente == null) {
			return ResponseEntity.notFound().build();
		} else if (!paciente.getConsultas().isEmpty()) {
			return ResponseEntity.ok(false);
		} else {
			serviceUsuario.excluir(id);
			return ResponseEntity.ok(true);
		}
	}
}