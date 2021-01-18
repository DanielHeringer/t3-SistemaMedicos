package br.ufscar.dc.dsw.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.domain.Paciente;
import br.ufscar.dc.dsw.domain.Consulta;
import br.ufscar.dc.dsw.domain.Medico;
import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.security.UsuarioDetails;
import br.ufscar.dc.dsw.service.spec.IPacienteService;
import br.ufscar.dc.dsw.service.spec.IConsultaService;
import br.ufscar.dc.dsw.service.spec.IMedicoService;
import br.ufscar.dc.dsw.service.spec.IUsuarioService;
import java.util.ArrayList;

@Controller
@RequestMapping("/consultas")
public class ConsultaController {
	@Autowired
	private IConsultaService consultaService;
	
	@Autowired
	private IPacienteService pacienteService;
        
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IMedicoService medicoService;
	
	private Paciente getPacienteAutenticado() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UsuarioDetails user = (UsuarioDetails)authentication.getPrincipal();
		return pacienteService.buscarPorId(user.getId());
	}
        
        private Medico getMedicoAutenticado() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UsuarioDetails user = (UsuarioDetails)authentication.getPrincipal();
		return medicoService.buscarPorId(user.getId());
	}
        
        private String getUserRole(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UsuarioDetails user = (UsuarioDetails)authentication.getPrincipal();
                Usuario usuario = usuarioService.buscarPorId(user.getId());
		return usuario.getRole();
        }
	
	private boolean verificaDataHoraOcupada(Consulta consulta) {
		List<Consulta> consultas = consultaService.buscarPorMedico(consulta.getMedico());
		
		for (int i = 0; i < consultas.size(); i++) {
			if (consultas.get(i).getDataConsulta().equals(consulta.getDataConsulta())
					&& consultas.get(i).getHoraConsulta() == consulta.getHoraConsulta()) {
				return true;
			}
		}
		
		return false;
	}
	
	@GetMapping("/cadastrar")
	public String cadastrar(Consulta consulta, ModelMap model) {
		model.addAttribute("medicos", medicoService.buscarTodos());
		return "consulta/cadastro";
	}

	@GetMapping("/listar")
	public String listar(@RequestParam(required = false) String c, ModelMap model) {
                List<Consulta> consultas = new ArrayList<Consulta>();
                
                if(getUserRole().equals("ROLE_ADMIN")){
                    consultas = consultaService.buscarTodos();
                }
                else if(getUserRole().equals("ROLE_MEDICO")){
                    consultas = consultaService.buscarPorMedico(getMedicoAutenticado());
                }
                else if(getUserRole().equals("ROLE_PACIENTE")){
                    consultas = consultaService.buscarPorPaciente(getPacienteAutenticado());
                }

		model.addAttribute("consultas", consultas);
		return "consulta/lista";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid Consulta consulta, BindingResult result, RedirectAttributes attr, ModelMap model) {
		if (result.hasErrors()) {
			model.addAttribute("medicos", medicoService.buscarTodos());
			return "consulta/cadastro";
		}
		
		if (verificaDataHoraOcupada(consulta)) {
			model.addAttribute("medicos", medicoService.buscarTodos());
			attr.addFlashAttribute("fail", "Consulta não inserida. Horário não esta disponivel.");
			return "redirect:/consultas/cadastrar";
		}
		
		consulta.setPaciente(getPacienteAutenticado());
		consultaService.salvar(consulta);
		
		attr.addFlashAttribute("sucess", "Consulta inserida com sucesso.");
		return "redirect:/consultas/listar";
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("medicos", medicoService.buscarTodos());
		model.addAttribute("consulta", consultaService.buscarPorId(id));
		return "consulta/cadastro";
	}

	@PostMapping("/editar")
	public String editar(@Valid Consulta consulta, BindingResult result, RedirectAttributes attr, ModelMap model) {
		if (result.hasErrors()) {
			model.addAttribute("medicos", medicoService.buscarTodos());
			return "consulta/cadastro";
		}
		
		consulta.setPaciente(getPacienteAutenticado());
		consultaService.salvar(consulta);
		
		attr.addFlashAttribute("sucess", "Consulta inserida com sucesso.");
		return "redirect:/consultas/listar";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
		consultaService.excluir(id);
		
		return "redirect:/consultas/listar";
	}
}
