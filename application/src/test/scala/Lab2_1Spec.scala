import org.scalatest.{AsyncFlatSpec, BeforeAndAfterAll}
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

class Lab2_1Spec extends AsyncFlatSpec with BeforeAndAfterAll {
  val db = Database.forConfig("postgresql")
  val lab20 = new Lab2_0(db)
  val lab21 = new Lab2_1(db)
  implicit val ec: ExecutionContext = ExecutionContext.global

  "Lab 2 Part 1" should "give +1 DRP for 1 task" in {
    lab21.task1.map(
      result =>
        assert(
          result == Set(
            //mov_title	mov_year
            ("Vertigo", 1958),
            ("The Innocents", 1961),
            ("Lawrence of Arabia", 1962),
            ("The Deer Hunter", 1978),
            ("Amadeus", 1984),
            ("Blade Runner", 1982),
            ("Eyes Wide Shut", 1999),
            ("The Usual Suspects", 1995),
            ("Chinatown", 1974),
            ("Boogie Nights", 1997),
            ("Annie Hall", 1977),
            ("Princess Mononoke", 1997),
            ("The Shawshank Redemption", 1994),
            ("American Beauty", 1999),
            ("Titanic", 1997),
            ("Good Will Hunting", 1997),
            ("Deliverance", 1972),
            ("Trainspotting", 1996),
            ("The Prestige", 2006),
            ("Donnie Darko", 2001),
            ("Slumdog Millionaire", 2008),
            ("Aliens", 1986),
            ("Beyond the Sea", 2004),
            ("Avatar", 2009),
            ("Seven Samurai", 1954),
            ("Spirited Away", 2001),
            ("Back to the Future", 1985),
            ("Braveheart", 1995)
          )))
  }

  it should "give +1 DRP for 2 task" in {
    lab21.task2.map { result =>
      assert(result.contains(1999))
    }
  }

  it should "give +1 DRP for 3 task" in {
    lab21.task3.map { result =>
      assert(
        result == Set(
          //mov_title
          "Eyes Wide Shut",
          "American Beauty"
        ))
    }
  }

  it should "give +1 DRP for 4 task" in {
    lab21.task4.map { result =>
      assert(
        result == Set(
          //mov_title
          "Vertigo",
          "The Innocents",
          "Lawrence of Arabia",
          "The Deer Hunter",
          "Amadeus",
          "Blade Runner",
          "The Usual Suspects",
          "Chinatown",
          "Boogie Nights",
          "Annie Hall",
          "Princess Mononoke",
          "The Shawshank Redemption",
          "Titanic",
          "Good Will Hunting",
          "Deliverance",
          "Trainspotting",
          "Aliens",
          "Seven Samurai",
          "Back to the Future",
          "Braveheart"
        ))
    }
  }

  it should "give +1 DRP for 5 task" in {
    lab21.task5.map { result =>
      assert(
        result == Set(
          "Vertigo",
          "The Innocents",
          "Lawrence of Arabia",
          "The Deer Hunter",
          "Amadeus",
          "Blade Runner",
          "Eyes Wide Shut",
          "The Usual Suspects",
          "Chinatown",
          "Boogie Nights",
          "Annie Hall",
          "Princess Mononoke",
          "The Shawshank Redemption",
          "American Beauty",
          "Titanic",
          "Good Will Hunting",
          "Deliverance",
          "Trainspotting",
          "The Prestige",
          "Donnie Darko",
          "Slumdog Millionaire",
          "Aliens",
          "Beyond the Sea",
          "Avatar",
          "Seven Samurai",
          "Spirited Away",
          "Back to the Future",
          "Braveheart",
          "Righty Sock",
          "Jack Malvern",
          "Flagrant Baronessa",
          "Alec Shaw",
          "Victor Woeltjen",
          "Simon Wright",
          "Neal Wruck",
          "Paul Monks",
          "Mike Salvati",
          "Wesley S. Walker",
          "Sasha Goldshtein",
          "Josh Cates",
          "Krug Stillo",
          "Scott LeBrun",
          "Hannah Steele",
          "Vincent Cadena",
          "Brandt Sponseller",
          "Richard Adams"
        ))
    }
  }

  it should "give +1 DRP for 6 task" in {
    lab21.task6.map { result =>
      assert(
        result == Set(
          //rev_name
          "Righty Sock",
          "Jack Malvern",
          "Flagrant Baronessa",
          "Victor Woeltjen",
          "Simon Wright",
          "Mike Salvati",
          "Sasha Goldshtein",
          "Righty Sock",
          "Krug Stillo",
          "Hannah Steele",
          "Vincent Cadena",
          "Brandt Sponseller"
        ))
    }
  }

  it should "give +1 DRP for 7 task" in {
    lab21.task7.map { result =>
      assert(
        result == Set(
          //mov_title
          "The Deer Hunter",
          "Amadeus",
          "Eyes Wide Shut",
          "The Shawshank Redemption",
          "Deliverance",
          "The Prestige",
          "Seven Samurai",
          "Spirited Away",
          "Back to the Future"
        ))
    }
  }

  it should "give +1 DRP for 8 task" in {
    lab21.task8.map { result =>
      assert(
        result == Set(
          //mov_title
          "Amadeus",
          "Eyes Wide Shut",
          "Deliverance"
        ))
    }
  }

  it should "give +1 DRP for 9 task" in {
    lab21.task9.map { result =>
      assert(
        result == Set(
          //mov_id	mov_title	mov_year
          (910, "Boogie Nights", 1997)
        ))
    }
  }

  it should "give +1 DRP for 10 task" in {
    lab21.task10.map { result =>
      assert(result.contains(111))
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
        .recover({ case _ => "error" })
        .map {
          case "ok" => println("DB is clear")
          case "error" =>
            println("DB is in unknown state, check the drop script")
        },
      Duration.Inf
    )
    Await.result(
      lab20.create
        .map(_ => "ok")
        .recover({ case _ => "error" })
        .map {
          case "ok" => println("Created DB schema")
          case "error" =>
            println("DB is in unknown state, check the create script")
        },
      Duration.Inf
    )
    Await.result(
      (for {
        _ <- Future.sequence(SampleData.actors.map(lab20.createActor))
        _ <- Future.sequence(SampleData.genres.map(lab20.createGenre))
        _ <- Future.sequence(SampleData.directors.map(lab20.createDirector))
        _ <- Future.sequence(SampleData.reviewers.map(lab20.createReviewer))
        _ <- Future.sequence(SampleData.movies.map(lab20.createMovie))
        _ <- Future.sequence(SampleData.movieCasts.map(lab20.createMovieCast))
        _ <- Future.sequence(SampleData.movieGenres.map(lab20.createMovieGenre))
        _ <- Future.sequence(
          SampleData.movieDirections.map(lab20.createMovieDirection))
        _ <- Future.sequence(SampleData.ratings.map(lab20.createRating))
      } yield ())
        .map(_ => "ok")
        .recover({ case _ => "error" })
        .map {
          case "ok" => println("DB is ready")
          case "error" =>
            println("DB is in unknown state, check the insertion scripts")
        },
      Duration.Inf
    )
  }

}
