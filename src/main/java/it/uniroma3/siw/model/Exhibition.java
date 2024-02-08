package it.uniroma3.siw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Exhibition {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	@Column(nullable = false)
    @NotBlank
	private String name;
	
	@NotBlank
	private String description;
	
	@NotBlank
	private String website;
	
	
	
	public Exhibition getExhibition() {
		return this;
	}
	//setter e getter id

		public void setId(Long id) {
			this.id = id;
		}
		
		public Long getId() {
			return this.id;
		}
		
		//setter e getter del nome
		
		public void setName(String name) {
			this.name = name;
		}
		
		public String getName() {
			return this.name;
		}
		//setter e getter della descrizione
		
		public void setDescription(String description) {
			this.description = description;
			}
				
		public String getDescription() {
			return this.description;
			}
		
		//setter e getter sito fiera
		public String getWebsite() {
			return this.website;
		}
		
		public String setWebsite(String url) {
					return this.website=url;
				}
}
