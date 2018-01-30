package agrosol.projetoSpringBoot.control;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import agrosol.projetoSpringBoot.model.Filme;
import agrosol.projetoSpringBoot.model.GenericDAO;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class FilmeRestController {
	@GetMapping(value="/rest-filmes") 
	public List<Filme> list(Model model) {		
		return GenericDAO.findAll(Filme.class);
	}

	@GetMapping("/rest-filmes/buscar/{id}")
	public Filme findFilme(@PathVariable("id") Long id) {
		Filme filme = GenericDAO.findById(Filme.class, id);
		return filme;
	}

	@PostMapping("/rest-filmes/salvar")
	public void salvarFilme(@RequestBody Filme filme) {
		if(filme!=null) {
			System.out.println("Salvando filme nome:"+filme.getNome()+" id:"+filme.getId());
		}
		else {
			System.out.println("Salvando filme null");
		}
		if(filme!=null && filme.getId()==null) {
			GenericDAO.save(filme);
		} else if(filme!=null && filme.getId()!=null) {
			GenericDAO.update(filme);
		}
	}

	@PostMapping("/rest-filmes/remover")
	public void removerFilme(@RequestBody Filme filme) {
		if(filme!=null) {
			System.out.println("Removendo filme nome:"+filme.getNome()+" id:"+filme.getId());
		}
		else {
			System.out.println("Removendo filme null");
		}
		GenericDAO.delete(filme);		
	}
}
