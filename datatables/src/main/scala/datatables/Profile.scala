package datatables

import java.time.LocalDate

import slick.ast.BaseTypedType
import slick.jdbc.{JdbcProfile, JdbcType, PostgresProfile}

trait Profile extends PostgresProfile {
  override val api = new PostgresAPI

  class PostgresAPI extends super.API {
    implicit def LocalDateToDateConverter: JdbcType[LocalDate] with BaseTypedType[LocalDate] =
      MappedColumnType.base[LocalDate, java.sql.Date](java.sql.Date.valueOf, _.toLocalDate)
  }
}

object Profile extends Profile
