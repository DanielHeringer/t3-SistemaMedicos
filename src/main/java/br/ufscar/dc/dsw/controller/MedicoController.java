package br.ufscar.dc.dsw.controller;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.ufscar.dc.dsw.domain.Medico;
import br.ufscar.dc.dsw.service.spec.IMedicoService;
import br.ufscar.dc.dsw.service.spec.IUsuarioService;

@Controller
@RequestMapping("/medicos")
public class MedicoController {

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IMedicoService medicoService;

	@GetMapping("/cadastrar")
	public String cadastrar(Medico medico) {
		return "medico/cadastro";
	}

	@GetMapping("/listar")
	public String listar(@RequestParam(required = false) String c, ModelMap model) {
		List<Medico> medicos = medicoService.buscarTodos();
		Set<String> especialidades = new HashSet<String>();

		for (Medico medico : medicos) {
			String especialidade = medico.getEspecialidade();
            if (!especialidades.contains(especialidade)) {
                especialidades.add(especialidade);
            }
		}

		if (c != null && !c.isEmpty()) {
			medicos = medicoService.buscarPorEspecialidade(c);
		}

		model.addAttribute("medicos", medicos);
                System.out.println("Medico: " + medicos.get(0).getNome());
		model.addAttribute("especialidades", especialidades);
		return "medico/lista";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid Medico medico, BindingResult result, RedirectAttributes attr) {
                BCryptPasswordEncoder encoder = null;
		if (medico.getRole() == null) {
			medico.setRole("ROLE_MEDICO");
		}
		
		if (result.hasErrors()) {
			return "medico/cadastro";
		}
                medico.setPassword(encoder.encode(medico.getPassword()));
		usuarioService.salvar(medico);
		attr.addFlashAttribute("sucess", "Medico inserido com sucesso");
		return "redirect:/medicos/listar";
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("medico", usuarioService.buscarPorId(id));
		return "medico/cadastro";
	}

	@PostMapping("/editar")
	public String editar(@Valid Medico medico, BindingResult result, RedirectAttributes attr) {
		if (medico.getRole() == null) {
			medico.setRole("ROLE_MEDICO");
		}

		if (result.hasErrors()) {
			return "medico/cadastro";
		}

		usuarioService.salvar(medico);
		attr.addFlashAttribute("sucess", "Medico editado com sucesso.");
		return "redirect:/medicos/listar";
	}

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
		if (medicoService.medicoTemConsulta(id)) {
			attr.addFlashAttribute("fail", "Medico não excluído. Possui uma ou mais Consultas.");
		}
		else {
			usuarioService.excluir(id);
			attr.addFlashAttribute("sucess", "Medico excluído com sucesso.");
		}
		return "redirect:/medicos/listar";
	}
}
