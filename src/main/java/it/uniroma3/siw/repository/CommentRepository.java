package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.model.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {

   public List<Comment> findByComicId(Long comicId);
}
