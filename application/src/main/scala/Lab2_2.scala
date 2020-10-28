import java.time.LocalDate

import datatables.Profile.api._
import model._

import scala.concurrent.{ExecutionContext, Future}

class Lab2_2(db: Database) {

  /** 1. Write a query in Slick to list all the information of the actors who played a role in the movie 'Annie Hall'. */
  def task1(implicit ec: ExecutionContext): Future[Set[Actor]] =
    db.run((for {
        m <- datatables.MovieTable.query if m.title === "Annie Hall"
        c <- datatables.MovieCastTable.query if c.movieId === m.id
        a <- datatables.ActorTable.query if a.id === c.actorId
      } yield a).result)
      .map(_.toSet)

  /** 2. Write a query in Slick to find the name of the director (first and last names) who directed a movie that casted a role for 'Eyes Wide Shut'. */
  //dir_fname	dir_lname
  def task2(implicit ec: ExecutionContext): Future[Option[(String, String)]] =
    db.run((for {
      m <- datatables.MovieTable.query if m.title === "Eyes Wide Shut"
      d <- datatables.MovieDirectionTable.query if d.movieId === m.id
      director <- d.director
    } yield (director.firstName, director.lastName)).result.headOption)

  /** 3. Write a query in Slick to list all the movies which released in the country other than UK. */
  def task3(implicit ec: ExecutionContext): Future[Set[Movie]] =
    db.run((for {
        m <- datatables.MovieTable.query.filterNot(_.releaseCountry === "UK")
      } yield m).result)
      .map(_.toSet)

  /** 4. Write a query in Slick to find the movie title, year, date of release, director and actor for those movies which reviewer is unknown. */
  //mov_title	mov_year	mov_dt_rel	dir_fname	dir_lname	act_fname	act_lname
  def task4(implicit
      ec: ExecutionContext
  ): Future[Set[(String, Int, LocalDate, String, String, String, String)]] =
    db.run(
        (for {
          r <- datatables.ReviewerTable.query.filter(_.name.isEmpty)
          m <- datatables.RatingTable.query.filter(_.reviewerId === r.id)
          movie <- m.movie
          a <- datatables.MovieCastTable.query.filter(_.movieId === movie.id)
          d <-
            datatables.MovieDirectionTable.query.filter(_.movieId === movie.id)
          director <- d.director
          act <- a.actor
        } yield (
          movie.title,
          movie.year,
          movie.releaseDate.get,
          director.firstName,
          director.lastName,
          act.firstName,
          act.lastName
        )).result
      )
      .map(_.toSet)

  /** 5. Write a query in Slick to find the titles of all movies directed by the director whose first and last name are Woody Allen. */
  def task5(implicit ec: ExecutionContext): Future[Set[String]] =
    db.run((for {
        d <- datatables.DirectorTable.query
        if (d.lastName === "Allen") && (d.firstName === "Woody")
        m <- datatables.MovieDirectionTable.query if m.directorId === d.id
        movie <- m.movie
      } yield movie.title).result)
      .map(_.toSet)

  /** 6. Write a query in Slick to find all the years which produced at least one movie and that received a rating of more than 3 stars. Show the results in increasing order. */
  def task6(implicit ec: ExecutionContext): Future[Vector[Int]] =
    db.run((for {
        m <- datatables.RatingTable.query.filter(_.reviewStars.isDefined)
        if m.reviewStars > 3.0
        movie <- m.movie
      } yield movie.year).result)
      .map(_.toSet)
      .map(_.toVector)
      .map(_.sorted)

  /** 7. Write a query in Slick to find the titles of all movies that have no ratings. */
  def task7(implicit ec: ExecutionContext): Future[Set[String]] =
    db.run(for {
        m <- datatables.RatingTable.query.map(_.movieId).result
        movie <-
          datatables.MovieTable.query
            .filterNot(_.id inSet m)
            .map(_.title)
            .result
      } yield movie)
      .map(_.toSet)

  /** 8. Write a query in Slick to find the names of all reviewers who have ratings with a NULL value. */
  def task8(implicit ec: ExecutionContext): Future[Set[String]] =
    db.run((for {
        r <- datatables.RatingTable.query if r.reviewStars.isEmpty
        reviewer <- r.reviewer
      } yield reviewer.name.get).result)
      .map(_.toSet)

  /** 9. Write a query in Slick to return the reviewer name, movie title, and stars for those movies which reviewed by a reviewer and must be rated. Sort the result by reviewer name, movie title, and number of stars. */
  def task9(implicit
      ec: ExecutionContext
  ): Future[Vector[(String, String, Double)]] = {
    val jreview =
      datatables.RatingTable.query join datatables.ReviewerTable.query on (_.reviewerId === _.id)
    db.run(
        (for {
          review <- jreview.filter(r =>
            r._2.name.isDefined && r._1.reviewStars.isDefined
          )
          movie <- review._1.movie
        } yield (
          review._2.name.get,
          movie.title,
          review._1.reviewStars.get
        )).result
      )
      .map(_.toVector.sortBy(d => (d._1, d._2, d._3)))
  }

  /** 10. Write a query in Slick to find the reviewer's name and the title of the movie for those reviewers who rated more than one movies. */
  def task10(implicit ec: ExecutionContext): Future[Set[(String, String)]] =
    db.run((for {
        r <- datatables.RatingTable.query.groupBy(_.reviewerId).map {
          case (reviewerId, reviews) => reviewerId -> reviews.length
        }.filter(_._2 > 1)
        reviewer <- datatables.ReviewerTable.query if reviewer.id === r._1
        m <- datatables.RatingTable.query.filter(_.reviewerId === r._1)
        movie <- m.movie
      } yield (reviewer.name.get, movie.title)).result)
      .map(_.toSet)

  /** 11. Write a query in Slick to find the movie title, and the highest number of stars that movie received and arranged the result according to the group of a movie and the movie title appear alphabetically in ascending order. */
  def task11(implicit ec: ExecutionContext): Future[Vector[(String, Double)]] =
    ???

  /** 12. Write a query in Slick to find the names of all reviewers who rated the movie American Beauty. */
  def task12(implicit ec: ExecutionContext): Future[Set[String]] = ???

  /** 13. Write a query in Slick to find the titles of all movies which have been reviewed by anybody except by Paul Monks. */
  def task13(implicit ec: ExecutionContext): Future[Set[String]] = ???

  /** 14. Write a query in Slick to return the reviewer name, movie title, and number of stars for those movies which rating is the lowest one. */
  //rev_name	mov_title	rev_stars
  def task14(implicit
      ec: ExecutionContext
  ): Future[Option[(String, String, Double)]] = ???

  /** 15. Write a query in Slick to find the titles of all movies directed by James Cameron. */
  def task15(implicit ec: ExecutionContext): Future[Set[String]] = ???

  /** 16. Write a query in Slick to find the name of those movies where one or more actors acted in two or more movies. */
  def task16(implicit ec: ExecutionContext): Future[Set[String]] = ???

}
