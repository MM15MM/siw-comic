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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.controller.validator.ExhibitionValidator;
import it.uniroma3.siw.model.Exhibition;
import it.uniroma3.siw.service.ExhibitionService;

@Controller
public class ExhibitionController {
	
	@Autowired
	private ExhibitionService exhibitionService;
	@Autowired
	private ExhibitionValidator exhibitionValidator;

/*----------------------------------------------------*/
/*----------------------------------------------------*/
/*--------------------ADMIN---------------------------*/
/*----------------------------------------------------*/
/*----------------------------------------------------*/
/*----------------------------------------------------*/
	
	
	/* ADMIN VEDE PAGINA FIERE*/
	@GetMapping(value ="/admin/exhibitions")
	public String getExhibitionsAdmin(Model model) {
		model.addAttribute("exhibitions", this.exhibitionService.findAll());
		return "admin/exhibitions.html";
	}

	
	
	
	/*AGGIUNTA NUOVA FIERA*/
	
	@GetMapping(value = "/admin/formNewExhibition")
	public String formNewExhibition(Model model) {
		model.addAttribute("exhibition", new Exhibition());
		return "admin/formNewExhibition.html";
	}

	@PostMapping(value = "/admin/exhibition")
	public String newExhibition(@Valid @ModelAttribute("exhibition") Exhibition exhibition, 
			              BindingResult bindingResult, Model model) {
		this.exhibitionValidator.validate(exhibition, bindingResult);
		if(!bindingResult.hasErrors()) { //se i dati sono corretti
		    this.exhibitionService.save(exhibition); //salvo oggetto 
		    
			model.addAttribute("exhibition", exhibition);
			return "redirect:/admin/exhibitions"; // pagina html con lista 
		} 
		else {
			return "admin/formNewExhibition.html"; 
		}
	}
	
	
	
	/*ADMIN ELIMINA FIERA*/
	
	
	
	@RequestMapping(value = "/admin/deleteExhibition/{id}", method=RequestMethod.GET)
	public String deleteExhibition(@PathVariable("id") Long exhibitionId, Model model){

		Exhibition exhibition = exhibitionService.findById(exhibitionId);
		this.exhibitionService.deleteById(exhibition.getId());

		return "redirect:/admin/exhibitions";
	}
	
	
	
	/*ADMIN MODIFICA DETTAGLI FIERA*/
	
	
	
	@GetMapping(value = "/admin/formUpdateExhibition/{id}")
	public String editExhibition(@PathVariable("id") Long id, Model model) {
		model.addAttribute("exhibition", this.exhibitionService.findById(id));
		return "admin/formUpdateExhibition.html";
	}

	@PostMapping(value = "/admin/formUpdateExhibition/{id}")
	public String editingExhibition(@Valid @PathVariable("id") Long id, @ModelAttribute("exhibition") Exhibition exhibition,
			BindingResult BindingResult, Model model) {
		Exhibition e = this.exhibitionService.findById(id);
		e.setName(exhibition.getName());
		e.setWebsite(exhibition.getWebsite());
		e.setDescription(exhibition.getDescription());

		this.exhibitionValidator.validate(e, BindingResult);
		if (!BindingResult.hasErrors()) {
			List<Exhibition> exhibitions = this.exhibitionService.findAll();
			model.addAttribute("exhibitions", exhibitions);
			this.exhibitionService.save(e);
			return "redirect:/admin/exhibitions";
		}
		model.addAttribute("exhibition", this.exhibitionService.findById(id));
		return "redirect:/admin/exhibitions";
	}
	
	@GetMapping(value = "/exhibitions")
	public String showExhibition(Model model) {
		model.addAttribute("exhibitions", this.exhibitionService.findAll());
		return "exhibitions.html";
	}

/*RICERCA FIERA */
	


	@PostMapping(value ="/admin/searchExhibitions")
	public String searchExhibitionsAdmin(Model model, @RequestParam String name) {
		model.addAttribute("exhibitions", this.exhibitionService.findByName(name));
		return "admin/adminExhibitionFound.html";

	}
	
	@PostMapping(value ="/searchExhibitions")
	public String searchExhibitions(Model model, @RequestParam String name) {
		model.addAttribute("exhibitions", this.exhibitionService.findByName(name));
		return "exhibitionFound.html";

	}
	
}
