package it.uniroma3.siw.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Comic;
import it.uniroma3.siw.model.Comment;
import it.uniroma3.siw.repository.CommentRepository;

@Service

public class CommentService {
	   @Autowired
	    private CommentRepository commentRepository;
	    
        @Transactional
        public Comment save(Comment comment) {
        	return this.commentRepository.save(comment);
        }

		public List<Comment> findByComic(Comic comic) {
			// TODO Auto-generated method stub
			return this.commentRepository.findByComicId(comic.getId());
		}
		public Comment findById(Long commentId) {
			return this.commentRepository.findById(commentId).get();
		}
		public void delete(Comment comment) {
			this.commentRepository.deleteById(comment.getCommentId());
		}

}
