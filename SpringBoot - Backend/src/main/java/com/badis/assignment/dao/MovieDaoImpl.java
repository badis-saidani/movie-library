package com.badis.assignment.dao;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import com.badis.assignment.model.Movie;
import com.badis.assignment.service.MovieService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Repository
public class MovieDaoImpl implements MovieDao {

	@Autowired
	@Qualifier(MovieService.SERVICE_NAME)
	private MovieService movieService;

	private List<Movie> movies;

	@PostConstruct
	public void loadFile() throws Exception {
		movies = movieService.getData();
	}

	@Override
	public List<Movie> findAllMovies() {
		return this.movies;
	}

	@Override
	public Movie findById(Integer id) {
		Movie movie = findAllMovies().stream().filter(p -> p.getId() == id).findFirst().orElse(null);
		return movie;
	}

	@Override
	public Integer createMovie(Movie movie) throws IOException {
		if (movieService.ValidateMovie(movie)) {
			Optional<Integer> max = this.movies.stream().map(Movie::getId).max(Comparator.naturalOrder());
			System.out.println("maxo = " + max);
			movie.setId(max.get() + 1);
			this.movies.add(movie);
			movieService.writeOnMoviesFile(this.movies);
			return movie.getId();

		} else {
			throw new IllegalArgumentException("An Error occurs while creating the movie");
		}
	}

	@Override
	public void updateMovie(Movie updatedMovie) throws IOException {
		if (movieService.ValidateMovie(updatedMovie)) {
			Movie currentMovie = findById(updatedMovie.getId());
			if (currentMovie != null) {
				currentMovie.setTitle(updatedMovie.getTitle());
				currentMovie.setDirector(updatedMovie.getDirector());
				currentMovie.setReleaseDate(updatedMovie.getReleaseDate());
				currentMovie.setType(updatedMovie.getType());
				movieService.writeOnMoviesFile(this.movies);
			} else {
				throw new NoSuchElementException("An Error occurs while updating the movie");
			}
		} else {
			throw new IllegalArgumentException("Data not valid");
		}

	}

	@Override
	public void removeMovie(Integer id) throws IOException {
		if (findById(id) != null) {
			this.movies.removeIf(m -> m.getId() == id);
			movieService.writeOnMoviesFile(this.movies);
		} else {
			throw new NoSuchElementException("Couldn't remove the movie, please check the ID");
		}

	}

}
