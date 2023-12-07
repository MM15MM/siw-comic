package it.uniroma3.siw.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class Artist {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(nullable = false)
    @NotBlank
	private String name;
	
	@Column(nullable=false)
	@NotBlank
	private String surname;
	
	@Column(nullable = false)
    @NotBlank
	private String email;
	
	@ManyToMany(mappedBy = "authors")
	private List<Comic> writtenComics;
	
	/*@OneToMany(mappedBy= "cartoonist")
	private List<Comic> drawnComics;*/
	

	//setter e getter dell'id
	
	public void setId(Long id) {
		this.id=id;
	}
	
	public Long getId() {
		return this.id;
	}

	
	//setter e getter del nome
	
	public void setName(String name) {
		this.name=name;
	}
	
	public String getName() {
		return this.name;
	}

	
	//setter e getter del cognome
	
	public void setSurname(String surname) {
		this.surname=surname;
	}
	
	public String getSurname() {
		return this.surname;
	}

	
	//setter e getter dell'indirizzo email
	
	public void setEmail(String e) {
		this.email=e;
	}
	
	public String getEmail() {
		return this.email;
	}

	
	//setter e getter dei fumetti scritti da...
	
	public void setWrittenComics(List<Comic> lista) {
		this.writtenComics=lista;
	}
	
	public List<Comic> getWrittenComics(){
		return this.writtenComics;
	}
	
	
/*	//setter e getter dei fumetti disegnati da...
	
	public void setDrawnComics(List<Comic> lista) {
		this.drawnComics=lista;
	}
	
	public List<Comic> getDrawnComics(){
		return this.drawnComics;
	}*/
	
	@Override
	public int hashCode() {
		return this.getName().hashCode()+ this.getEmail().hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		Artist f = (Artist)o;
		return this.getName() == f.getName()
		    && this.getEmail() == f.getEmail();
	}

}
