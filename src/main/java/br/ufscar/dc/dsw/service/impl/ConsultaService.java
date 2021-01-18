package br.ufscar.dc.dsw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.dao.IConsultaDAO;
import br.ufscar.dc.dsw.domain.Paciente;
import br.ufscar.dc.dsw.domain.Consulta;
import br.ufscar.dc.dsw.domain.Medico;
import br.ufscar.dc.dsw.service.spec.IConsultaService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Service
@Transactional(readOnly = false)
public class ConsultaService implements IConsultaService {

	@Autowired
	IConsultaDAO dao;
	
	public void salvar(Consulta consulta) {
		dao.save(consulta);
	}

	public void excluir(Long id) {
		dao.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Consulta buscarPorId(Long id) {
		return dao.findById(id.longValue());
	}

	@Transactional(readOnly = true)
        @JsonIgnoreProperties("paciente")
	public List<Consulta> buscarTodos() {
		return dao.findAll();
	}

	@Transactional(readOnly = true)
	public List<Consulta> buscarPorPaciente(Paciente paciente) {
		return dao.findByPaciente(paciente);
	}

	@Transactional(readOnly = true)
	public List<Consulta> buscarPorMedico(Medico medico) {
		return dao.findByMedico(medico);
	}
}
