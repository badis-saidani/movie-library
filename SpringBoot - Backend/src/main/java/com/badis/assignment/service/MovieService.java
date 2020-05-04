package com.badis.assignment.service;

import java.io.IOException;
import java.util.List;

import com.badis.assignment.model.Movie;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * 
 * @author Badis Saidani
 * @date 12/17/2019
 */
public interface MovieService {

	public static String SERVICE_NAME = "libraryMovieService";

	/**
	 * 
	 * @return list of movies from the json file
	 * @throws IOException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 */
	List<Movie> getData() throws IOException, JsonParseException, JsonMappingException;

	/**
	 * saves updates on the json file
	 * 
	 * @param movies
	 * @throws IOException
	 */
	void writeOnMoviesFile(List<Movie> movies) throws IOException;

	/**
	 * check if a movie is valid
	 * 
	 * @param movie
	 * @return
	 */
	boolean ValidateMovie(Movie movie);

	/**
	 * check if the date respects the format dd/mm/yyyy
	 * 
	 * @param date
	 * @return
	 */
	boolean ValidateDate(String date);

}
