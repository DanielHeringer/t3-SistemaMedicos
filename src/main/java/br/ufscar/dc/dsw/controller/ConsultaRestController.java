package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.domain.Consulta;
import br.ufscar.dc.dsw.domain.Medico;
import br.ufscar.dc.dsw.domain.Paciente;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufscar.dc.dsw.service.spec.IConsultaService;
@RestController
public class ConsultaRestController {

	@Autowired
	private IConsultaService consultaService;
	
	private boolean isJSONValid(String jsonInString) {
		try {
			return new ObjectMapper().readTree(jsonInString) != null;
		} catch (IOException e) {
			return false;
		}
	}

	private void parse(Consulta consulta, JSONObject json) {

		Object id = json.get("id");
		if (id != null) {
			if (id instanceof Integer) {
				consulta.setId(((Integer) id).longValue());
			} else {
				consulta.setId((Long) id);
			}
		}

		consulta.setDataConsulta((String) json.get("data"));
		consulta.setHoraConsulta((int) json.get("hora"));
		consulta.setMedico((Medico) json.get("medico"));
                consulta.setPaciente((Paciente) json.get("paciente"));
                
	}

	@GetMapping(path = "/consultas")
	public ResponseEntity<List<Consulta>> lista() {
		List<Consulta> lista = consultaService.buscarTodos();
		if (lista.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(lista);
	}

	@GetMapping(path = "/consultas/{id}")
	public ResponseEntity<Consulta> lista(@PathVariable("id") long id) {
		Consulta consulta = consultaService.buscarPorId(id);
		if (consulta == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(consulta);
	}

	@PostMapping(path = "/consultas")
	@ResponseBody
	public ResponseEntity<Consulta> cria(@RequestBody JSONObject json) {
		try {
			if (isJSONValid(json.toString())) {
				Consulta consulta = new Consulta();
				parse(consulta, json);
				consultaService.salvar(consulta);
				return ResponseEntity.ok(consulta);
			} else {
				return ResponseEntity.badRequest().body(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}

	@PutMapping(path = "/consultas/{id}")
	public ResponseEntity<Consulta> atualiza(@PathVariable("id") long id, @RequestBody JSONObject json) {
		try {
			if (isJSONValid(json.toString())) {
				Consulta consulta = consultaService.buscarPorId(id);
				if (consulta == null) {
					return ResponseEntity.notFound().build();
				} else {
					parse(consulta, json);
					consultaService.salvar(consulta);
					return ResponseEntity.ok(consulta);
				}
			} else {
				return ResponseEntity.badRequest().body(null);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}

	@DeleteMapping(path = "/consultas/{id}")
	public ResponseEntity<Boolean> remove(@PathVariable("id") long id) {

		Consulta consulta = consultaService.buscarPorId(id);
		if (consulta == null) {
			return ResponseEntity.notFound().build();
		} else {
			consultaService.excluir(id);
			return ResponseEntity.noContent().build();
		}
	}
}
