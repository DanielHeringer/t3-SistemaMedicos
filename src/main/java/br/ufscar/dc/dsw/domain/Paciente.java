package br.ufscar.dc.dsw.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.ufscar.dc.dsw.domain.Usuario; 
import br.ufscar.dc.dsw.domain.Consulta; 
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@SuppressWarnings("serial")
@JsonIgnoreProperties(value = { "consultas", "password", "role", "enabled" })
@Entity
@Table(name = "Paciente")
public class Paciente extends Usuario {

	
	@NotBlank
	@Size(min = 3, max = 60)
	@Column(nullable = false, unique = true, length = 60)
	private String nome;
	
	@NotBlank
	@Size(min = 14, max = 14)
	@Column(nullable = false, unique = true, length = 60)
	private String CPF;
	
	@NotBlank
	@Size(min = 1, max = 40)
	@Column(nullable = false, unique = false, length = 60)
	private String genero;
	 
	@NotBlank
	@Size(min = 8, max = 10)
	@Column(nullable = false, unique = false, length = 60)
	private String dataNascimento;
	 
        @NotBlank
	@Size(min = 11, max = 18)
	@Column(nullable = false, unique = false, length = 60)
	private String telefone;

	@OneToMany(mappedBy = "paciente")
	private List<Consulta> consultas;
	
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCPF() {
		return CPF;
	}

	public void setCPF(String CPF) {
		this.CPF = CPF;
	}
	
	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}
	
	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

    public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}


	public List<Consulta> getConsultas() {
		return consultas;
	}

	public void setConsultas(List<Consulta> consultas) {
		this.consultas = consultas;
	}
}
