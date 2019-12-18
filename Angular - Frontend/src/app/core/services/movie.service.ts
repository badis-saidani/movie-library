import { Movie } from './../models/movie';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MovieService {
  private baseUrl = 'http://localhost:8080';

  private ALL_MOVIE_URL = this.baseUrl + '/movies';
  private SAVE_UPDATE_MOVIE_URL = this.baseUrl + '/movies';
  private DELETE_MOVIE_URL = this.baseUrl + '/movies/';

  constructor(private http: HttpClient) {}

  getAllMovies(): Observable<Movie[]> {
    return this.http.get<Movie[]>(this.ALL_MOVIE_URL);
  }
  postMovie(movie: Movie): Observable<Movie> {
    return this.http.post<Movie>(this.SAVE_UPDATE_MOVIE_URL, movie);
  }

  updateMovie(movie: Movie): Observable<Movie> {
    return this.http.put<Movie>(this.SAVE_UPDATE_MOVIE_URL, movie);
  }

  deleteMovie(id: number): Observable<any> {
    return this.http.delete(this.DELETE_MOVIE_URL + id);
  }
}
