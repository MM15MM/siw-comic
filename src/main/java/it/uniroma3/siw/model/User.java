package it.uniroma3.siw.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	private String name;
	private String surname;
	private String email;
	
	@ManyToMany
	private List<Comic> favorites = new LinkedList<Comic>();
	
	public void setFavorites(Comic comic) {
		this.favorites.add(comic);
	}
	
	public List<Comic> getFavorites(){
		return this.favorites;
	}

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	/* =======================	GESTIONE COMMENTI ================================ */

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments;


	
	public List<Comment> getReviews() {
		return this.comments;
	}

	public void setReviews(List<Comment> comments) {
		this.comments = comments;
	}
}

