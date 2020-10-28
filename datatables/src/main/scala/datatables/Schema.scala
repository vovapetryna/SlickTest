package datatables

import java.time.LocalDate

import datatables.Profile.api._
import model._

class ReviewerTable(tag: Tag) extends Table[Reviewer](tag, "reviewers") {
  def id = column[Int]("id", O.PrimaryKey)
  def name = column[Option[String]]("name")
  def * = (id, name).mapTo[Reviewer]
}

object ReviewerTable {
  lazy val query = TableQuery[ReviewerTable]
  def byId(id: Int) = query.filter(_.id === id)
}

class GenreTable(tag: Tag) extends Table[Genre](tag, "genres") {
  def id = column[Int]("id", O.PrimaryKey)
  def title = column[String]("title")
  def * = (id, title).mapTo[Genre]
}

object GenreTable {
  lazy val query = TableQuery[GenreTable]
  def byId(id: Int) = query.filter(_.id === id)
}

class DirectorTable(tag: Tag) extends Table[Director](tag, "directors") {
  def id = column[Int]("id", O.PrimaryKey)
  def firstName = column[String]("firstName")
  def lastName = column[String]("lastName")
  def * = (id, firstName, lastName).mapTo[Director]
}

object DirectorTable {
  lazy val query = TableQuery[DirectorTable]
  def byId(id: Int) = query.filter(_.id === id)
}

class ActorTable(tag: Tag) extends Table[Actor](tag, "actors") {
  def id = column[Int]("id", O.PrimaryKey)
  def firstName = column[String]("firstName")
  def lastName = column[String]("lastName")
  def gender = column[String]("gender")
  def * = (id, firstName, lastName, gender).mapTo[Actor]
}

object ActorTable {
  lazy val query = TableQuery[ActorTable]
  def byId(id: Int) = query.filter(_.id === id)
}

class MovieTable(tag: Tag) extends Table[Movie](tag, "movies") {
  def id = column[Int]("id", O.PrimaryKey)
  def title = column[String]("title")
  def year = column[Int]("year")
  def time = column[Int]("time")
  def language = column[String]("language")
  def releaseDate = column[Option[LocalDate]]("releaseDate")
  def releaseCountry = column[String]("releaseCountry")
  def * =
    (id, title, year, time, language, releaseDate, releaseCountry).mapTo[Movie]
}

object MovieTable {
  lazy val query = TableQuery[MovieTable]
  def byId(id: Int) = query.filter(_.id === id)
}

class MovieGenreTable(tag: Tag) extends Table[MovieGenre](tag, "moviesGenres") {
  def movieId = column[Int]("movieId")
  def genreId = column[Int]("genreId")
  def * = (movieId, genreId).mapTo[MovieGenre]

  def pk = primaryKey("MovieGenrePk", (movieId, genreId))
  def movie =
    foreignKey("MovieGenreTableMovieTableId", movieId, MovieTable.query)(
      _.id,
      onDelete = ForeignKeyAction.Cascade
    )
  def genre =
    foreignKey("MovieGenreTableGenreTableId", genreId, GenreTable.query)(
      _.id,
      onDelete = ForeignKeyAction.Cascade
    )
}

object MovieGenreTable {
  lazy val query = TableQuery[MovieGenreTable]
  def byId(movieId: Int, genreId: Int) =
    query.filter(r => (r.movieId === movieId) && (r.genreId === genreId))
}

class MovieCastTable(tag: Tag) extends Table[MovieCast](tag, "moviesCasts") {
  def actorId = column[Int]("actorId")
  def movieId = column[Int]("movieId")
  def role = column[String]("role")
  def * = (actorId, movieId, role).mapTo[MovieCast]

  def pk = primaryKey("MovieCastPk", (actorId, movieId))
  def actor =
    foreignKey("MovieCastTableActorTableId", actorId, ActorTable.query)(
      _.id,
      onDelete = ForeignKeyAction.Cascade
    )
  def movie =
    foreignKey("MovieCastTableMovieTableId", movieId, MovieTable.query)(
      _.id,
      onDelete = ForeignKeyAction.Cascade
    )
}

object MovieCastTable {
  lazy val query = TableQuery[MovieCastTable]
  def byId(actorId: Int, movieId: Int) =
    query.filter(r => (r.actorId === actorId) && (r.movieId === movieId))
}

class MovieDirectionTable(tag: Tag)
    extends Table[MovieDirection](tag, "moviesDirections") {
  def directorId = column[Int]("directorId")
  def movieId = column[Int]("movieId")
  def * = (directorId, movieId).mapTo[MovieDirection]
  def pk = primaryKey("MovieDirectionPk", (directorId, movieId))

  def movie =
    foreignKey("MovieDirectionTableMovieTableId", movieId, MovieTable.query)(
      _.id,
      onDelete = ForeignKeyAction.Cascade
    )
  def director =
    foreignKey(
      "MovieDirectionTableDirectorTableId",
      directorId,
      DirectorTable.query
    )(_.id, onDelete = ForeignKeyAction.Cascade)
}

object MovieDirectionTable {
  lazy val query = TableQuery[MovieDirectionTable]
  def byId(directorId: Int, movieId: Int) =
    query.filter(r => (r.directorId === directorId) && (r.movieId === movieId))
}

class RatingTable(tag: Tag) extends Table[Rating](tag, "ratings") {
  def movieId = column[Int]("movieId")
  def reviewerId = column[Int]("reviewerId")
  def reviewStars =
    column[Option[Double]]("reviewStars", O.SqlType("double precision"))
  def numberOfRatings = column[Option[Int]]("numberOfRatings")
  def * = (movieId, reviewerId, reviewStars, numberOfRatings).mapTo[Rating]

  def pk = primaryKey("RatingTablePk", (movieId, reviewerId))

  def movie =
    foreignKey("RatingTableMovieTableId", movieId, MovieTable.query)(
      _.id,
      onDelete = ForeignKeyAction.Cascade
    )
  def reviewer =
    foreignKey("RatingTableReviewerTableId", reviewerId, ReviewerTable.query)(
      _.id,
      onDelete = ForeignKeyAction.Cascade
    )
}

object RatingTable {
  lazy val query = TableQuery[RatingTable]
  def byId(movieId: Int, reviewerId: Int) =
    query.filter(r => (r.movieId === movieId) && (r.reviewerId === reviewerId))
}
