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

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "comicId")
    private Comic comic;
    @Column(name = "username")
	private String username;
    //setter e getter user
	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return this.user;
	}
	
	//setter e getter fumetto
	public void setUsername(String user) {
		this.username = user;
	}

	public String getUsername() {
		return this.username;
	}
	
	//setter e getter fumetto
	
	public void setComic(Comic comic) {
		this.comic=comic;
	}
	
	public Comic getComic() {
		return this.comic;
	}

	//setter e getter degli id dei commenti
	
	public void setId(Long commentId) {
		this.commentId = commentId;
	}
	
	public Long getId() {
		return this.commentId;
	}


	//setter e getter commenti
	
	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return this.comment;
	}
	/*public String getCommentUsername() {
		return this.username;
	}*/

	/*public void setComment_by_user(String username) {
		this.username = username;
	}*/


}
