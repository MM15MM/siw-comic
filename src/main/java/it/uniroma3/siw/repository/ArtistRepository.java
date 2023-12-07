package it.uniroma3.siw.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.Artist;


public interface ArtistRepository extends CrudRepository<Artist, Long>{

	public boolean existsByNameAndEmail(String name, String email);	

	@Query(value="select * "
			+ "from artist a "
			+ "where a.id not in "
			+ "(select authors_id "
			+ "from comic_authors "
			+ "where comic_authors.written_comics_id = :comicId)", nativeQuery=true)
	public Iterable<Artist> findArtistsNotInComic(@Param("comicId") Long id);

	public boolean existsByNameAndSurname(String name, String surname);



}
