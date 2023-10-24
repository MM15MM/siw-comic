package it.uniroma3.siw.controller;

import java.util.List;

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
	
	
	/*AGGIUNGI AI PREFERITI*/
	
	@GetMapping("/user/{userId}/addFavorite/{articleId}")
	    public String addFavorite(@PathVariable Long userId, @PathVariable Long comicId) {
	        User user = userService.getUser(userId);
	        Comic comic = comicService.findById(comicId);

	        if (user != null && comic != null) {
	            user.getFavorites().add(comic);
	            userService.saveUser(user);
	        }

	        return "redirect:/user/{userId}/favorites";
	    }

	    @GetMapping("/user/{userId}/favorites")
	    public String viewFavorites(@PathVariable Long userId, Model model) {
	        User user = userService.getUser(userId);

	        if (user != null) {
	            List<Comic> favorites = user.getFavorites();
	            model.addAttribute("favorites", favorites);
	        }

	        return "favorites";
	    }
	
	    
	    /*RIMUOVI DAI PREFERITI*/
	    
	    @GetMapping("/user/{userId}/removeFavorite/{articleId}")
	    public String removeFavorite(@PathVariable Long userId, @PathVariable Long comicId) {
	    	User user = userService.getUser(userId);
	    	Comic comic = comicService.findById(comicId);

	        if (user != null && comic != null) {
	            user.getFavorites().remove(comic);
	            this.userService.saveUser(user);
	        }

	        return "redirect:/user/{userId}/favorites";
	    }
	    
	    /*COMMENTI*/
	    
	    @PostMapping("/{userId}/articles/{articleId}/addComment")
	    public String addComment(@PathVariable Long userId, @PathVariable Long articleId, @RequestParam String text) {
	        Comic comic = this.comicService.findById(articleId);

	        if (comic != null) {
	            Comment comment = new Comment();
	            comment.setComment(text);
	            comment.setUser(this.userService.getUser(userId));
	            comment.setComic(comic);
	            this.commentService.save(comment);
	        }

	        return "redirect:/{userId}/comics/{comicId}";
	    }


	    @GetMapping("/{userId}/articles/{articleId}/deleteComment/{commentId}")
	    public String deleteComment(@PathVariable Long articleId, @PathVariable Long commentId, User user) {
	        Comment comment = commentService.findById(commentId);

	        if (comment != null && user != null && (user.getEmail()).equals(comment.getUser().getEmail())) {
	            commentService.delete(comment);
	        }

	        return "redirect:/{userId}/articles/{articleId}";
	    }
	
	
}
