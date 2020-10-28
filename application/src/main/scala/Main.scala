import slick.jdbc.PostgresProfile.api._


object Main {
  def main(args: Array[String]): Unit = {
    val db = Database.forConfig("postgresql")
    Thread.sleep(10000)
    db.close()
  }
}
