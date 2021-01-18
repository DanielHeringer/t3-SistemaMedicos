package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Paciente;
import br.ufscar.dc.dsw.domain.Medico;
import br.ufscar.dc.dsw.domain.Consulta;

@SuppressWarnings("unchecked")
public interface IConsultaDAO extends CrudRepository<Consulta, Long>{

	Consulta findById(long id);

	List<Consulta> findAll();

        Consulta save(Consulta consulta);

	void deleteById(Long id);

        List<Consulta> findByPaciente(Paciente paciente);

        List<Consulta> findByMedico(Medico medico);

}
