import java.time.LocalDate

import datatables.Profile.api._

import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, ExecutionContext, Future}

class Lab2_3(db: Database) {

  /** 1. Write a query in Slick to find the name of all reviewers who have rated their ratings with a NULL value.  */
  def task1(implicit ec: ExecutionContext): Future[Set[String]] =
    db.run((for {
        r <- datatables.RatingTable.query if r.reviewStars.isEmpty
        reviewer <- r.reviewer if reviewer.name.isDefined
      } yield reviewer.name.get).result)
      .map(_.toSet)

  /** 2. Write a query in Slick to list the first and last names of all the actors who were cast in the movie 'Annie Hall', and the roles they played in that production. */
  //act_fname	act_lname	role
  def task2(implicit
      ec: ExecutionContext
  ): Future[Set[(String, String, String)]] =
    db.run((for {
        m <- datatables.MovieTable.query if m.title === "Annie Hall"
        c <- datatables.MovieCastTable.query if c.movieId === m.id
        actor <- c.actor
      } yield (actor.firstName, actor.lastName, c.role)).result)
      .map(_.toSet)

  /** 3. Write a query in Slick to find the name of movie and director (first and last names) who directed a movie that casted a role for 'Eyes Wide Shut'. */
  //dir_fname	dir_lname
  def task3(implicit ec: ExecutionContext): Future[Option[(String, String)]] =
    db.run((for {
        m <- datatables.MovieTable.query if m.title === "Eyes Wide Shut"
        d <- datatables.MovieDirectionTable.query if d.movieId === m.id
        director <- d.director
      } yield (director.firstName, director.lastName)).result)
      .map(_.headOption)

  /** 4. Write a query in Slick to find the name of movie and director (first and last names) who directed a movie that casted a role as Sean Maguire. */
  //dir_fname	dir_lname	mov_title
  def task4(implicit
      ec: ExecutionContext
  ): Future[Option[(String, String, String)]] =
    db.run((for {
        r <- datatables.MovieCastTable.query if r.role === "Sean Maguire"
        m <- datatables.MovieCastTable.query if m.movieId === r.movieId
        d <- datatables.MovieDirectionTable.query if d.movieId === r.movieId
        director <- d.director
        movie <- m.movie
      } yield (director.firstName, director.lastName, movie.title)).result)
      .map(_.headOption)

  /** 5. Write a query in Slick to list all the actors who acted in a movie before 1990 and also in a movie after 2000. */
  //act_fname	act_lname	mov_title	mov_year
  def task5(implicit
      ec: ExecutionContext
  ): Future[Set[(String, String, String, Int)]] = {
    db.run(
        (for {
          c <- datatables.MovieCastTable.query
          movie <- c.movie if (movie.year < 1990) || (movie.year > 2000)
          actor <- c.actor
        } yield (
          actor.firstName,
          actor.lastName,
          movie.title,
          movie.year
        )).result
      )
      .map(_.toSet)
  }

  /** 6. Write a query in Slick to list first and last name of all the directors with number of genres movies the directed with genres name, and arranged the result alphabetically with the first and last name of the director. */
  //dir_fname	dir_lname	gen_title	count
  def task6(implicit
      ec: ExecutionContext
  ): Future[Vector[(String, String, String, Int)]] = {
    val directorMovie = datatables.DirectorTable.query
      .join(datatables.MovieDirectionTable.query)
      .on(_.id === _.directorId)
      .join(datatables.MovieGenreTable.query)
      .on(_._2.movieId === _.movieId)
      .map {
        case ((director, movie), genre) =>
          (director.firstName, director.lastName, genre.genreId)
      }
    db.run(
        (for {
          directorGenre <- directorMovie.groupBy(d => (d._1, d._2, d._3)).map {
            case ((firstName, lastName, genre), group) =>
              (firstName, lastName, genre, group.length)
          }
          genre <- datatables.GenreTable.query if genre.id === directorGenre._3
        } yield (
          directorGenre._1,
          directorGenre._2,
          genre.title,
          directorGenre._4
        )).result
      )
      .map(_.toVector.sortBy(d => d._1 + d._2))
  }

  /** 7. Write a query in Slick to list all the movies with year and genres. */
  //mov_title mov_year gen_title
  def task7(implicit
      ec: ExecutionContext
  ): Future[Set[(String, Int, String)]] = {
    val movieGenre = datatables.MovieTable.query
      .join(datatables.MovieGenreTable.query)
      .on(_.id === _.movieId)
      .map{case (movie, genre) => (movie.title, movie.year, genre)}
    db.run((for {
      mg <- movieGenre
      genre <- mg._3.genre
    } yield (mg._1, mg._2, genre.title)).result).map(_.toSet)
  }

  /** 8. Write a query in Slick to list all the movies with year, genres, and name of the director. */
  //mov_title mov_year gen_title dir_fname dir_lname
  def task8(implicit
      ec: ExecutionContext
  ): Future[Set[(String, Int, String, String, String)]] = {
    val movieGenreDirecotr = datatables.MovieTable.query
      .join(datatables.MovieGenreTable.query)
      .on(_.id === _.movieId)
      .join(datatables.MovieDirectionTable.query)
      .on(_._1.id === _.movieId)
      .map { case ((movie, genre), director) => (movie.title, movie.year, genre, director) }

    db.run((for {
      mgd <- movieGenreDirecotr
      genre <- mgd._3.genre
      director <- mgd._4.director
    } yield (mgd._1, mgd._2, genre.title, director.firstName, director.lastName)).result).map(_.toSet)
  }

  /** 9. Write a query in Slick to list all the movies with title, year, date of release, movie duration, and first and last name of the director which released before 1st january 1989, and sort the result set according to release date from highest date to lowest. */
  //mov_title mov_year mov_dt_rel mov_time dir_fname dir_lname
  def task9(implicit
      ec: ExecutionContext
  ): Future[Vector[(String, Int, LocalDate, Int, String, String)]] = {
    val movieDirecotr = datatables.MovieTable.query
      .join(datatables.MovieDirectionTable.query)
      .on(_.id === _.movieId)
      .map { case (movie, director) =>
        (
          movie.title,
          movie.year,
          movie.releaseDate,
          movie.time,
          director) }

    db.run((for {
      md <- movieDirecotr if md._3.isDefined && (md._3 < LocalDate.parse("1989-01-01"))
      director <- md._5.director
    } yield (md._1, md._2, md._3.get, md._4, director.firstName, director.lastName)).result).map(_.toVector.sortBy(- _._3.toEpochDay))
  }

  /** 10. Write a query in Slick to compute a report which contain the genres of those movies with their average time and number of movies for each genres. */
  def task10(implicit
      ec: ExecutionContext
  ): Future[Set[(String, Double, Int)]] = {
    val movieGenres = datatables.MovieTable.query
      .join(datatables.MovieGenreTable.query)
      .on(_.id === _.movieId)
      .join(datatables.GenreTable.query)
      .on(_._2.genreId === _.id)
      .map{case ((movie, _), genre) => (genre.title, movie.time.asColumnOf[Double])}

    println(Await.result(db.run(movieGenres.result), 2.seconds))

    db.run((for {
      gm <- movieGenres.groupBy(_._1).map{case (genre, group) => (genre, group.map(_._2).avg, group.length)}
    } yield (gm._1, gm._2.get, gm._3)).result).map(_.toSet)
  }

  /** 11. Write a query in Slick to find those lowest duration movies along with the year, director's name, actor's name and his/her role in that production. */
  //mov_title	mov_year	dir_fname	dir_lname	act_fname	act_lname	role
  def task11(implicit
      ec: ExecutionContext
  ): Future[Option[(String, Int, String, String, String, String, String)]] = ???

  /** 12. Write a query in Slick to find all the years which produced a movie that received a rating of 3 or 4, and sort the result in increasing order. */
  def task12(implicit ec: ExecutionContext): Future[Vector[Int]] = ???

  /** 13. Write a query in Slick to return the reviewer name, movie title, and stars in an order that reviewer name will come first, then by movie title, and lastly by number of stars. */
  //rev_name	mov_title	rev_stars
  def task13(implicit
      ec: ExecutionContext
  ): Future[Vector[(String, String, Option[Double])]] = ???

  /** 14. Write a query in Slick to find movie title and number of stars for each movie that has at least one rating and find the highest number of stars that movie received and sort the result by movie title. */
  def task14(implicit ec: ExecutionContext): Future[Vector[(String, Double)]] =
    ???

  /** 15. Write a query in Slick to find the director's first and last name together with the title of the movie(s) they directed and received the rating. */
  //mov_title	dir_fname	dir_lname	rev_stars
  def task15(implicit
      ec: ExecutionContext
  ): Future[Set[(String, String, String, Double)]] = ???

  /** 16. Write a query in Slick to find the movie title, actor first and last name, and the role for those movies where one or more actors acted in two or more movies. */
  //mov_title	act_fname	act_lname	role
  def task16(implicit
      ec: ExecutionContext
  ): Future[Set[(String, String, String, String)]] = ???

  /** 17. Write a query in Slick to find the first and last name of a director and the movie he or she directed, and the actress appeared which first name was Claire and last name was Danes along with her role in that movie. */
  //dir_fname	dir_lname	mov_title	act_fname	act_lname	role
  def task17(implicit
      ec: ExecutionContext
  ): Future[Set[(String, String, String, String, String, String)]] = ???

  /** 18. Write a query in Slick to find the first and last name of an actor with their role in the movie which was also directed by themselve. */
  //act_fname	act_lname	mov_title	role
  def task18(implicit
      ec: ExecutionContext
  ): Future[Set[(String, String, String, String)]] = ???

  /** 19. Write a query in Slick to find the cast list for the movie Chinatown. */
  //act_fname	act_lname
  def task19(implicit ec: ExecutionContext): Future[Set[(String, String)]] = ???

  /** 20. Write a query in Slick to find the movie in which the actor appeared whose first and last name are 'Harrison' and 'Ford'. */
  def task20(implicit ec: ExecutionContext): Future[Set[String]] = ???

  /** 21. Write a query in Slick to find the highest-rated movie, and report its title, year, rating, and releasing country. */
  //mov_title	mov_year	rev_stars	mov_rel_country
  def task21(implicit
      ec: ExecutionContext
  ): Future[Option[(String, Int, Double, String)]] = ???

  /** 22. Write a query in Slick to find the highest-rated Mystery movie, and report the title, year, and rating. */
  //mov_title	mov_year	rev_stars
  def task22(implicit
      ec: ExecutionContext
  ): Future[Option[(String, Int, Double)]] = ???

  /** 23. Write a query in Slick to generate a report which shows the year when most of the Mystery movies produces, and number of movies and their average rating. */
  //mov_year	gen_title	count	avg
  def task23(implicit
      ec: ExecutionContext
  ): Future[Option[(Int, String, Int, Double)]] = ???

  /** 24. Write a query in Slick to generate a report which contain the columns movie title, name of the female actor, year of the movie, role, movie genres, the director, date of release, and rating of that movie. */
  //mov_title	act_fname	act_lname	mov_year	role	gen_title	dir_fname	dir_lname	mov_dt_rel	rev_stars
  def task24(implicit ec: ExecutionContext): Future[Set[
    (
        String, String, String, Int, String, String, String, String, LocalDate,
        Double
    )
  ]] = ???

}
