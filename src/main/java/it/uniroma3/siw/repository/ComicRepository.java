package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Comic;

public interface ComicRepository extends CrudRepository<Comic, Long> {


	public boolean existsByTitleAndYear(String title, Integer year);

	public List<Comic> findByTitle(String title);

	public List<Comic> findByYear(int year);

    
}