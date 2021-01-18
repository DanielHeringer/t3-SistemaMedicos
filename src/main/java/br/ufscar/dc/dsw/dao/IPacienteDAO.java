package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Paciente;

@SuppressWarnings("unchecked")
public interface IPacienteDAO extends CrudRepository<Paciente, Long>{

	List<Paciente> findAll();
	
	Paciente findById(long id);

	Paciente findByCPF(String CPF);
}
