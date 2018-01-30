package agrosol.projetoSpringBoot.control;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import agrosol.projetoSpringBoot.model.Filme;
import agrosol.projetoSpringBoot.model.GenericDAO;

@Controller
public class FilmeController {

	public void iniciar(Model model) {
		setarFilmes(model);
		setarFilme(model, new Filme());
	}

	@ModelAttribute
	public Filme getFilme() {
		Filme filme=null;
		if(filme==null) {
			filme = new Filme();
			filme.setNome("Just to show something!");
		}
		return filme;
	}

	public void setarFilmes(Model model) {    	
		List<Filme> filmes = GenericDAO.findAll(Filme.class);
		model.addAttribute("filmes", filmes);				
	}

	public void setarFilme(Model model, Filme filme) {    	
		model.addAttribute("filme", filme);		
	}

	@GetMapping(value="/filmes") 
	public String list(Model model) {		
		iniciar(model);
		return "filmes";
	}

	@GetMapping("filmes/editar/{id}")
	public String updateFilme(@PathVariable Long id, Model model) {
		Filme filme = GenericDAO.findById(Filme.class, id);
		setarFilme(model, filme);
		setarFilmes(model);
		return "filmes";
	}

	@RequestMapping(params = "salvar")
	public String salvarFilme(Filme filme, Model model) {
		if(filme!=null && filme.getId()==null) {
			GenericDAO.save(filme);
		} else if(filme!=null && filme.getId()!=null) {
			GenericDAO.update(filme);
		}
		iniciar(model);
		return "filmes";
	}

	@RequestMapping(params = "remover")
	public String removerFilme(Filme filme, Model model) {
		GenericDAO.delete(filme);
		iniciar(model);
		return "filmes";
	}

	@GetMapping("filmes/cancelar")
	public String removerFilme(Model model) {		
		iniciar(model);
		return "filmes";
	}

	// http://localhost:8080/teste will return filmes.html page
	@RequestMapping("teste") 
	public String teste() {
		return "filmes";
	}

	// http://localhost:8080/teste2 will return JSON data (REST)
	@GetMapping(value = "teste2")
	@CrossOrigin(origins = "http://localhost:4200")
	public @ResponseBody List<Filme> teste2() {
		List<Filme> filmes = new ArrayList<Filme>();
		filmes.add(new Filme(1l, "O nome da Rosa", 9f, 1986));
		filmes.add(new Filme(4l, "Blade Runner", 10f, 1982));
		filmes.add(new Filme(6l, "Blade Runner 2049", 8f, 2017));
		return filmes;
	}

}
