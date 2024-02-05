package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.ArtistRepository;

@Component
public class ArtistValidator implements Validator {
	
	@Autowired
	private ArtistRepository artistRepository;

	@Override
	public void validate(Object o, Errors errors) {
		Artist artist = (Artist)o;
		String nome= artist.getName().trim();
		String cognome = artist.getSurname().trim();
		  
		if(nome.isEmpty())
			errors.rejectValue("name", "required");
		if(cognome.isEmpty())
			errors.rejectValue("surname", "required");
		else if (artistRepository.existsByNameAndSurname(nome,cognome)) {
			errors.rejectValue("surname","duplicate");
			errors.rejectValue("name","duplicate");
		}
	}
	
	@Override
	public boolean supports(Class<?> aClass) {
		return Artist.class.equals(aClass);
}
}
