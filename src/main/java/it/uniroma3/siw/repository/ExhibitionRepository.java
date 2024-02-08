package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Exhibition;

public interface ExhibitionRepository extends CrudRepository<Exhibition, Long>{

	

	public boolean existsByName(String name);

	public List<Exhibition> findByName(String name);

}
