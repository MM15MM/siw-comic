package it.uniroma3.siw.model;

import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@Entity
public class Comic {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	@Column(nullable = false)
    @NotBlank
	private String title;
	
	@Column(nullable = false)
    @NotBlank
	private Integer year;
	
	@Column(nullable = false)
    @NotBlank
	private String type;
	
    @NotBlank
	private String publisher;
	
    @Column(nullable = false, length = 64)
	private String Image;
	
    @ManyToMany
	private Set<Artist> authors;
	
	@ManyToOne
	private Artist cartoonist;
	
	private String resume;

   
	//setter e getter id

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return this.id;
	}

	
	//setter e getter del titolo
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return this.title;
	}

	
	//setter e getter dell'anno

	public void setYear(Integer year) {
		this.year = year;
	}
	
	public Integer getYear() {
		return this.year;
	}

	
	//setter e getter del genere
	
	public void setType(String type) {
		this.type=type;
	}
	public String getType() {
		return this.type;
	}
	
	
	//setter e getter della casa editrice
	
	public void setPublisher(String publisher) {
		this.publisher=publisher;
	}
	
	public String getPublisher() {
		return this.publisher;
	}


	//setter e getter image
	
	public void setImage(String Image) {
		this.Image = Image;
	}
	
	public String getImage() {
		return Image;
	}

	
    //setter e getter trama
	
	public void setResume(String resume) {
		this.resume=resume;
	}
	
	public String getResume() {
		return this.resume;
	}

	
	//setter e getter autore 
	
	public void setAuthor(Set<Artist> authors) {
		this.authors=authors;
	}
	
	public Set<Artist> getAuthor(){
	    return this.authors;
	}
	
	
	//setter e getter del disegnatore
	
	public void setCartoonist(Artist cartoonist) {
		this.cartoonist=cartoonist;
	}
	
	public Artist getCartoonist(){
	    return this.cartoonist;
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(title, year);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comic other = (Comic) obj;
		return Objects.equals(title, other.title) && year == other.year;
	}
}
