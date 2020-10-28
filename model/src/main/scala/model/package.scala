import java.time.LocalDate

package object model {

  case class Reviewer(id: Int, name: Option[String])

  case class Rating(
      movieId: Int,
      reviewerId: Int,
      reviewStars: Option[Double],
      numberOfRatings: Option[Int]
  )

  case class Movie(
      id: Int,
      title: String,
      year: Int,
      time: Int,
      language: String,
      releaseDate: Option[LocalDate],
      releaseCountry: String
  )

  case class Genre(id: Int, title: String)

  case class MovieGenre(movieId: Int, genreId: Int)

  case class Director(id: Int, firstName: String, lastName: String)

  case class MovieDirection(directorId: Int, movieId: Int)

  case class Actor(id: Int, firstName: String, lastName: String, gender: String)

  case class MovieCast(actorId: Int, movieId: Int, role: String)

}
