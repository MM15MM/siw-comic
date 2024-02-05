package it.uniroma3.siw.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
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

import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.model.Comic;
import it.uniroma3.siw.service.ArtistService;
import it.uniroma3.siw.service.ComicService;
import it.uniroma3.siw.controller.validator.ArtistValidator;

@Controller
public class ArtistController {
	
	@Autowired 
	private ArtistService artistService;
	
	@Autowired 
	private ArtistValidator artistValidator;
	
	@Autowired 
	private ComicService comicService;
	
	
	
	/*ADMIN VISUALIZZA LISTA ARTISTI*/
	
	
	
	@GetMapping(value="/admin/artists")
	public String indexArtist(Model model) {
		model.addAttribute("artists", this.artistService.findAll());
		return "admin/artists.html";
	}
	
	
	/*ADMIN  RIMOZIONE ARTISTA */
	

	@PostMapping(value = "/admin/deleteArtist/{id}")
	public String deleteArtist(@PathVariable("id") Long id) {
        this.artistService.deleteById(id);
		return "redirect:/admin/artists";
	}

	
	
	/* ADMIN AGGIUNGI ARTISTA */
	
	
	
	@PostMapping(value ="/admin/artist")
	public String newArtist(@Valid @ModelAttribute("artist") Artist artist, 
			                BindingResult bindingResult, Model model) {
		
		this.artistValidator.validate(artist, bindingResult);
		if(!bindingResult.hasErrors()) {
			this.artistService.save(artist); 
			model.addAttribute("artist", artist);
			return "admin/artist.html";
		   } 
		else {
			return "admin/formNewArtist.html"; 
		}
	}
	
	@GetMapping(value = "/admin/formNewArtist")
	public String addArtist(Model model){
		model.addAttribute("artist",new Artist());
		return "admin/formNewArtist.html";
	}
	
	
	
	/*ADMIN MODIFICA DETTAGLI ARTISTA*/
	
	
	
	@GetMapping(value = "/admin/editArtist/{id}")
	public String editArtist(@PathVariable("id") Long id, Model model) {
		model.addAttribute("artist", this.artistService.findById(id));
		return "admin/editArtist";
	}

	@PostMapping(value = "/admin/editArtist/{id}")
	public String editingArtist(@PathVariable("id") Long id, @ModelAttribute("comic") Artist artist,
			BindingResult BindingResult, Model model) {
		Artist originalArtist = this.artistService.findById(id);
		originalArtist.setName(artist.getName());
		originalArtist.setSurname(artist.getSurname());
		originalArtist.setEmail(artist.getEmail());

		this.artistValidator.validate(originalArtist, BindingResult);
		if (!BindingResult.hasErrors()) {
			List<Artist> artists = this.artistService.findAll();
			model.addAttribute("artists", artists);
			this.artistService.save(originalArtist);
			return "admin/indexArtist.html";
		}
		model.addAttribute("artist", this.artistService.findById(id));
		return "admin/editartist.html";
	}

	
	
	/* CHIUNQUE VISUALIZZA I DETTAGLI DELL'ARTISTA E LA LISTA DEGLI ARTISTI*/
	
	
	
	@GetMapping(value = "/artist/{id}")
	public String getArtist(@PathVariable("id") Long id, Model model) {
		model.addAttribute("artist", this.artistService.findById(id));
		List<Comic> comics = this.artistService.findById(id).getWrittenComics();
		model.addAttribute("comics", comics);
		return "artist.html";
	}

	@GetMapping(value = "/artist")
	public String getArtists(Model model) {
		model.addAttribute("artists", this.artistService.findAll());
		return "artists.html";
	}
	
	/*ADMIN VISUALIZZA DETTAGLI ARTISTA*/
	
	@GetMapping (value="/admin/artist/{id}")
	public String showArtistDetailsAdmin(@PathVariable("id") Long id ,Model model){
		model.addAttribute("artist", this.artistService.findById(id));
		List<Comic> comics = this.artistService.findById(id).getWrittenComics();
		model.addAttribute("comics", comics);
		return "admin/artist";
	}
	
	
	
	/*ADMIN AGGIUNGE O RIMUOVE ARTISTI DA COMIC SPECIFICO*/
	
	
	
	@GetMapping("/admin/updateAuthors/{id}")
	public String updateAuthors(@PathVariable("id") Long id, Model model) {

		model.addAttribute("authorsToAdd", this.artistService.findArtistsNotInComic(id));
		model.addAttribute("comic", this.comicService.findById(id));

		return "admin/artistComic.html";
	}

	@PostMapping(value="/admin/addAuthorToComic/{comicId}")
	public String addAuthorToComic(@RequestParam("authorId") Long authorId, 
			@PathVariable("comicId") Long comicId,HttpServletRequest request, Model model) {
		String referer = request.getHeader("Referer");//per aggiornare la pagina
	    	
		Comic comic = this.comicService.findById(comicId);
		Artist author = this.artistService.findById(authorId);
		
		 if (!comic.getAuthors().contains(author)) {
	            comic.getAuthors().add(author);
	            author.getWrittenComics().add(comic);
	            this.comicService.save(comic);
	            this.artistService.save(author);
	        }
		
		return "redirect:" + referer  ;

	}
	
	@PostMapping(value="/admin/removeAuthorFromComic/{comicId}/{authorId}")
	public String removeAuthorFromComic(@PathVariable("authorId") Long authorId, 
			@PathVariable("comicId") Long comicId, Model model) {
		Comic comic = this.comicService.findById(comicId);
		Artist author = this.artistService.findById(authorId);
		
		if (comic.getAuthors().contains(author)) {
            comic.getAuthors().remove(author);
            author.getWrittenComics().remove(comic);
            this.comicService.save(comic);
            this.artistService.save(author);
        }
		
		 return "redirect:/admin/updateAuthors/" + comicId;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
