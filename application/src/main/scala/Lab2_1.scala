import datatables.Profile.api._

import scala.concurrent.{ExecutionContext, Future}

class Lab2_1(db: Database) {

  /** 1.1 Write a query in Slick to find the name and year of the movies. */
  //mov_title	mov_year
  def task1(implicit ec: ExecutionContext): Future[Set[(String, Int)]] =
    db.run(datatables.MovieTable.query.map(m => (m.title, m.year)).result)
      .map(_.toSet)

  /** 1.2. Write a query in Slick to find the year when the movie American Beauty released. */
  def task2(implicit ec: ExecutionContext): Future[Option[Int]] =
    db.run(
        datatables.MovieTable.query
          .withFilter(_.title === "American Beauty")
          .map(_.year)
          .result
      )
      .map(_.headOption)

  /** 1.3. Write a query in Slick to find the movies which was released in the year 1999. */
  //mov_title
  def task3(implicit ec: ExecutionContext): Future[Set[String]] =
    db.run(
        datatables.MovieTable.query.filter(_.year === 1999).map(_.title).result
      )
      .map(_.toSet)

  /** 1.4. Write a query in Slick to find the movies which was released before 1998. */
  //mov_title
  def task4(implicit ec: ExecutionContext): Future[Set[String]] =
    db.run(
        datatables.MovieTable.query.filter(_.year < 1998).map(_.title).result
      )
      .map(_.toSet)

  /** 1.5. Write a query in Slick to return the name of all reviewers and name of movies together in a single list. */
  def task5(implicit ec: ExecutionContext): Future[Set[String]] =
    db.run(
      (for {
        r <-
          datatables.ReviewerTable.query
            .filter(_.name.isDefined)
            .map(_.name)
            .result
        m <- datatables.MovieTable.query.map(_.title).result
      } yield r.map { case Some(name) => name }.toSet ++ m.toSet)
    )

  /** 1.6. Write a query in Slick to find the name of all reviewers who have rated 7 or more stars to their rating. */
  def task6(implicit ec: ExecutionContext): Future[Set[String]] =
    db.run((for {
        rating <- datatables.RatingTable.query
        if rating.reviewStars.isDefined && rating.reviewStars >= 7.0
        reviewer <- rating.reviewer if reviewer.name.isDefined
      } yield reviewer.name.get).result)
      .map(_.toSet)

  /** 1.7. Write a query in Slick to find the titles of all movies that have no ratings. */
  def task7(implicit ec: ExecutionContext): Future[Set[String]] =
    db.run({
        val ratedMovies = datatables.RatingTable.query.map(_.movieId)
        datatables.MovieTable.query.filterNot(_.id in ratedMovies).map(_.title)
      }.result)
      .map(_.toSet)

  /** 1.8. Write a query in Slick to find the titles of the movies with ID 905, 907, 917. */
  def task8(implicit ec: ExecutionContext): Future[Set[String]] =
    db.run(
        datatables.MovieTable.query
          .filter(_.id.inSet(Seq(905, 907, 917)))
          .map(_.title)
          .result
      )
      .map(_.toSet)

  /** 1.9. Write a query in Slick to find the list of all those movies with year which include the words Boogie Nights. */
  //mov_id	mov_title	mov_year
  def task9(implicit ec: ExecutionContext): Future[Set[(Int, String, Int)]] =
    db.run((for {
      movie <- datatables.MovieTable.query if movie.title like "%Boogie Nights%"
    } yield (movie.id, movie.title, movie.year)).result).map(_.toSet)

  /** 1.10. Write a query in Slick to find the ID number for the actor whose first name is 'Woody' and the last name is 'Allen'. */
  def task10(implicit ec: ExecutionContext): Future[Option[Int]] =
    db.run((for {
      actor <- datatables.ActorTable.query if actor.firstName === "Woody" && actor.lastName === "Allen"
    } yield actor.id).result.headOption)

}
