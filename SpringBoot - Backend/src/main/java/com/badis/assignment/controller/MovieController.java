package com.badis.assignment.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.badis.assignment.dao.MovieDao;
import com.badis.assignment.model.Movie;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/movies")
public class MovieController {

	@Autowired
	MovieDao movieDao;

	@GetMapping("")
	public List<Movie> getMovies() throws Exception {
		return movieDao.findAllMovies();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Movie> getMovie(@PathVariable("id") Integer id) {
		Movie movie = movieDao.findById(id);
		if (movie == null)
			throw new RuntimeException("Movie not found " + id);
		return ResponseEntity.ok().body(movie);
	}

	@PostMapping
	public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) throws IOException {
		Integer id = this.movieDao.createMovie(movie);
		movie.setId(id);
		return ResponseEntity.ok().body(movie);
	}

	@PutMapping
	public ResponseEntity<?> updateMovie(@RequestBody Movie movie) throws IOException {
		this.movieDao.updateMovie(movie);
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> removeMovie(@PathVariable("id") Integer id) throws IOException {
		this.movieDao.removeMovie(id);
		return ResponseEntity.ok(HttpStatus.OK);
	}
}
