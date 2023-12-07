package it.uniroma3.siw.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import it.uniroma3.siw.controller.validator.ComicValidator;
import it.uniroma3.siw.model.Comic;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.service.ComicService;
import it.uniroma3.siw.service.CommentService;
import it.uniroma3.siw.service.CredentialsService;

@Controller
public class ComicController {
   
	@Autowired 
	ComicService comicService;
	
	@Autowired 
	ComicValidator comicValidator;
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	CredentialsService credentialsService;


/*----------------------------------------------------*/
/*----------------------------------------------------*/
/*--------------------ADMIN---------------------------*/
/*----------------------------------------------------*/
/*----------------------------------------------------*/
/*----------------------------------------------------*/
	
	
	/*--------------PERCORSO PER L'ADMIN---------------*/
	@GetMapping(value ="/admin/comics")
	public String getComicsAdmin(Model model) {
		model.addAttribute("comics", this.comicService.findAll());
		return "admin/comics.html";
	}

	@GetMapping(value ="/admin/comic/{id}")
	public String getComicAdmin(@PathVariable("id") Long id, Model model) {

		Comic comic = this.comicService.findById(id);
		model.addAttribute("comic", comic);
		//fa si che ogni film visualizzi i commenti propri
		model.addAttribute("comments", this.commentService.findByComicId(id));
		return "admin/comicAdmin.html";
	}
	
	
	/*------------------AGGIUNTA NUOVO COMIC-------------------*/
	
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
			//String imageName = StringUtils.cleanPath(multiPartFile.getOriginalFilename());
		    this.comicService.save(comic); //salvo oggetto comic
		    //String uploadDir = "comic-pic/" + newComic.getId();
		    //FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		    
			model.addAttribute("comic", comic);
			return "redirect:/admin/comics"; // pagina html con lista comics
		} 
		else {
			return "admin/error.html"; 
		}
	}
	
	/*----------ADMIN ELIMINA COMIC----------*/
	
	@PostMapping(value = "/admin/deleteComic/{id}")
	public String deleteComic(@PathVariable("id") Long comicId, Model model){

		Comic comic = comicService.findById(comicId);
		comicService.deleteById(comic.getId());

		return "redirect:/admin/comics";
	}
	
	/*---------ADMIN MODIFICA DETTAGLI COMIC--------*/
	
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
	
/*---------RICERCA COMICS E ARTISTI DA PARTE DELL'ADMIN----------*/
	


	@RequestMapping(value="/admin/formSearchComics",method = RequestMethod.GET)
	public String formSearchComicsAdmin() {
		return "admin/formSearchComics.html";
	}
	@PostMapping(value ="/admin/searchComics")
	public String searchComicsAdmin(Model model, @RequestParam int year) {
		model.addAttribute("comics", this.comicService.findByYear(year));
		return "admin/adminComicsFound.html";

	}
	
	/*----------------------------------------------------*/
	/*----------------------------------------------------*/
	/*--------------------GENERAL-------------------------*/
	/*----------------------------------------------------*/
	/*----------------------------------------------------*/
	/*----------------------------------------------------*/
	
	/*CHIUNQUE VISUALIZZA LISTA COMICS E DETTAGLI COMIC*/
	
	@GetMapping( "/comic/{id}")
	public String getComic(@PathVariable("id") Long id, Model model) {
		model.addAttribute("comic", this.comicService.findById(id));
		model.addAttribute("comments", this.commentService.findByComicId(id));
		return "comic.html";
	}

	@GetMapping(value = "/comicsDefaultUser")
	public String showComics(Model model) {
		model.addAttribute("comics", this.comicService.findAll());
		return "comicsDefaultUser.html";
	}
	@GetMapping(value = "/comicDefaultUser")
	public String showComicDefault() {
		return "comicsDefaultUser.html";
	}
	
	@GetMapping("/comic/{id}/defaultUser")
	public String getMovieDefaultUser(@PathVariable("id") Long id, Model model) {
		model.addAttribute("comic", this.comicService.findById(id));

		//fa si che ogni film visualizzi i commenti propri
		model.addAttribute("comment", this.commentService.findByComicId(id));
		return "comicDefaultUser";
	}

	
	/* RiCERCA DEI COMICS*/
	
	@GetMapping(value = "/formSearchComics")
	public String formSearchComics() {
		return "formSearchComics.html";
	}

	@PostMapping(value = "/searchComics")
	public String searchComicsByYear(Model model, @RequestParam Integer year) {
		model.addAttribute("comics", this.comicService.findByYear(year));
		return "foundComics.html";
	}
	
	@GetMapping(value = "/comics")
	public String ShowComics(Model model) {
	 	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());

		model.addAttribute("comics", this.comicService.findAll());
		model.addAttribute("user", credentials.getUser());
		return "comics.html";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
