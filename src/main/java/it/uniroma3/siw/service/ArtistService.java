package it.uniroma3.siw.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.repository.ArtistRepository;

@Service
public class ArtistService {
	  @Autowired
	    private ArtistRepository artistRepository;
	    
	   @Transactional
		public Artist save(Artist artist) {
			return this.artistRepository.save(artist);
      }
	   @Transactional
	   public boolean existsByNameAndSurname(String name, String surname) {
		   return this.artistRepository.existsByNameAndSurname(name, surname);
	   }
	   
	   @Transactional
		public Artist findById(Long id) {
			return this.artistRepository.findById(id).get();
		}
	   
	   @Transactional
		public List<Artist> findAll() {
			return (List<Artist>) this.artistRepository.findAll();
		}
	   
	   @Transactional
	   public void deleteById(Long id) {
		   this.artistRepository.deleteById(id);
	   }
	    @Transactional
	  public List<Artist> findArtistsNotInComic(Long id){
	    	return (List<Artist>) this.artistRepository.findArtistsNotInComic(id);
	    }
	    @Transactional
		public List<Artist> findByName(String name) {
			// TODO Auto-generated method stub
			return this.artistRepository.findByName(name);
		}
}
