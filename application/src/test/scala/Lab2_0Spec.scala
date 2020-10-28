import java.time.LocalDate

import model._
import org.scalatest.{AsyncFlatSpec, BeforeAndAfterAll}
import datatables.Profile.api._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

class Lab2_0Spec extends AsyncFlatSpec with BeforeAndAfterAll {
  val db = Database.forConfig("postgresql")
  val lab20 = new Lab2_0(db)
  implicit val ec: ExecutionContext = ExecutionContext.global

  "Lab 2 Part 0" should "create database schema" in {
    lab20.create.map(_ => succeed)
  }

  it should "implement Create and Read correctly" in {
    for {
      _ <- Future.sequence(SampleData.actors.map(lab20.createActor))
      actors <- lab20.getAllActors
      _ <- Future.sequence(SampleData.genres.map(lab20.createGenre))
      genres <- lab20.getAllGenres
      _ <- Future.sequence(SampleData.directors.map(lab20.createDirector))
      directors <- lab20.getAllDirectors
      _ <- Future.sequence(SampleData.reviewers.map(lab20.createReviewer))
      reviewers <- lab20.getAllReviewers
      _ <- Future.sequence(SampleData.movies.map(lab20.createMovie))
      movies <- lab20.getAllMovies
      _ <- Future.sequence(SampleData.movieCasts.map(lab20.createMovieCast))
      movieCasts <- lab20.getAllMovieCasts
      _ <- Future.sequence(SampleData.movieGenres.map(lab20.createMovieGenre))
      movieGenres <- lab20.getAllMovieGenres
      _ <- Future.sequence(
        SampleData.movieDirections.map(lab20.createMovieDirection))
      movieDirection <- lab20.getAllMovieDirections
      _ <- Future.sequence(SampleData.ratings.map(lab20.createRating))
      ratings <- lab20.getAllRatings

    } yield {
      assert(SampleData.actors == actors)
      assert(SampleData.genres == genres)
      assert(SampleData.directors == directors)
      assert(SampleData.reviewers == reviewers)
      assert(SampleData.movieCasts == movieCasts)
      assert(SampleData.movieGenres == movieGenres)
      assert(SampleData.movieDirections == movieDirection)
      assert(SampleData.ratings == ratings)
      assert(SampleData.movies == movies)
    }
  }

  it should "implement Update and Delete correctly" in {
    val movie = Movie(901,
      "Vertigo",
      1958,
      128,
      "English",
      Option(LocalDate.parse("1958-08-24")),
      "UK").copy(title = "New title")
    val movieCast =
      MovieCast(101, 901, "John Scottie Ferguson").copy(role = "new role")
    val rating = Rating(901, 9001, Option(8.40), Option(263575))
      .copy(reviewStars = Option(10.0))
    val reviewer =
      Reviewer(9001, Option("Righty Sock")).copy(name = Option("new name"))
    val genre = Genre(1010, "Mystery").copy(title = "new title")
    val actor =
      Actor(101, "James", "Stewart", "M").copy(firstName = "new first name")
    val director =
      Director(201, "Alfred", "Hitchcock").copy(firstName = "new first name")

    for {
      _ <- lab20.updateMovieById(movie.id, movie)
      movies <- lab20.getAllMovies
      _ <- lab20.updateMovieCastById(movieCast.actorId,
        movieCast.movieId,
        movieCast)
      movieCasts <- lab20.getAllMovieCasts
      _ <- lab20.updateRatingById(rating.movieId, rating.reviewerId, rating)
      ratings <- lab20.getAllRatings
      _ <- lab20.updateReviewerById(reviewer.id, reviewer)
      reviewers <- lab20.getAllReviewers
      _ <- lab20.updateGenreById(genre.id, genre)
      genres <- lab20.getAllGenres
      _ <- lab20.updateActorById(actor.id, actor)
      actors <- lab20.getAllActors
      _ <- lab20.updateDirectorById(director.id, director)
      directors <- lab20.getAllDirectors

      _ <- lab20.deleteMovieById(movie.id)
      movies2 <- lab20.getAllMovies
      _ <- lab20.deleteMovieCastById(movieCast.actorId, movieCast.movieId)
      movieCasts2 <- lab20.getAllMovieCasts
      _ <- lab20.deleteRatingById(rating.movieId, rating.reviewerId)
      ratings2 <- lab20.getAllRatings
      _ <- lab20.deleteReviewerById(reviewer.id)
      reviewers2 <- lab20.getAllReviewers
      _ <- lab20.deleteGenreById(genre.id)
      genres2 <- lab20.getAllGenres
      _ <- lab20.deleteActorById(actor.id)
      actors2 <- lab20.getAllActors
      _ <- lab20.deleteDirectorById(director.id)
      directors2 <- lab20.getAllDirectors
    } yield {
      assert(movies.contains(movie))
      assert(movieCasts.contains(movieCast))
      assert(ratings.contains(rating))
      assert(reviewers.contains(reviewer))
      assert(genres.contains(genre))
      assert(actors.contains(actor))
      assert(directors.contains(director))

      assert(!movies2.contains(movie))
      assert(!movieCasts2.contains(movieCast))
      assert(!ratings2.contains(rating))
      assert(!reviewers2.contains(reviewer))
      assert(!genres2.contains(genre))
      assert(!actors2.contains(actor))
      assert(!directors2.contains(director))
    }
  }

  override def afterAll(): Unit = {
    Await.result(lab20.drop.andThen {
      case _ => db.close()
    }, Duration.Inf)

  }

  override def beforeAll(): Unit = {
    Await.result(
      lab20.drop
        .map(_ => "ok")
        .recover({ case ex => "error" })
        .map {
          case "ok" => println("DB is clear and ready")
          case "error" =>
            println("DB is in unknown state, check the drop script")
        },
      Duration.Inf
    )
  }

}
