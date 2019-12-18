import { Component, OnInit } from '@angular/core';
import { Director } from 'src/app/core/models/Director';
import { MovieService } from 'src/app/core/services/movie.service';
import { Movie } from 'src/app/core/models/Movie';

@Component({
  selector: 'app-authors',
  templateUrl: './authors.component.html',
  styleUrls: ['./authors.component.css']
})
export class AuthorsComponent implements OnInit {
  directors: Director[] = [];
  finalDirectors: Director[] = [];

  constructor(private movieService: MovieService) {}

  ngOnInit() {
    this.getDirectors();
  }

  getDirectors() {
    this.movieService.getAllMovies().subscribe(
      data => {
        data
          .map(movie => movie.director)
          .map(value => {
            const listDirector = value.split(',');

            listDirector.forEach(x => {
              const director: Director = new Director();
              director.name = x;
              director.movies = data.filter(_ => _.director === value);
              if (this.directors.filter(_ => _.name === value).length === 0) {
                this.directors.push(director);
              }
            });

            this.directors.forEach(x => {
              const res = this.finalDirectors.find(y => y.name === x.name);
              if (res === undefined) {
                this.finalDirectors.push(x);
              } else {
                const i = this.finalDirectors.indexOf(res);
                this.finalDirectors[i].movies.concat(x.movies);
              }
            });
          });
      },
      error => {
        alert('Error occured while retrieving directors');
      }
    );
  }

  getDirectorMovies(movies: Movie[]) {
    return movies.map(x => x.title).join(',');
  }
}
