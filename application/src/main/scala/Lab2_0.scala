import datatables.Profile.api._
import model._

import scala.concurrent.{ExecutionContext, Future}

class Lab2_0(db: Database) {

  /** Creates all database schema */
  def create(implicit ec: ExecutionContext): Future[Unit] = {
    db.run(
      DBIO.seq(
        datatables.DirectorTable.query.schema.create,
        datatables.ActorTable.query.schema.create,
        datatables.GenreTable.query.schema.create,
        datatables.MovieTable.query.schema.create,
        datatables.ReviewerTable.query.schema.create,
        datatables.MovieCastTable.query.schema.create,
        datatables.MovieDirectionTable.query.schema.create,
        datatables.MovieGenreTable.query.schema.create,
        datatables.RatingTable.query.schema.create
      )
    )
  }

  /** Drops all database schema */
  def drop(implicit ec: ExecutionContext): Future[Unit] = {
    db.run(
      DBIO.seq(
        datatables.DirectorTable.query.schema.drop.asTry,
        datatables.ActorTable.query.schema.drop.asTry,
        datatables.GenreTable.query.schema.drop.asTry,
        datatables.MovieTable.query.schema.drop.asTry,
        datatables.ReviewerTable.query.schema.drop.asTry,
        datatables.MovieCastTable.query.schema.drop.asTry,
        datatables.MovieDirectionTable.query.schema.drop.asTry,
        datatables.MovieGenreTable.query.schema.drop.asTry,
        datatables.RatingTable.query.schema.drop.asTry
      )
    )
  }

  /** Creates new record */
  def createMovie(record: Movie)(implicit ec: ExecutionContext): Future[Int] =
    db.run(datatables.MovieTable.query += record)

  /** Gets all records */
  def getAllMovies(implicit ec: ExecutionContext): Future[Set[Movie]] =
    db.run(datatables.MovieTable.query.result).map(_.toSet)

  /** Updates record by id */
  def updateMovieById(id: Int, record: Movie)(implicit
      ec: ExecutionContext
  ): Future[Int] = db.run(datatables.MovieTable.byId(id).update(record))

  /** Deletes the record by id */
  def deleteMovieById(id: Int)(implicit ec: ExecutionContext): Future[Int] =
    db.run(datatables.MovieTable.byId(id).delete)

  /** Creates new record */
  def createReviewer(record: Reviewer)(implicit
      ec: ExecutionContext
  ): Future[Int] = db.run(datatables.ReviewerTable.query += record)

  /** Gets all records */
  def getAllReviewers(implicit ec: ExecutionContext): Future[Set[Reviewer]] =
    db.run(datatables.ReviewerTable.query.result).map(_.toSet)

  /** Updates record by id */
  def updateReviewerById(id: Int, record: Reviewer)(implicit
      ec: ExecutionContext
  ): Future[Int] = db.run(datatables.ReviewerTable.byId(id).update(record))

  /** Deletes the record by id */
  def deleteReviewerById(id: Int)(implicit ec: ExecutionContext): Future[Int] =
    db.run(datatables.ReviewerTable.byId(id).delete)

  /** Creates new record */
  def createRating(record: Rating)(implicit ec: ExecutionContext): Future[Int] =
    db.run(datatables.RatingTable.query += record)

  /** Gets all records */
  def getAllRatings(implicit ec: ExecutionContext): Future[Set[Rating]] =
    db.run(datatables.RatingTable.query.result).map(_.toSet)

  /** Updates record by id */
  def updateRatingById(movieId: Int, reviewerId: Int, record: Rating)(implicit
      ec: ExecutionContext
  ): Future[Int] =
    db.run(datatables.RatingTable.byId(movieId, reviewerId).update(record))

  /** Deletes the record by id */
  def deleteRatingById(movieId: Int, reviewerId: Int)(implicit
      ec: ExecutionContext
  ): Future[Int] =
    db.run(datatables.RatingTable.byId(movieId, reviewerId).delete)

  /** Creates new record */
  def createGenre(record: Genre)(implicit ec: ExecutionContext): Future[Int] =
    db.run(datatables.GenreTable.query += record)

  /** Gets all records */
  def getAllGenres(implicit ec: ExecutionContext): Future[Set[Genre]] =
    db.run(datatables.GenreTable.query.result).map(_.toSet)

  /** Updates record by id */
  def updateGenreById(id: Int, record: Genre)(implicit
      ec: ExecutionContext
  ): Future[Int] = db.run(datatables.GenreTable.byId(id).update(record))

  /** Deletes the record by id */
  def deleteGenreById(id: Int)(implicit ec: ExecutionContext): Future[Int] =
    db.run(datatables.GenreTable.byId(id).delete)

  /** Creates new record */
  def createMovieGenre(record: MovieGenre)(implicit
      ec: ExecutionContext
  ): Future[Int] = db.run(datatables.MovieGenreTable.query += record)

  /** Gets all records */
  def getAllMovieGenres(implicit
      ec: ExecutionContext
  ): Future[Set[MovieGenre]] =
    db.run(datatables.MovieGenreTable.query.result).map(_.toSet)

  /** Deletes the record by id */
  def deleteMovieGenreById(movieId: Int, genreId: Int)(implicit
      ec: ExecutionContext
  ): Future[Int] =
    db.run(datatables.MovieGenreTable.byId(movieId, genreId).delete)

  /** Creates new record */
  def createDirector(record: Director)(implicit
      ec: ExecutionContext
  ): Future[Int] = db.run(datatables.DirectorTable.query += record)

  /** Gets all records */
  def getAllDirectors(implicit ec: ExecutionContext): Future[Set[Director]] =
    db.run(datatables.DirectorTable.query.result).map(_.toSet)

  /** Updates record by id */
  def updateDirectorById(id: Int, record: Director)(implicit
      ec: ExecutionContext
  ): Future[Int] = db.run(datatables.DirectorTable.byId(id).update(record))

  /** Deletes the record by id */
  def deleteDirectorById(id: Int)(implicit ec: ExecutionContext): Future[Int] =
    db.run(datatables.DirectorTable.byId(id).delete)

  /** Creates new record */
  def createMovieDirection(record: MovieDirection)(implicit
      ec: ExecutionContext
  ): Future[Int] = db.run(datatables.MovieDirectionTable.query += record)

  /** Gets all records */
  def getAllMovieDirections(implicit
      ec: ExecutionContext
  ): Future[Set[MovieDirection]] =
    db.run(datatables.MovieDirectionTable.query.result).map(_.toSet)

  /** Deletes the record by id */
  def deleteMovieDirectionById(directorId: Int, movieId: Int)(implicit
      ec: ExecutionContext
  ): Future[Int] =
    db.run(datatables.MovieDirectionTable.byId(directorId, movieId).delete)

  /** Creates new record */
  def createActor(record: Actor)(implicit ec: ExecutionContext): Future[Int] =
    db.run(datatables.ActorTable.query += record)

  /** Gets all records */
  def getAllActors(implicit ec: ExecutionContext): Future[Set[Actor]] =
    db.run(datatables.ActorTable.query.result).map(_.toSet)

  /** Updates record by id */
  def updateActorById(id: Int, record: Actor)(implicit
      ec: ExecutionContext
  ): Future[Int] = db.run(datatables.ActorTable.byId(id).update(record))

  /** Deletes the record by id */
  def deleteActorById(id: Int)(implicit ec: ExecutionContext): Future[Int] =
    db.run(datatables.ActorTable.byId(id).delete)

  /** Creates new record */
  def createMovieCast(record: MovieCast)(implicit
      ec: ExecutionContext
  ): Future[Int] = db.run(datatables.MovieCastTable.query += record)

  /** Gets all records */
  def getAllMovieCasts(implicit ec: ExecutionContext): Future[Set[MovieCast]] =
    db.run(datatables.MovieCastTable.query.result).map(_.toSet)

  /** Updates record by id */
  def updateMovieCastById(actorId: Int, movieId: Int, record: MovieCast)(
      implicit ec: ExecutionContext
  ): Future[Int] =
    db.run(datatables.MovieCastTable.byId(actorId, movieId).update(record))

  /** Deletes the record by id */
  def deleteMovieCastById(actorId: Int, movieId: Int)(implicit
      ec: ExecutionContext
  ): Future[Int] =
    db.run(datatables.MovieCastTable.byId(actorId, movieId).delete)
}
