package br.ufscar.dc.dsw;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import br.ufscar.dc.dsw.domain.*;
import br.ufscar.dc.dsw.dao.*;

@SpringBootApplication
public class SistemaMedicoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaMedicoApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(IUsuarioDAO usuarioDAO, IPacienteDAO pacienteDAO, IMedicoDAO medicoDAO, IConsultaDAO consultaDAO, BCryptPasswordEncoder encoder) {
		return (args) -> {
                        /* Comentar esses comandos caso for rodar mais de uma vez */
//			Usuario user = new Usuario();
//			user.setUsername("admin");
//			user.setPassword(encoder.encode("admin"));
//			user.setRole("ROLE_ADMIN");
//			user.setEnabled(true);
//			usuarioDAO.save(user);
//
//			Paciente c1 = new Paciente();
//			c1.setUsername("joao@gmail.com");
//			c1.setPassword(encoder.encode("joao"));
//			c1.setRole("ROLE_PACIENTE");
//			c1.setEnabled(true);
//			c1.setCPF("000.111.123-00");
//			c1.setNome("Joao");
//			c1.setTelefone("16999887766");
//			c1.setGenero("M");
//			c1.setDataNascimento("01/02/1987");
//			usuarioDAO.save(c1);
//
//			Paciente c2 = new Paciente();
//			c2.setUsername("philipe@gmail.com");
//			c2.setPassword(encoder.encode("philipe"));
//			c2.setRole("ROLE_PACIENTE");
//			c2.setEnabled(true);
//			c2.setCPF("123.312.000-10");
//			c2.setNome("Philipe");
//			c2.setTelefone("16990098008");
//			c2.setGenero("M");
//			c2.setDataNascimento("10/10/1980");
//			usuarioDAO.save(c2);
//
//			Medico m1 = new Medico();
//			m1.setUsername("daniel@gmail.com");
//			m1.setPassword(encoder.encode("daniel"));
//			m1.setRole("ROLE_MEDICO");
//			m1.setEnabled(true);
//			m1.setCRM("123456780");
//			m1.setNome("Daniel");
//			m1.setEspecialidade("Cardiologista");
//			usuarioDAO.save(m1);
//
//			Medico m2 = new Medico();
//			m2.setUsername("Delano@gmail.com");
//			m2.setPassword(encoder.encode("delano"));
//			m2.setRole("ROLE_MEDICO");
//			m2.setEnabled(true);
//			m2.setCRM("876543210");
//			m2.setNome("Delano");
//			m2.setEspecialidade("Oftarmologista");
//			usuarioDAO.save(m2);
//
//			Consulta consulta1 = new Consulta();
//			consulta1.setDataConsulta("2021-01-15");
//			consulta1.setHoraConsulta(15);
//			consulta1.setPaciente(c1);
//			consulta1.setMedico(m1);
//			consultaDAO.save(consulta1);
//
//			Consulta consulta2 = new Consulta();
//			consulta2.setDataConsulta("2021-01-16");
//			consulta2.setHoraConsulta(15);
//			consulta2.setPaciente(c1);
//			consulta2.setMedico(m2);
//			consultaDAO.save(consulta2);
//
//			Consulta consulta3 = new Consulta();
//			consulta3.setDataConsulta("2021-03-03");
//			consulta3.setHoraConsulta(15);
//			consulta3.setPaciente(c2);
//			consulta3.setMedico(m2);
//			consultaDAO.save(consulta3);
		};
	}
}
