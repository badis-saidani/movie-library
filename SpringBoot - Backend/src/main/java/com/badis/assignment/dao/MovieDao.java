package com.badis.assignment.dao;

import java.io.IOException;
import java.util.List;

import com.badis.assignment.model.Movie;

/**
 * 
 * @author Badis Saidani
 * @date 12/17/2019
 *
 */
public interface MovieDao {

	/**
	 * 
	 * @return list of movies
	 */
	public List<Movie> findAllMovies();

	/**
	 * 
	 * @param id
	 * @return a movie by its id
	 */
	public Movie findById(Integer id);

	/**
	 * 
	 * @param movie
	 * @return the id of the new movie
	 * @throws IOException
	 */
	public Integer createMovie(Movie movie) throws IOException;

	/**
	 * Update a movie
	 * 
	 * @param updatedMovie
	 * @throws IOException
	 */
	public void updateMovie(Movie updatedMovie) throws IOException;

	/**
	 * Remove a movie by its id
	 * 
	 * @param id
	 * @throws IOException
	 */
	public void removeMovie(Integer id) throws IOException;
}
