package com.maltem.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import com.maltem.assignment.model.Movie;

@SpringBootTest(classes = MovieLibraryAssignmentApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MovieLibraryAssignmentApplicationTests {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@LocalServerPort
	private int port;

	private String getBaseUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void testGetAllMovies() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = testRestTemplate.exchange(getBaseUrl() + "/movies", HttpMethod.GET, entity,
				String.class);
		System.out.println("this is the result : " + response.toString());
		assertNotNull(response.getBody());

	}

	@Test
	public void testGetMovieById() {
		Movie movie = testRestTemplate.getForObject(getBaseUrl() + "/movies/3", Movie.class);
		System.out.println("testGetMovieById" + movie.toString());
		assertNotNull(movie);
	}

	@Test
	public void testCreateMovie() {
		Movie movie = new Movie("myMovie", "directorName", "12/16/2019", "Action");

		ResponseEntity<Movie> postResponse = testRestTemplate.postForEntity(getBaseUrl() + "/movies/", movie,
				Movie.class);
		assertNotNull(postResponse);
		assertNotNull(postResponse.getBody());
		System.out.println("testCreateMovie" + postResponse.toString());
	}

	@Test
	public void testUpdateMovie() {
		Movie movie = testRestTemplate.getForObject(getBaseUrl() + "/movies/1", Movie.class);
		movie.setDirector("Badis Saidani");

		testRestTemplate.put(getBaseUrl() + "/movies/", movie, Movie.class);

		Movie updatedMovie = testRestTemplate.getForObject(getBaseUrl() + "/movies/1", Movie.class);
		assertEquals("Updated title", updatedMovie.getTitle());
	}

	@Test
	public void testRemoveMovie() {
		int id = 5;
		Movie movie = testRestTemplate.getForObject(getBaseUrl() + "/movies/" + id, Movie.class);
		assertNotNull(movie);

		testRestTemplate.delete(getBaseUrl() + "/movies/" + id);

		try {
			movie = testRestTemplate.getForObject(getBaseUrl() + "/movies/" + id, Movie.class);
		} catch (final HttpClientErrorException e) {
			assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}
}
