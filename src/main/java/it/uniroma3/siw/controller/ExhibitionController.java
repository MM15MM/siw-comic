package it.uniroma3.siw.controller;

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
	
	
	
	@GetMapping(value = "/admin/deleteExhibition/{id}")
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
			BindingResult bindingResult, Model model) {
		

		this.exhibitionValidator.validate(exhibition, bindingResult);
		if (!bindingResult.hasErrors()) {
			
			this.exhibitionService.save(exhibition);
			model.addAttribute("exhibition", exhibition);
			
			return "redirect:/admin/exhibitions";
		}
		else {
		return "admin/formUpdateExhibition.html";
		}
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
