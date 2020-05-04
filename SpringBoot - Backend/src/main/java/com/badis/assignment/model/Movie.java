package com.badis.assignment.model;

/**
 * 
 * @author Badis Saidani
 * @date 12/17/2019
 */
public class Movie {

	private Integer id;
	private String title;
	private String director;
	private String releaseDate;
	private String type;

	public Movie() {
		super();
	}

	public Movie(String title, String director, String releaseDate, String type) {
		super();

		this.title = title;
		this.director = director;
		this.releaseDate = releaseDate;
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Movie [id=" + id + ", title=" + title + ", director=" + director + ", releaseDate=" + releaseDate
				+ ", type=" + type + "]";
	}

}
