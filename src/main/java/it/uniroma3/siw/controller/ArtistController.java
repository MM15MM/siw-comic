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

import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.service.ArtistService;
import it.uniroma3.siw.controller.validator.ArtistValidator;

@Controller
public class ArtistController {
	
	@Autowired 
	private ArtistService artistService;
	
	@Autowired 
	ArtistValidator artistValidator;
	
	
	/*ADMIN VISUALIZZA LISTA ARTISTI*/
	
	@GetMapping(value="/admin/artists")
	public String indexArtist(Model model) {
		model.addAttribute("artists", this.artistService.findAll());
		return "admin/artists.html";
	}
	
	
	/* RIMOZIONE ARTISTA */
	
/*	@GetMapping(value = "/admin/toRemoveArtist/{id}")
	public String toRemoveArtist(@PathVariable("id") Long id, Model model) {
		model.addAttribute("artist", this.artistService.findById(id));
		return "admin/confirmArtistDeletion";
	}*/

	@PostMapping(value = "/admin/deleteArtist/{id}")
	public String deleteArtist(@PathVariable("id") Long id) {
        this.artistService.deleteById(id);
		return "indexAdmin.htm";
	}

	/*  AGGIUNGI ARTISTA */
	
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
		return "admin/artist";
	}
}
