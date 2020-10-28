import java.time.LocalDate

import model.{Actor, Movie}
import org.scalatest.{AsyncFlatSpec, BeforeAndAfterAll}
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

class Lab2_2Spec extends AsyncFlatSpec with BeforeAndAfterAll {
  val db = Database.forConfig("postgresql")
  val lab20 = new Lab2_0(db)
  val lab22 = new Lab2_2(db)
  implicit val ec: ExecutionContext = ExecutionContext.global

  "Lab 2 Part 2" should "give +2 DRP for 1 task" in {
    lab22.task1.map(
      result =>
        assert(
          result == Set(
            Actor(111, "Woody", "Allen", "M")
          )))
  }

  it should "give +2 DRP for 2 task" in {
    lab22.task2.map { result =>
      //dir_fname	dir_lname
      assert(result.contains(("Stanley", "Kubrick")))
    }
  }

  it should "give +2 DRP for 3 task" in {
    lab22.task3.map { result =>
      assert(
        result == Set(
          Movie(902,
            "The Innocents",
            1961,
            100,
            "English",
            Option(LocalDate.parse("1962-02-19")),
            "SW"),
          Movie(911,
            "Annie Hall",
            1977,
            93,
            "English",
            Option(LocalDate.parse("1977-04-20")),
            "USA"),
          Movie(926,
            "Seven Samurai",
            1954,
            207,
            "Japanese ",
            Option(LocalDate.parse("1954-04-26")),
            "JP")
        ))
    }
  }

  it should "give +2 DRP for 4 task" in {
    lab22.task4.map { result =>
      assert(
        result == Set(
          //mov_title	mov_year	mov_dt_rel	dir_fname	dir_lname	act_fname	act_lname
          ("Blade Runner",
            1982,
            LocalDate.parse("1982-09-09"),
            "Ridley",
            "Scott",
            "Harrison",
            "Ford"),
          ("Princess Mononoke",
            1997,
            LocalDate.parse("2001-10-19"),
            "Hayao",
            "Miyazaki",
            "Claire",
            "Danes")
        ))
    }
  }

  it should "give +2 DRP for 5 task" in {
    lab22.task5.map { result =>
      assert(
        result == Set(
          "Annie Hall"
        ))
    }
  }

  it should "give +2 DRP for 6 task" in {
    lab22.task6.map { result =>
      assert(
        result == Vector(
          1958, 1961, 1962, 1977, 1982, 1986, 1995, 1997, 1999, 2001, 2004,
          2008, 2009
        ))
    }
  }

  it should "give +2 DRP for 7 task" in {
    lab22.task7.map { result =>
      assert(
        result == Set(
          "Deliverance",
          "Amadeus",
          "Spirited Away",
          "The Prestige",
          "The Deer Hunter",
          "Eyes Wide Shut",
          "Back to the Future",
          "The Shawshank Redemption",
          "Seven Samurai"
        ))
    }
  }

  it should "give +2 DRP for 8 task" in {
    lab22.task8.map { result =>
      assert(
        result == Set(
          "Neal Wruck",
          "Scott LeBrun"
        ))
    }
  }

  it should "give +2 DRP for 9 task" in {
    lab22.task9.map { result =>
      assert(
        result == Vector(
          ("Brandt Sponseller", "Aliens", 8.40),
          ("Flagrant Baronessa", "Lawrence of Arabia", 8.30),
          ("Hannah Steele", "Donnie Darko", 8.10),
          ("Jack Malvern", "The Innocents", 7.90),
          ("Josh Cates", "Good Will Hunting", 4.00),
          ("Krug Stillo", "Braveheart", 7.70),
          ("Mike Salvati", "Annie Hall", 8.10),
          ("Paul Monks", "Boogie Nights", 3.00),
          ("Richard Adams", "Beyond the Sea", 6.70),
          ("Righty Sock", "Titanic", 7.70),
          ("Righty Sock", "Vertigo", 8.40),
          ("Sasha Goldshtein", "American Beauty", 7.00),
          ("Simon Wright", "The Usual Suspects", 8.60),
          ("Victor Woeltjen", "Avatar", 7.30),
          ("Vincent Cadena", "Slumdog Millionaire", 8.00)
        ))
    }
  }

  it should "give +2 DRP for 10 task" in {
    lab22.task10.map { result =>
      assert(result == Set(
        ("Righty Sock", "Titanic"),
        ("Righty Sock", "Vertigo")
      ))
    }
  }

  it should "give +2 DRP for 11 task" in {
    lab22.task11.map { result =>
      assert(result == Vector(
        ("Aliens", 8.40),
        ("American Beauty", 7.00),
        ("Annie Hall", 8.10),
        ("Avatar", 7.30),
        ("Beyond the Sea", 6.70),
        ("Blade Runner", 8.20),
        ("Boogie Nights", 3.00),
        ("Braveheart", 7.70),
        ("Donnie Darko", 8.10),
        ("Good Will Hunting", 4.00),
        ("Lawrence of Arabia", 8.30),
        ("Princess Mononoke", 8.40),
        ("Slumdog Millionaire", 8.00),
        ("The Innocents", 7.90),
        ("The Usual Suspects", 8.60),
        ("Titanic", 7.70),
        ("Vertigo", 8.40)
      ))
    }
  }

  it should "give +2 DRP for 12 task" in {
    lab22.task12.map { result =>
      assert(result == Set(
        "Sasha Goldshtein"
      ))
    }
  }

  it should "give +2 DRP for 13 task" in {
    lab22.task13.map { result =>
      assert(result == Set(
        "Avatar",
        "Lawrence of Arabia",
        "Donnie Darko",
        "Aliens",
        "Vertigo",
        "The Innocents",
        "Slumdog Millionaire",
        "Annie Hall",
        "Good Will Hunting",
        "American Beauty",
        "Titanic",
        "Beyond the Sea",
        "Trainspotting",
        "Princess Mononoke",
        "The Usual Suspects",
        "Blade Runner",
        "Braveheart",
        "Chinatown"
      ))
    }
  }

  it should "give +2 DRP for 14 task" in {
    lab22.task14.map { result =>
      //rev_name	mov_title	rev_stars
      assert(result.contains(("Paul Monks",	"Boogie Nights",	3.00)))
    }
  }

  it should "give +2 DRP for 15 task" in {
    lab22.task15.map { result =>
      assert(result == Set(
        "Titanic",
        "Aliens"
      ))
    }
  }

  it should "give +2 DRP for 16 task" in {
    lab22.task16.map { result =>
      assert(result == Set(
        "Beyond the Sea",
        "American Beauty"
      ))
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
