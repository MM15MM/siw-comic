package it.uniroma3.siw.controller;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.HttpServletResponse;
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
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.ComicService;
import it.uniroma3.siw.service.CommentService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UserService;

@Controller
public class ComicController {
   
	@Autowired 
	private ComicService comicService;
	
	@Autowired 
	private UserService userService;
	
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
		for(User u: this.userService.getAllUsers()) {
			if(u.getFavorites().contains(comic)) {
	            u.getFavorites().remove(comic);
	            this.userService.saveUser(u);  // Salva l'utente aggiornato nel database
	        }
		}
		this.comicService.deleteById(comicId);
		

		return "redirect:/admin/comics";
	}
	
	
	
	/*ADMIN MODIFICA DETTAGLI COMIC*/
	
	
	
	@GetMapping(value = "/admin/formUpdateComic/{id}")
	public String editComic(@PathVariable("id") Long id, Model model) {
		model.addAttribute("comic", this.comicService.findById(id));
		
		return "admin/formUpdateComic.html";
	}

	@PostMapping(value = "/admin/formUpdateComic/{id}")
	public String editingComic( @PathVariable("id") Long id, @Valid @ModelAttribute("comic") Comic comic,
			BindingResult bindingResult, Model model) {
	
		this.comicValidator.validate(comic, bindingResult);
		if (!bindingResult.hasErrors()) {
			if(this.comicService.findById(id).getFile()!=null) {
				comic.setFile(this.comicService.findById(id).getFile());
			}
			comic.setAuthors(this.comicService.findById(id).getAuthors());
			this.comicService.save(comic);
			model.addAttribute("comic", comic);
			
			return "redirect:/admin/comic/"+id;
		}
		else {
			
		return "admin/formUpdateComic.html";
		}
	}
	
	/*ADMIN CARICA FILE FUMETTO*/
	@PostMapping(value = "/admin/uploadPdf/{id}")
	public String uploadFile(@PathVariable("id") Long id, 
			@RequestParam("file") MultipartFile file, Model model) {
	    Comic comic = this.comicService.findById(id);
	    try {
	        comic.setFile(file.getBytes());
	        this.comicService.save(comic);
	    } catch (IOException e) {
	   //stampa lista di istruzioni che il programma ha eseguito prima di incontrare un errore
	        e.printStackTrace();
	    }
	    return "redirect:/admin/comic/" + id;
	}
	
	/*ADMIN RIMUOVE FILE DAL FUMETTO*/
	@GetMapping(value="/admin/removeFile/{id}")
	public String deleteFile(@PathVariable("id") Long id, Model model) {
		Comic comic=this.comicService.findById(id);
		comic.setFile(null);
		this.comicService.save(comic);
		
		return "redirect:/admin/comic/"+id;
	}

  /*RICERCA COMICS DA PARTE DELL'ADMIN*/
	


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
	


	@GetMapping(value = "/comics")
	public String showComics(Model model) {
		model.addAttribute("comics", this.comicService.findAll());
		return "comics.html";
	}

	
	@GetMapping(value = "/comic/{id}")
	public String getComicUser(@PathVariable("id") Long id, Model model, Principal principal) {
		 model.addAttribute("comic", this.comicService.findById(id));
		    model.addAttribute("comments", this.commentService.findAllByComicId(id));
	    if (principal == null) {
	        // L'utente non è autenticato
	        return "comicDefault";
	    }

	   

	    String username = principal.getName();
	    User user = this.credentialsService.getCredentialsUsername(username).getUser();

	    if (user != null && user.getFavorites().contains(this.comicService.findById(id))) {
	        // L'utente ha il fumetto tra i preferiti
	        return "comicFavorite";
	    }

	    // L'utente è autenticato ma il fumetto non è tra i preferiti
	    return "comicNotFavorite";
	}


	
	/* RiCERCA DEI COMICS*/
	
	
	@PostMapping(value = "/searchComics")
	public String searchComicsByTitle(Model model, @RequestParam String title) {
		model.addAttribute("comics", this.comicService.findByTitle(title));
		return "foundComics.html";
	}
	
	/*CHI E' AUTENTICATO E HA QUEL COMIC TRA I PREFERITI PUO' VISUALIZZARE IL FILE PDF*/
	
	@GetMapping(value = "/comic/{comicId}/pdf")
	public void viewPdf(@PathVariable("comicId") Long comicId, HttpServletResponse response) {
	    Comic comic = this.comicService.findById(comicId);
	    byte[] pdfFile = comic.getFile();
	    //Content-Type è un'intestazione HTTP che specifica 
        //il tipo di contenuto del corpo del messaggio HTTP
        
      //Imposta l'intestazione 'Content-Type' della risposta HTTP a 'application/pdf'
      //specifica il tipo di file da inviare al client
	    response.setContentType("application/pdf");
	    try {
	   
	    // scrive il contenuto del file nell'output stream
	    //(flusso dati che inviato dal server al client come parte di una risposta HTTP)
	        response.getOutputStream().write(pdfFile);
	      
	        // assicura che tutti i dati siano inviati al client prima che il metodo termini
	        response.getOutputStream().flush();
	    } catch (IOException e) {
	    //stampa lista di istruzioni che il programma ha eseguito prima di incontrare un errore
	        e.printStackTrace();
	    }
	}


	
}
