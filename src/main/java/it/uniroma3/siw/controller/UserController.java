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
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.User;
import it.uniroma3.siw.model.Comic;
import it.uniroma3.siw.model.Comment;
import it.uniroma3.siw.service.ComicService;
import it.uniroma3.siw.service.CommentService;
import it.uniroma3.siw.service.UserService;

@Controller
public class UserController {

	@Autowired 
	ComicService comicService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CommentService commentService;
	
	/*----------------------------------------------------*/
	/*----------------------------------------------------*/
	/*----------------GESTIONE PREFERITI------------------*/
	/*----------------------------------------------------*/
	/*----------------------------------------------------*/
	/*----------------------------------------------------*/
	
	
	/*AGGIUNGI AI PREFERITI*/
	
	@GetMapping(value="/user/{userId}/addFavorite/{articleId}")
	    public String addFavorite(@PathVariable Long userId, @PathVariable Long comicId) {
	        User user = userService.getUser(userId);
	        Comic comic = comicService.findById(comicId);

	        if (user != null && comic != null) {
	            user.getFavorites().add(comic);
	            userService.saveUser(user);
	        }

	        return "redirect:/user/{userId}/favorites";
	    }

	    @GetMapping(value="/user/{userId}/favorites")
	    public String viewFavorites(@PathVariable Long userId, Model model) {
	        User user = userService.getUser(userId);

	        if (user != null) {
	            List<Comic> favorites = user.getFavorites();
	            model.addAttribute("favorites", favorites);
	        }

	        return "favorites";
	    }
	
	    
	    /*RIMUOVI DAI PREFERITI*/
	    
	    @GetMapping(value="/user/{userId}/removeFavorite/{articleId}")
	    public String removeFavorite(@PathVariable Long userId, @PathVariable Long comicId) {
	    	User user = userService.getUser(userId);
	    	Comic comic = comicService.findById(comicId);

	        if (user != null && comic != null) {
	            user.getFavorites().remove(comic);
	            this.userService.saveUser(user);
	        }

	        return "redirect:/user/{userId}/favorites";
	    }
	    
	   
		/*----------------------------------------------------*/
		/*----------------------------------------------------*/
		/*----------------GESTIONE COMMENTI-------------------*/
		/*----------------------------------------------------*/
		/*----------------------------------------------------*/
		/*----------------------------------------------------*/
		
	    
	    /*COMMENTI*/
	    
	    @PostMapping(value="/comic/{id}/addComment")
	    public String addComment(@PathVariable("id") Long comicId, @RequestParam("comment") String text, Principal principal,HttpServletRequest request) {
	    	 String referer = request.getHeader("Referer");//uso HttpServletREquest per aggiornare la pagina
	    	
	    	
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


	    @PostMapping(value="/admin/deleteComment/{commentId}")
	    public String deleteComment(@PathVariable("commentId") Long commentId, User user, HttpServletRequest request) {
	        Comment comment = this.commentService.findById(commentId);
	        String referer = request.getHeader("Referer");//uso HttpServletREquest per aggiornare la pagina
	    	
	        if (comment != null) {
	            this.commentService.delete(comment);
	            return "redirect:" + referer;
	        }

	        return "redirect:" + referer;
	    }
	
	
}
