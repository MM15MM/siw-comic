package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Comic;
import it.uniroma3.siw.repository.ComicRepository;

@Component
public class ComicValidator implements Validator {

		@Autowired
		private ComicRepository comicRepository;

		@Override
		public void validate(Object o, Errors errors) {
			Comic comic = (Comic)o;
			if (comic.getTitle()!=null && comic.getYear()!=null 
					&& comicRepository.existsByTitleAndYear(comic.getTitle(),  comic.getYear())) {
				errors.reject("comic.duplicate");
			}
		}
		@Override
		public boolean supports(Class<?> aClass) {
			return Comic.class.equals(aClass);
	}
}
