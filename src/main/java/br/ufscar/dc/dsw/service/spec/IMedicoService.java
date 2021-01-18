package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Medico;

public interface IMedicoService {
	Medico buscarPorCRM(String CRM);

	Medico buscarPorId(long id);

	List<Medico> buscarTodos();

	List<Medico> buscarPorEspecialidade(String especialidade);

	boolean medicoTemConsulta(Long id);
}
