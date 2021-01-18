package br.ufscar.dc.dsw.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
@Table(name = "Consulta")
public class Consulta extends AbstractEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "paciente_id")
	private Paciente paciente;

    @ManyToOne    
    @JoinColumn(name = "medico_id")
	private Medico medico;

    @NotBlank
	@Size(min = 8, max = 12)
	@Column(nullable = false, unique = false, length = 60)
	private String dataConsulta;
    
	@Column(nullable = false, unique = false)
	private int horaConsulta;
	
        public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

        public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public String getDataConsulta() {
		return dataConsulta;
	}

	public void setDataConsulta(String dataConsulta) {
		this.dataConsulta = dataConsulta;
	}
	
	public int getHoraConsulta() {
		return horaConsulta;
	}

	public void setHoraConsulta(int horaConsulta) {
		this.horaConsulta = horaConsulta;
	}
}
