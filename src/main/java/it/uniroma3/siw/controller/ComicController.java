package it.uniroma3.siw.controller;

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
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.controller.validator.ComicValidator;
import it.uniroma3.siw.model.Comic;
import it.uniroma3.siw.service.ComicService;
import it.uniroma3.siw.service.CommentService;

@Controller
public class ComicController {
   
	@Autowired 
	ComicService comicService;
	
	@Autowired 
	ComicValidator comicValidator;
	
	@Autowired
	CommentService commentService;

	/*SCHERMATA HOME*/
	
	@GetMapping("/index")
	public String index() {
		return "index.html";
	}
	
	
	/*AGGIUNTA NUOVO COMIC*/
	
	@GetMapping(value = "/admin/formNewComic")
	public String formNewComic(Model model) {
		model.addAttribute("comic", new Comic());
		return "formNewComic.html";
	}

	@PostMapping(value = "/admin/comics")
	public String newComic(@Valid @ModelAttribute("comic") Comic comic, 
			              BindingResult bindingResult,@RequestParam("image") MultipartFile multiPartFile, Model model) {
		this.comicValidator.validate(comic, bindingResult);
		if(!bindingResult.hasErrors()) { //se i dati sono corretti
			//String imageName = StringUtils.cleanPath(multiPartFile.getOriginalFilename());
			//comic.setImage(imageName);
		    Comic newComic = this.comicService.save(comic); //salvo oggetto comic
		    //String uploadDir = "comic-pic/" + newComic.getId();
		    //FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			model.addAttribute("comic", comic);
			return "comic.html"; //restituisco pagina html con il comic richiesto
		} 
		else {
			return "formNewComic.html"; 
		}
	}

	
	/*ADMIN MODIFICA DETTAGLI COMIC*/
	
	@GetMapping(value = "/admin/editComic/{id}")
	public String editComic(@PathVariable("id") Long id, Model model) {
		model.addAttribute("comic", this.comicService.findById(id));
		return "admin/editComic";
	}

	@PostMapping(value = "/admin/editComic/{id}")
	public String editingComic(@Valid @PathVariable("id") Long id, @ModelAttribute("comic") Comic comic,
			BindingResult BindingResult, Model model) {
		Comic originalComic = this.comicService.findById(id);
		originalComic.setTitle(comic.getTitle());
		originalComic.setType(comic.getType());
		originalComic.setImage(comic.getImage());
		originalComic.setAuthor(comic.getAuthor());
		originalComic.setPublisher(comic.getPublisher());
		originalComic.setResume(comic.getResume());
		originalComic.setYear(comic.getYear());
		originalComic.setCartoonist(comic.getCartoonist());

		this.comicValidator.validate(originalComic, BindingResult);
		if (!BindingResult.hasErrors()) {
			List<Comic> comics = this.comicService.findAll();
			model.addAttribute("comics", comics);
			this.comicService.save(originalComic);
			return "admin/comics";
		}
		model.addAttribute("comic", this.comicService.findById(id));
		return "admin/editComic";
	}
	
	/*CHIUNQUE VISUALIZZA LISTA COMICS E DETTAGLI COMIC*/
	
	@GetMapping(value = "/comics/{id}")
	public String getComic(@PathVariable("id") Long id, Model model) {
		model.addAttribute("comic", this.comicService.findById(id));
		model.addAttribute("comments", this.commentService.findByComic(this.comicService.findById(id)));
		return "comic.html";
	}

	@GetMapping(value = "/comics")
	public String showComics(Model model) {
		model.addAttribute("comics", this.comicService.findAll());
		return "comics.html";
	}
	
	
	/*CHIUNQUE CERCA I COMICS*/
	
	@GetMapping(value = "/formSearchComics")
	public String formSearchComics() {
		return "formSearchComics.html";
	}

	@PostMapping(value = "/searchComics")
	public String searchComicsByYear(Model model, @RequestParam Integer year) {
		model.addAttribute("comics", this.comicService.findByYear(year));
		return "foundComics.html";
	}
	
	
}
