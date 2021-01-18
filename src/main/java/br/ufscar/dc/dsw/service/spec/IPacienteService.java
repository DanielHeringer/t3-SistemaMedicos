package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Paciente;

public interface IPacienteService {
	List<Paciente> buscarTodos();

	Paciente buscarPorCPF(String CPF);

	Paciente buscarPorId(long id);

	boolean pacienteTemConsulta(Long id);
}
