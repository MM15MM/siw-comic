package it.uniroma3.siw.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Comic;
import it.uniroma3.siw.repository.ComicRepository;

@Service
public class ComicService {
	   @Autowired
	    private ComicRepository comicRepository;
	    
	   @Transactional
		public Comic save(Comic comic) {
			return this.comicRepository.save(comic);
        }
	   
	   @Transactional
		public Comic findById(Long id) {
			return this.comicRepository.findById(id).get();
		}
	   
	   @Transactional
		public List<Comic> findByYear(int year) {
			return this.comicRepository.findByYear(year);
		}
	  
	   @Transactional
		public List<Comic> findAll() {
			return (List<Comic>)this.comicRepository.findAll();
		}
	   
	   @Transactional
	   public List<Comic> findByTitle(String title){
		   return this.comicRepository.findByTitle(title);
	   }
	   
	   @Transactional
	   public void deleteById(Long id) {
		   this.comicRepository.deleteById(id);
	   }
	   
}
