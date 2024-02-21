package it.uniroma3.siw.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Comic {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	@Column(nullable = false)
    @NotBlank
	private String title;
	
	@Column(nullable = false)
    @NotNull
	private Integer year;
	
	@Column(nullable = false)
    @NotBlank
	private String genre;
	
    @NotBlank
	private String publisher;
	
    @Column(nullable = false, length = 64)
	private String image;
	
    @ManyToMany
	private Set<Artist> authors;
    
    @OneToMany(mappedBy="comic", cascade=CascadeType.ALL)
    private List<Comment> comments;
	
	
	@NotBlank
	private String resume;

	
	 private byte[] file;

	 //  getter e setter file
	 public byte[] getFile() {
	     return file;
	 }

	 public void setFile(byte[] file) {
	      this.file = file;
	  }
	 
	// getter e setter comments
	 public List<Comment> getComments() {
	   return this.comments;
     }

	public void setComments(List<Comment> comments) {
		      this.comments = comments;
	}
   
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
	
	public void setGenre(String genre) {
		this.genre=genre;
	}
	public String getGenre() {
		return this.genre;
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
		this.image = Image;
	}
	
	public String getImage() {
		return image;
	}

	
    //setter e getter trama
	
	public void setResume(String resume) {
		this.resume=resume;
	}
	
	public String getResume() {
		return this.resume;
	}

	
	//setter e getter autore 
	
	public void setAuthors(Set<Artist> authors) {
		this.authors=authors;
	}
	
	public Set<Artist> getAuthors(){
	    return this.authors;
	}
	
	public Comic getComic() {
		return this;
	}

}
