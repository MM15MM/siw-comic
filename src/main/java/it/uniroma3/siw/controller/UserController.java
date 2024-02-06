package it.uniroma3.siw.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.User;
import it.uniroma3.siw.model.Comic;
import it.uniroma3.siw.model.Comment;
import it.uniroma3.siw.service.ComicService;
import it.uniroma3.siw.service.CommentService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UserService;

@Controller
public class UserController {

	@Autowired
	private CredentialsService credentialsService;
	@Autowired 
	private ComicService comicService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommentService commentService;
	
	/*----------------------------------------------------*/
	/*----------------------------------------------------*/
	/*----------------GESTIONE PREFERITI------------------*/
	/*----------------------------------------------------*/
	/*----------------------------------------------------*/
	/*----------------------------------------------------*/
	
	
	/*AGGIUNGI AI PREFERITI*/
	
	@PostMapping(value="/addFavorite/{id}")
	    public String addFavorite(@PathVariable("id") Long comicId, Principal principal, Model model,
	    		HttpServletRequest request) {
		
		String referer = request.getHeader("Referer");//per aggiornare la pagina
		String username = principal.getName();
		
		User user = this.credentialsService.getCredentialsUsername(username).getUser();
	        Comic comic = this.comicService.findById(comicId);

	        if (user != null && comic != null) {
	            user.getFavorites().add(comic);
	            model.addAttribute("favorite",comic);
	            this.userService.saveUser(user);
	        }

	        return "redirect:" + referer;
	    }

	    @GetMapping(value="/favorites")
	    public String viewFavorites( Model model, Principal principal) {
	    	String username = principal.getName();
			
			User user = this.credentialsService.getCredentialsUsername(username).getUser();

	        if (user != null) {
	            List<Comic> favorites = user.getFavorites();
	            model.addAttribute("favorites", favorites);
	        }

	        return "favorites";
	    }
	
	    
	    /*RIMUOVI DAI PREFERITI*/
	    
	    @PostMapping(value="/removeFavorite/{id}")
	    public String removeFavorite(@PathVariable("id") Long comicId,HttpServletRequest request,Principal principal) {
	    	String username = principal.getName();
	    	String referer = request.getHeader("Referer");//per aggiornare la pagina
	    	
			User user = this.credentialsService.getCredentialsUsername(username).getUser();
	    	Comic comic = comicService.findById(comicId);

	        if (user != null && comic != null) {
	            user.getFavorites().remove(comic);
	            this.userService.saveUser(user);
	        }

	        return "redirect:" + referer;
	    }
	    
	   
		/*----------------------------------------------------*/
		/*----------------------------------------------------*/
		/*----------------GESTIONE COMMENTI-------------------*/
		/*----------------------------------------------------*/
		/*----------------------------------------------------*/
		/*----------------------------------------------------*/
		
	    
	    /*COMMENTI*/
	    
	    @PostMapping(value="/comic/{id}/addComment")
	    public String addComment(@PathVariable("id") Long comicId, @RequestParam("comment") String text, 
	    		Principal principal,HttpServletRequest request) {
	    	 String referer = request.getHeader("Referer");//per aggiornare la pagina
	    	
	    	
	        Comic comic = this.comicService.findById(comicId);
	        String user = principal.getName();
	        Comment comment = new Comment();
	        if ( principal!=null) { //comic != null &&
	            comment.setComment(text);
	            //comment.setId(commentId);
	            comment.setUsername(user);
	            comment.setComic(comic);
	            this.commentService.save(comment);
	        }
	        return "redirect:" + referer;
	    }


	    @RequestMapping(value="/admin/deleteComment/{commentId}", method=RequestMethod.GET)
	    public String deleteComment(@PathVariable("commentId") Long commentId, User user, HttpServletRequest request) {
	        Comment comment = this.commentService.findById(commentId);
	        String referer = request.getHeader("Referer");//per aggiornare la pagina
	    	
	        if (comment != null) {
	            this.commentService.delete(comment);
	            return "redirect:" + referer;
	        }

	        return "redirect:" + referer;
	    }
	
	
}
