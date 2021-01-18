package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Paciente;
import br.ufscar.dc.dsw.domain.Consulta;
import br.ufscar.dc.dsw.domain.Medico;

public interface IConsultaService {

	Consulta buscarPorId(Long id);

	List<Consulta> buscarTodos();

	List<Consulta> buscarPorPaciente(Paciente paciente);

	List<Consulta> buscarPorMedico(Medico medico);

	void salvar(Consulta consulta);

	void excluir(Long id);
}
