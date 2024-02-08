package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Exhibition;
import it.uniroma3.siw.service.ExhibitionService;

@Component
public class ExhibitionValidator implements Validator {
	@Autowired
	 private ExhibitionService exhibitionService;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Exhibition.class.equals(clazz);
	}

	@Override
	public void validate(Object o, Errors errors) {
		// TODO Auto-generated method stub
		Exhibition e = (Exhibition)o;
		String nome= e.getName().trim();
		String descr = e.getDescription().trim();
		  
		if(nome.isEmpty())
			errors.rejectValue("name", "required");
		if(descr.isEmpty())
			errors.rejectValue("description", "required");
		else if (exhibitionService.existsByName(nome)) {
			errors.rejectValue("name","duplicate");
		}
	}
}
