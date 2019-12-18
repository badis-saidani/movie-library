import { Component, OnInit } from '@angular/core';
import { Movie } from 'src/app/core/models/Movie';
import { Type } from './../../core/models/Type';
import { NgbDate, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { Router } from '@angular/router';
import { MovieService } from 'src/app/core/services/movie.service';

@Component({
  selector: 'app-movies',
  templateUrl: './movies.component.html',
  styleUrls: ['./movies.component.css']
})
export class MoviesComponent implements OnInit {
  movies: Movie[] = [];
  temp: Movie[] = [];
  movie: Movie = new Movie();
  types: string[];
  closingResult: string;
  isSaveFailed = false;
  toBeUpdated = false;
  errorMessage = '';
  transformedReleaseDate: NgbDate;
  sortingObject: {
    filter: string;
    direction: number;
  };
  displayedMovies: Movie[] = [];

  movieForm: FormGroup;
  submitted = false;

  directors: string[] = [];

  constructor(
    private movieService: MovieService,
    private router: Router,
    private modalService: NgbModal,
    private datePipe: DatePipe,
    private formBuilder: FormBuilder
  ) {}

  ngOnInit() {
    this.getAllMovies();
    this.types = Object.keys(Type);
    this.types.forEach(x => {
      console.log(x);
    });
    this.sortingObject = {
      direction: 1,
      filter: ''
    };

    this.movieForm = this.formBuilder.group({
      title: ['', Validators.required],
      director: ['', Validators.required],
      type: ['', Validators.required],
      releaseDate: ['', Validators.required]
    });
  }

  getAllMovies() {
    this.movieService.getAllMovies().subscribe(
      res => {
        this.movies = res;
        this.temp = res;
        this.displayedMovies = res;
      },
      err => {
        alert('Error occurred while downloading the list of movies;');
      }
    );
  }

  createUpdateMovie(movie: Movie, content, toBeUpdated) {
    this.movie = movie;
    this.toBeUpdated = toBeUpdated;

    if (toBeUpdated === false) {
      this.movie = new Movie();
      this.transformedReleaseDate = null;
      this.directors = [];
    } else {
      this.directors = this.movie.director.split(',');
      const dt: string[] = this.movie.releaseDate.split('/');
      this.transformedReleaseDate = new NgbDate(
        parseInt(dt[2], 10),
        parseInt(dt[1], 10),
        parseInt(dt[0], 10)
      );
    }

    this.modalService
      .open(content, { ariaLabelledBy: 'modal body' })
      .result.then(result => {
        this.movie.releaseDate = this.datePipe.transform(
          new Date(
            this.transformedReleaseDate.year,
            this.transformedReleaseDate.month,
            this.transformedReleaseDate.day
          ),
          'dd/MM/yyyy'
        );
      });
  }

  onSubmit() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.movieForm.invalid) {
      return;
    }
    console.log(
      'badis update? in onSubmit: ' +
        this.toBeUpdated +
        ' code : ' +
        this.movie.id
    );
    this.movie.releaseDate = this.datePipe.transform(
      new Date(
        this.transformedReleaseDate.year,
        this.transformedReleaseDate.month - 1,
        this.transformedReleaseDate.day
      ),
      'dd/MM/yyyy'
    );

    if (this.toBeUpdated === false) {
      console.log('badis look: ' + this.transformedReleaseDate.month);
      this.movieService.postMovie(this.movie).subscribe(
        res => {
          this.reloadPage();
        },
        err => {
          console.log(err);
          this.errorMessage = err.error.message;

          alert('An error occurred while saving the movie');
        }
      );
    } else {
      this.movieService.updateMovie(this.movie).subscribe(
        res => {
          this.reloadPage();
        },
        err => {
          alert('An error occurred while updating the movie');
        }
      );
    }
  }

  removeMovie(movie: Movie) {
    const result = confirm(`Are you sure want to delete the: ${movie.title}?`);
    if (!!result) {
      this.movieService.deleteMovie(movie.id).subscribe(
        data => {
          console.log(data);
          this.reloadPage();
        },
        error => alert('Error!')
      );
    }
  }

  reloadPage() {
    window.location.reload();
  }

  search($event: any): void {
    this.displayedMovies = this.temp;
    const value: string = $event.target.value.toLowerCase().trim();
    if (!!value) {
      this.displayedMovies = this.temp.filter(movie => {
        return (
          movie.title.toLowerCase().indexOf(value) !== -1 ||
          movie.director.toLowerCase().indexOf(value) !== -1 ||
          movie.releaseDate.indexOf(value) !== -1 ||
          movie.type.toLowerCase().indexOf(value) !== -1
        );
      });
    }
  }

  sort(filter: string) {
    this.sortingObject.filter = filter;
    this.sortingObject.direction *= -1;
    this.displayedMovies.sort(
      (x, y) =>
        x[filter].localeCompare(y[filter]) * this.sortingObject.direction
    );
  }

  hideModal() {
    this.modalService.dismissAll();
    this.onReset();
  }

  onReset() {
    this.submitted = false;
    this.movieForm.reset();
  }

  addNewDirector(director: string) {
    if (director !== '' && director != null) {
      this.directors.push(director);
      this.movie.director = this.directors.toString();
    }
  }

  get f() {
    return this.movieForm.controls;
  }

  removeLastDirector() {
    this.directors.pop();
    this.movie.director = this.directors.toString();
  }
}
