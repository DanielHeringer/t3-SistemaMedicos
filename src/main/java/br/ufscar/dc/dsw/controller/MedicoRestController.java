package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.domain.Medico;
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

import br.ufscar.dc.dsw.domain.Medico;
import br.ufscar.dc.dsw.service.spec.IMedicoService;
import br.ufscar.dc.dsw.service.spec.IUsuarioService;

@RestController
public class MedicoRestController {
	
	@Autowired
	private IUsuarioService serviceUsuario;

	@Autowired
	private IMedicoService serviceMedico;

	private boolean isJSONValid(String jsonInString) {
		try {
			return new ObjectMapper().readTree(jsonInString) != null;
		} catch (IOException e) {
			return false;
		}
	}

	private void parse(Medico medico, JSONObject json) {
		Object id = json.get("id");
		if (id != null) {
			if (id instanceof Integer) {
				medico.setId(((Integer) id).longValue());
			} else {
				medico.setId((Long) id);
			}
		}

		medico.setCRM((String) json.get("crm"));
		medico.setNome((String) json.get("nome"));
		medico.setUsername((String) json.get("username"));
		medico.setPassword((String) json.get("password"));
		medico.setRole((String) json.get("role"));
		medico.setEnabled(true);
		medico.setEspecialidade((String) json.get("especialidade"));
	}

	@PostMapping(path = "/medicos")
	@ResponseBody
	public ResponseEntity<Medico> cria(@RequestBody JSONObject json, BCryptPasswordEncoder encoder) {
		try {
			if (isJSONValid(json.toString())) {
				Medico medico = new Medico();
				parse(medico, json);
				medico.setPassword(encoder.encode(medico.getPassword()));
				serviceUsuario.salvar(medico);
				return ResponseEntity.ok(medico);
			} else {
				return ResponseEntity.badRequest().body(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}
	
	@GetMapping(path = "/medicos")
	public ResponseEntity<List<Medico>> lista() {
		List<Medico> lista = serviceMedico.buscarTodos();
		if (lista.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping(path = "/medicos/{id}")
	public ResponseEntity<Medico> listaPorId(@PathVariable("id") Long id) {
		Medico medico = serviceMedico.buscarPorId(id);
		if (medico == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(medico);
	}
	
	@PutMapping(path = "/medicos/{id}")
	public ResponseEntity<Medico> atualiza(@PathVariable("id") Long id, @RequestBody JSONObject json) {
		try {
			if (isJSONValid(json.toString())) {
				Medico medico = serviceMedico.buscarPorId(id);
				if (medico == null) {
					return ResponseEntity.notFound().build();
				} else {
					parse(medico, json);
					serviceUsuario.salvar(medico);
					return ResponseEntity.ok(medico);
				}
			} else {
				return ResponseEntity.badRequest().body(null);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}
	
	@DeleteMapping(path = "/medicos/{id}")
	public ResponseEntity<Boolean> remove(@PathVariable("id") Long id) {
		Medico medico = serviceMedico.buscarPorId(id);
		if (medico == null) {
			return ResponseEntity.notFound().build();
		} else if (!medico.getConsultas().isEmpty()) {
			return ResponseEntity.ok(false);
		} else {
			serviceUsuario.excluir(id);
			return ResponseEntity.ok(true);
		}
	}
}