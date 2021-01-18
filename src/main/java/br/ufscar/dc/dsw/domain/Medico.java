package br.ufscar.dc.dsw.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
@JsonIgnoreProperties(value = { "consultas", "password", "role", "enabled" })
@Table(name = "Medico")
public class Medico extends Usuario {

	@NotBlank
	@Size(min = 3, max = 18, message = "{Size.medico.CRM}")
	@Column(nullable = false, unique = true, length = 60)
	private String CRM;
	
	@NotBlank
	@Size(min = 3, max = 60)
	@Column(nullable = false, unique = true, length = 60)
	private String nome;

        @NotBlank
	@Size(min = 3, max = 60)
	@Column(nullable = false, unique = false, length = 60)
	private String especialidade;

	@OneToMany(mappedBy = "medico")
	private List<Consulta> consultas;
	
	public String getCRM() {
		return CRM;
	}

	public void setCRM(String CRM) {
		this.CRM = CRM;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

    public String getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}

	public List<Consulta> getConsultas() {
		return consultas;
	}

	public void setConsultas(List<Consulta> consultas) {
		this.consultas = consultas;
	}
}
