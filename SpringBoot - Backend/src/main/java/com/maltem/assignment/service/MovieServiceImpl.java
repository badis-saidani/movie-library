package com.maltem.assignment.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.maltem.assignment.model.Movie;

@Service(MovieService.SERVICE_NAME)
public class MovieServiceImpl implements MovieService {

	private static final String dateFormat = "dd/MM/yyyy";
	private final static String MOVIES_FILE_NAME = "./movies.json";

	private ObjectMapper mapper = new ObjectMapper();

	public List<Movie> getData() throws IOException, JsonParseException, JsonMappingException {
		this.mapper.setDateFormat(new SimpleDateFormat(dateFormat));
		this.mapper.enable(SerializationFeature.INDENT_OUTPUT);

		return this.mapper.readValue(new ClassPathResource(MOVIES_FILE_NAME).getFile(),
				this.mapper.getTypeFactory().constructCollectionType(List.class, Movie.class));
	}

	public void writeOnMoviesFile(List<Movie> movies) throws IOException {
		this.mapper.writeValue(new ClassPathResource(MOVIES_FILE_NAME).getFile(), movies);
	}

	public boolean ValidateMovie(Movie movie) {
		return movie.getTitle() != null && movie.getDirector() != null && movie.getType() != null
				&& movie.getReleaseDate() != null && ValidateDate(movie.getReleaseDate());
	}

	public boolean ValidateDate(String date) {
		SimpleDateFormat df = new SimpleDateFormat(dateFormat);
		try {
			df.parse(date);
		} catch (ParseException ex) {
			return false;
		}
		return true;
	}

}
