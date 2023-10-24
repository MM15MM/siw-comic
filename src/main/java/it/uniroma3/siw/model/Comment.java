package it.uniroma3.siw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name = "comments")
public class Comment {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long commentId;


	@Column(name = "comment")
	private String comment;


	//PER LA GESTIONE DEI COMMENTI

	@Column(name = "ComicIdByComment")
	private Long comicId;

	@Column(name = "CommentedByUser")
	private String comment_by_user;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "comic_id")
    private Comic comic;

	 
    //setter e getter user
	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return this.user;
	}
	
	//setter e getter fumetto
	
	public void setComic(Comic comic) {
		this.comic = comic;
	}
	
	public Comic getComic() {
		return this.comic;
	}

	//setter e getter degli id dei commenti
	
	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}
	
	public Long getCommentId() {
		return this.commentId;
	}


	//setter e getter commenti
	
	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return this.comment;
	}


}
