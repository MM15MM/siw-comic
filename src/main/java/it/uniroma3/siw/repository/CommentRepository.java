package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.model.Comment;
import it.uniroma3.siw.model.User;

public interface CommentRepository extends CrudRepository<Comment, Long> {

   public List<Comment> findAllByComicId(Long comicId);
   public  List<Comment> findByUsername(User author);
   public Comment findByComicId(Long id);
}
