package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Comic;
import it.uniroma3.siw.service.ComicService;

@Component
public class ComicValidator implements Validator {

		@Autowired
		private ComicService comicService;

		@Override
		public void validate(Object o, Errors errors) {
			Comic comic = (Comic)o;
			String titolo = comic.getTitle().trim();
			String resume= comic.getResume().trim();
			if (titolo.isEmpty())
	            errors.rejectValue("title", "required");
			else if (comicService.existsByTitleAndYear(titolo,  comic.getYear())) 
				errors.rejectValue("title","duplicate");
			if (resume.isEmpty())
				errors.rejectValue("resume", "required");
			else if (resume.length()>250) 
				errors.rejectValue("resume","size");
		}

		@Override
		public boolean supports(Class<?> aClass) {
			return Comic.class.equals(aClass);
	}
}
