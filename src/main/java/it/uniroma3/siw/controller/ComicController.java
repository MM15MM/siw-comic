package it.uniroma3.siw.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import it.uniroma3.siw.controller.validator.ComicValidator;
import it.uniroma3.siw.model.Comic;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.ComicService;
import it.uniroma3.siw.service.CommentService;
import it.uniroma3.siw.service.CredentialsService;

@Controller
public class ComicController {
   
	@Autowired 
	private ComicService comicService;
	
	@Autowired 
	private ComicValidator comicValidator;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private CredentialsService credentialsService;


/*----------------------------------------------------*/
/*----------------------------------------------------*/
/*--------------------ADMIN---------------------------*/
/*----------------------------------------------------*/
/*----------------------------------------------------*/
/*----------------------------------------------------*/
	
	
	/* ADMIN VEDE PAGINA COMICS E COMIC SPECIFICO*/
	@GetMapping(value ="/admin/comics")
	public String getComicsAdmin(Model model) {
		model.addAttribute("comics", this.comicService.findAll());
		return "admin/comics.html";
	}

	@GetMapping(value ="/admin/comic/{id}")
	public String getComicAdmin(@PathVariable("id") Long id, Model model) {

		Comic comic = this.comicService.findById(id);
		model.addAttribute("comic", comic);
		model.addAttribute("comments", this.commentService.findAllByComicId(id));
		return "admin/comicAdmin.html";
	}
	
	
	/*AGGIUNTA NUOVO COMIC*/
	
	@GetMapping(value = "/admin/formNewComic")
	public String formNewComic(Model model) {
		model.addAttribute("comic", new Comic());
		return "admin/formNewComic.html";
	}

	@PostMapping(value = "/admin/comic")
	public String newComic(@Valid @ModelAttribute("comic") Comic comic, 
			              BindingResult bindingResult, Model model) {
		this.comicValidator.validate(comic, bindingResult);
		if(!bindingResult.hasErrors()) { //se i dati sono corretti
		    this.comicService.save(comic); //salvo oggetto comic
		    
			model.addAttribute("comic", comic);
			return "redirect:/admin/comics"; // pagina html con lista comics
		} 
		else {
			return "admin/formNewComic.html"; 
		}
	}
	
	
	
	/*ADMIN ELIMINA COMIC*/
	
	
	
	@PostMapping(value = "/admin/deleteComic/{id}")
	public String deleteComic(@PathVariable("id") Long comicId, Model model){

		Comic comic = comicService.findById(comicId);
		comicService.deleteById(comic.getId());

		return "redirect:/admin/comics";
	}
	
	
	
	/*ADMIN MODIFICA DETTAGLI COMIC*/
	
	
	
	@GetMapping(value = "/admin/formUpdateComic/{id}")
	public String editComic(@PathVariable("id") Long id, Model model) {
		model.addAttribute("comic", this.comicService.findById(id));
		return "admin/formUpdateComic.html";
	}

	@PostMapping(value = "/admin/formUpdateComic/{id}")
	public String editingComic(@Valid @PathVariable("id") Long id, @ModelAttribute("comic") Comic comic,
			BindingResult BindingResult, Model model) {
		Comic originalComic = this.comicService.findById(id);
		originalComic.setTitle(comic.getTitle());
		originalComic.setGenre(comic.getGenre());
		originalComic.setImage(comic.getImage());
		originalComic.setPublisher(comic.getPublisher());
		originalComic.setResume(comic.getResume());
		originalComic.setYear(comic.getYear());

		this.comicValidator.validate(originalComic, BindingResult);
		if (!BindingResult.hasErrors()) {
			List<Comic> comics = this.comicService.findAll();
			model.addAttribute("comics", comics);
			this.comicService.save(originalComic);
			return "admin/comics.html";
		}
		model.addAttribute("comic", this.comicService.findById(id));
		return "admin/formUpdateComic.html";
	}
	
	
	
  /*RICERCA COMICS DA PARTE DELL'ADMIN*/
	


/*	@RequestMapping(value="/admin/formSearchComics",method = RequestMethod.GET)
	public String formSearchComicsAdmin(Model model) {
		return "admin/formSearchComics.html";
	}*/
	@PostMapping(value ="/admin/searchComics")
	public String searchComicsAdmin(Model model, @RequestParam String title) {
		model.addAttribute("comics", this.comicService.findByTitle(title));
		return "admin/adminComicsFound.html";

	}
	
	/*----------------------------------------------------*/
	/*----------------------------------------------------*/
	/*--------------------GENERAL-------------------------*/
	/*----------------------------------------------------*/
	/*----------------------------------------------------*/
	/*----------------------------------------------------*/
	
	/*CHIUNQUE VISUALIZZA LISTA COMICS E DETTAGLI COMIC*/
	
/*	@GetMapping( value= "/comic/{id}")
	public String getComic(@PathVariable("id") Long id, Model model) {
		model.addAttribute("comic", this.comicService.findById(id));
		model.addAttribute("comments", this.commentService.findAllByComicId(id));
		return "comic.html";
	}*/

	@GetMapping(value = "/comicsDefaultUser")
	public String showComics(Model model) {
		model.addAttribute("comics", this.comicService.findAll());
		return "comicsDefaultUser.html";
	}
	@GetMapping(value = "/comicDefaultUser")
	public String showComicDefault() {
		return "comicsDefaultUser.html";
	}
	
	@GetMapping(value= "/comic/{id}/defaultUser")
	public String getComicDefaultUser(@PathVariable("id") Long id, Model model, Principal principal) {
		model.addAttribute("comic", this.comicService.findById(id));
		model.addAttribute("comments", this.commentService.findAllByComicId(id));
		 if (principal != null) {
		        String username = principal.getName();
		        User user = this.credentialsService.getCredentials(username).getUser();

		        if (user != null && user.getFavorites().contains(this.comicService.findById(id))) {
		            // L'utente ha il fumetto tra i preferiti, visualizza comic.html
		            return "comic";
		        }
		    }
		return "comicDefaultUser";
	}

		/*@GetMapping(value = "/comics")
	public String ShowComics(Model model) {
	 	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());

		model.addAttribute("comics", this.comicService.findAll());
		model.addAttribute("user", credentials.getUser());
		return "comics.html";
	}*/
	
	
	/* RiCERCA DEI COMICS*/
	
	
	
/*	@GetMapping(value = "/formSearchComics")
	public String formSearchComics() {
		return "formSearchComics.html";
	}*/

	@PostMapping(value = "/searchComics")
	public String searchComicsByYear(Model model, @RequestParam String title) {
		model.addAttribute("comics", this.comicService.findByTitle(title));
		return "foundComics.html";
	}
	
	
	
}
