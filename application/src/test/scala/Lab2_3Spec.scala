import java.time.LocalDate

import model.{Actor, Movie}
import org.scalatest.{AsyncFlatSpec, BeforeAndAfterAll}
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

class Lab2_3Spec extends AsyncFlatSpec with BeforeAndAfterAll {
  val db = Database.forConfig("postgresql")
  val lab20 = new Lab2_0(db)
  val lab23 = new Lab2_3(db)
  implicit val ec: ExecutionContext = ExecutionContext.global

  "Lab 2 Part 2" should "give +3 DRP for 1 task" in {
    lab23.task1.map(result =>
      assert(result == Set("Neal Wruck", "Scott LeBrun")))
  }

  it should "give +3 DRP for 2 task" in {
    lab23.task2.map { result =>
      //act_fname	act_lname	role
      assert(
        result == Set(
          ("Woody", "Allen", "Alvy Singer")
        ))
    }
  }

  it should "give +3 DRP for 3 task" in {
    lab23.task3.map { result =>
      //dir_fname	dir_lname
      assert(result.contains(("Stanley", "Kubrick")))
    }
  }

  it should "give +3 DRP for 4 task" in {
    lab23.task4.map { result =>
      //dir_fname	dir_lname	mov_title
      assert(result.contains(("Gus", "Van Sant", "Good Will Hunting")))
    }
  }

  it should "give +3 DRP for 5 task" in {
    lab23.task5.map { result =>
      //act_fname	act_lname	mov_title	mov_year
      assert(
        result == Set(
          ("James", "Stewart", "Vertigo", 1958),
          ("Deborah", "Kerr", "The Innocents", 1961),
          ("Peter", "OToole", "Lawrence of Arabia", 1962),
          ("Robert", "De Niro", "The Deer Hunter", 1978),
          ("F. Murray", "Abraham", "Amadeus", 1984),
          ("Harrison", "Ford", "Blade Runner", 1982),
          ("Woody", "Allen", "Annie Hall", 1977),
          ("Jon", "Voight", "Deliverance", 1972),
          ("Maggie", "Gyllenhaal", "Donnie Darko", 2001),
          ("Dev", "Patel", "Slumdog Millionaire", 2008),
          ("Sigourney", "Weaver", "Aliens", 1986),
          ("Kevin", "Spacey", "Beyond the Sea", 2004),
          ("Jack", "Nicholson", "Chinatown", 1974),
          ("Christian", "Bale", "The Prestige", 2006)
        )
      )
    }
  }

  it should "give +3 DRP for 6 task" in {
    lab23.task6.map { result =>
      //dir_fname	dir_lname	gen_title	count
      assert(
        result == Vector(
          ("Alfred", "Hitchcock", "Mystery", 1),
          ("Bryan", "Singer", "Crime", 1),
          ("Danny", "Boyle", "Drama", 2),
          ("David", "Lean", "Adventure", 1),
          ("Frank", "Darabont", "Crime", 1),
          ("Hayao", "Miyazaki", "Animation", 1),
          ("Jack", "Clayton", "Horror", 1),
          ("James", "Cameron", "Action", 1),
          ("John", "Boorman", "Adventure", 1),
          ("Kevin", "Spacey", "Music", 1),
          ("Michael", "Cimino", "War", 1),
          ("Ridley", "Scott", "Thriller", 1),
          ("Sam", "Mendes", "Romance", 1),
          ("Stanley", "Kubrick", "Mystery", 1),
          ("Woody", "Allen", "Comedy", 1)
        ))
    }
  }

  it should "give +3 DRP for 7 task" in {
    lab23.task7.map { result =>
      //mov_title mov_year gen_title
      assert(
        result == Set(
          ("Aliens", 1986, "Action"),
          ("Deliverance", 1972, "Adventure"),
          ("Lawrence of Arabia", 1962, "Adventure"),
          ("Princess Mononoke", 1997, "Animation"),
          ("Annie Hall", 1977, "Comedy"),
          ("The Usual Suspects", 1995, "Crime"),
          ("The Shawshank Redemption", 1994, "Crime"),
          ("Seven Samurai", 1954, "Drama"),
          ("Back to the Future", 1985, "Drama"),
          ("Trainspotting", 1996, "Drama"),
          ("Slumdog Millionaire", 2008, "Drama"),
          ("The Innocents", 1961, "Horror"),
          ("Beyond the Sea", 2004, "Music"),
          ("Eyes Wide Shut", 1999, "Mystery"),
          ("Spirited Away", 2001, "Mystery"),
          ("Vertigo", 1958, "Mystery"),
          ("American Beauty", 1999, "Romance"),
          ("Blade Runner", 1982, "Thriller"),
          ("The Deer Hunter", 1978, "War")
        ))
    }
  }

  it should "give +3 DRP for 8 task" in {
    lab23.task8.map { result =>
      //mov_title mov_year gen_title dir_fname dir_lname
      assert(
        result == Set(
          ("Vertigo", 1958, "Mystery", "Alfred", "Hitchcock"),
          ("The Innocents", 1961, "Horror", "Jack", "Clayton"),
          ("Lawrence of Arabia", 1962, "Adventure", "David", "Lean"),
          ("The Deer Hunter", 1978, "War", "Michael", "Cimino"),
          ("Blade Runner", 1982, "Thriller", "Ridley", "Scott"),
          ("Eyes Wide Shut", 1999, "Mystery", "Stanley", "Kubrick"),
          ("The Usual Suspects", 1995, "Crime", "Bryan", "Singer"),
          ("Annie Hall", 1977, "Comedy", "Woody", "Allen"),
          ("Princess Mononoke", 1997, "Animation", "Hayao", "Miyazaki"),
          ("The Shawshank Redemption", 1994, "Crime", "Frank", "Darabont"),
          ("American Beauty", 1999, "Romance", "Sam", "Mendes"),
          ("Deliverance", 1972, "Adventure", "John", "Boorman"),
          ("Trainspotting", 1996, "Drama", "Danny", "Boyle"),
          ("Slumdog Millionaire", 2008, "Drama", "Danny", "Boyle"),
          ("Aliens", 1986, "Action", "James", "Cameron"),
          ("Beyond the Sea", 2004, "Music", "Kevin", "Spacey")
        ))
    }
  }

  it should "give +3 DRP for 9 task" in {
    lab23.task9.map { result =>
      //mov_title mov_year mov_dt_rel mov_time dir_fname dir_lname
      assert(
        result == Vector(
          ("Aliens",
            1986,
            LocalDate.parse("1986-08-29"),
            137,
            "James",
            "Cameron"),
          ("Amadeus",
            1984,
            LocalDate.parse("1985-01-07"),
            160,
            "Milos",
            "Forman"),
          ("Deliverance",
            1972,
            LocalDate.parse("1982-10-05"),
            109,
            "John",
            "Boorman"),
          ("Blade Runner",
            1982,
            LocalDate.parse("1982-09-09"),
            117,
            "Ridley",
            "Scott"),
          ("The Deer Hunter",
            1978,
            LocalDate.parse("1979-03-08"),
            183,
            "Michael",
            "Cimino"),
          ("Annie Hall",
            1977,
            LocalDate.parse("1977-04-20"),
            93,
            "Woody",
            "Allen"),
          ("Chinatown",
            1974,
            LocalDate.parse("1974-08-09"),
            130,
            "Roman",
            "Polanski"),
          ("Lawrence of Arabia",
            1962,
            LocalDate.parse("1962-12-11"),
            216,
            "David",
            "Lean"),
          ("The Innocents",
            1961,
            LocalDate.parse("1962-02-19"),
            100,
            "Jack",
            "Clayton"),
          ("Vertigo",
            1958,
            LocalDate.parse("1958-08-24"),
            128,
            "Alfred",
            "Hitchcock")
        ))
    }
  }

  it should "give +3 DRP for 10 task" in {
    lab23.task10.map { result =>
      assert(
        result == Set(

          ("Adventure", 162.5, 2),
          ("Comedy", 93.0, 1),
          ("Drama", 134.25, 4),
          ("Horror", 100.0, 1),
          ("Thriller", 117.0, 1),
          ("Crime", 124.0, 2),
          ("Action", 137.0, 1),
          ("Music", 118.0, 1),
          ("War", 183.0, 1),
          ("Romance", 122.0, 1),
          ("Animation", 134.0, 1),
          ("Mystery", 137.3333333333333333, 3)
        ))
    }
  }

  it should "give +3 DRP for 11 task" in {
    lab23.task11.map { result =>
      //mov_title	mov_year	dir_fname	dir_lname	act_fname	act_lname	role
      assert(result.contains(
        ("Annie Hall", 1977, "Woody", "Allen", "Woody", "Allen", "Alvy Singer")
      ))
    }
  }

  it should "give +3 DRP for 12 task" in {
    lab23.task12.map { result =>
      assert(result == Vector(1997))
    }
  }

  it should "give +3 DRP for 13 task" in {
    lab23.task13.map { result =>
      //rev_name	mov_title	rev_stars
      assert(
        result == Vector(
          ("Brandt Sponseller", "Aliens", Option(8.40)),
          ("Flagrant Baronessa", "Lawrence of Arabia", Option(8.30)),
          ("Hannah Steele", "Donnie Darko", Option(8.10)),
          ("Jack Malvern", "The Innocents", Option(7.90)),
          ("Josh Cates", "Good Will Hunting", Option(4.00)),
          ("Krug Stillo", "Braveheart", Option(7.70)),
          ("Mike Salvati", "Annie Hall", Option(8.10)),
          ("Neal Wruck", "Chinatown", None),
          ("Paul Monks", "Boogie Nights", Option(3.00)),
          ("Richard Adams", "Beyond the Sea", Option(6.70)),
          ("Righty Sock", "Titanic", Option(7.70)),
          ("Righty Sock", "Vertigo", Option(8.40)),
          ("Sasha Goldshtein", "American Beauty", Option(7.00)),
          ("Scott LeBrun", "Trainspotting", None),
          ("Simon Wright", "The Usual Suspects", Option(8.60)),
          ("Victor Woeltjen", "Avatar", Option(7.30)),
          ("Vincent Cadena", "Slumdog Millionaire", Option(8.00))
        ))
    }
  }

  it should "give +3 DRP for 14 task" in {
    lab23.task14.map { result =>
      //rev_name	mov_title	rev_stars
      assert(
        result == Vector(
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

  it should "give +3 DRP for 15 task" in {
    lab23.task15.map { result =>
      //mov_title	dir_fname	dir_lname	rev_stars
      assert(
        result == Set(
          ("Vertigo", "Alfred", "Hitchcock", 8.40),
          ("The Innocents", "Jack", "Clayton", 7.90),
          ("Lawrence of Arabia", "David", "Lean", 8.30),
          ("Blade Runner", "Ridley", "Scott", 8.20),
          ("The Usual Suspects", "Bryan", "Singer", 8.60),
          ("Boogie Nights", "Paul", "Thomas Anderson", 3.00),
          ("Annie Hall", "Woody", "Allen", 8.10),
          ("Princess Mononoke", "Hayao", "Miyazaki", 8.40),
          ("American Beauty", "Sam", "Mendes", 7.00),
          ("Titanic", "James", "Cameron", 7.70),
          ("Good Will Hunting", "Gus", "Van Sant", 4.00),
          ("Donnie Darko", "Richard", "Kelly", 8.10),
          ("Slumdog Millionaire", "Danny", "Boyle", 8.00),
          ("Aliens", "James", "Cameron", 8.40),
          ("Beyond the Sea", "Kevin", "Spacey", 6.70)
        ))
    }
  }

  it should "give +3 DRP for 16 task" in {
    lab23.task16.map { result =>
      //mov_title	act_fname	act_lname	role
      assert(
        result == Set(
          ("American Beauty", "Kevin", "Spacey", "Lester Burnham"),
          ("Beyond the Sea", "Kevin", "Spacey", "Bobby Darin")
        ))
    }
  }

  it should "give +3 DRP for 17 task" in {
    lab23.task17.map { result =>
      //dir_fname	dir_lname	mov_title	act_fname	act_lname	role
      assert(
        result == Set(
          ("Hayao", "Miyazaki", "Princess Mononoke", "Claire", "Danes", "San")
        ))
    }
  }

  it should "give +3 DRP for 18 task" in {
    lab23.task18.map { result =>
      //act_fname	act_lname	mov_title	role
      assert(
        result == Set(
          ("Woody", "Allen", "Annie Hall", "Alvy Singer"),
          ("Kevin", "Spacey", "Beyond the Sea", "Bobby Darin")
        ))
    }
  }

  it should "give +3 DRP for 19 task" in {
    lab23.task19.map { result =>
      //act_fname	act_lname
      assert(
        result == Set(
          ("Jack", "Nicholson")
        ))
    }
  }

  it should "give +3 DRP for 20 task" in {
    lab23.task20.map { result =>
      assert(
        result == Set(
          "Blade Runner"
        ))
    }
  }

  it should "give +3 DRP for 21 task" in {
    lab23.task21.map { result =>
      //mov_title	mov_year	rev_stars	mov_rel_country
      assert(
        result.contains(
          ("The Usual Suspects", 1995, 8.60, "UK")
        ))
    }
  }

  it should "give +3 DRP for 22 task" in {
    lab23.task22.map { result =>
      //mov_title	mov_year	rev_stars
      assert(
        result.contains(
          ("Vertigo", 1958, 8.40)
        ))
    }
  }

  it should "give +3 DRP for 23 task" in {
    lab23.task23.map { result =>
      //mov_year	gen_title	count	avg
      assert(
        result.contains(
          (1958, "Mystery", 1, 8.40)
        ))
    }
  }

  it should "give +3 DRP for 24 task" in {
    lab23.task24.map { result =>
      //mov_title	act_fname	act_lname	mov_year	role	gen_title	dir_fname	dir_lname	mov_dt_rel	rev_stars
      assert(
        result == Set(
          ("The Innocents", "Deborah", "Kerr", 1961, "Miss Giddens", "Horror", "Jack", "Clayton", LocalDate.parse("1962-02-19"), 7.90),
          ("Princess Mononoke", "Claire", "Danes", 1997, "San", "Animation", "Hayao", "Miyazaki", LocalDate.parse("2001-10-19"), 8.40),
          ("Aliens", "Sigourney", "Weaver", 1986, "Ripley", "Action", "James", "Cameron", LocalDate.parse("1986-08-29"), 8.40)
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
