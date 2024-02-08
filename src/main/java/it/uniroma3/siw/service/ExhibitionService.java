package it.uniroma3.siw.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Comic;
import it.uniroma3.siw.model.Exhibition;
import it.uniroma3.siw.repository.ExhibitionRepository;

@Service
public class ExhibitionService {
	@Autowired
    private ExhibitionRepository exhibitionRepository;
	
	
	@Transactional
	public Exhibition save(Exhibition e) {
		return this.exhibitionRepository.save(e);
    }
   
   @Transactional
	public Exhibition findById(Long id) {
		return this.exhibitionRepository.findById(id).get();
	}
   @Transactional
	public List<Exhibition> findAll() {
		return (List<Exhibition>)this.exhibitionRepository.findAll();
	}
   @Transactional
   public void deleteById(Long id) {
	   this.exhibitionRepository.deleteById(id);
   }
 @Transactional
public boolean existsByName(String name) {
	// TODO Auto-generated method stub
	return this.exhibitionRepository.existsByName(name);
}
@Transactional
public List<Exhibition> findByName(String name) {
	// TODO Auto-generated method stub
	return this.exhibitionRepository.findByName(name);
}
}
