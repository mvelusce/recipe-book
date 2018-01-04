package models

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.lifted

case class Ingredient(id: Long, name: String, description: String)

class IngredientRepo @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) {

  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db
  import dbConfig.profile.api._

  private[models] val ingredients = lifted.TableQuery[IngredientsTable]

  private[models] class IngredientsTable(tag: Tag) extends Table[Ingredient](tag, "ingredients") {

    def id = column[Long]("id", O.AutoInc, O.PrimaryKey)
    def name = column[String]("name")
    def description = column[String]("description")

    override def * = (id, name, description) <> (Ingredient.tupled, Ingredient.unapply)
    def ? = (id.?, name.?, description.?).shaped.<>(
      {
        r =>
          import r._
          _1.map(_ => Ingredient.tupled((_1.get, _2.get, _3.get)))
      },
      (_: Any) => throw new Exception("Inserting into ? projection not supported.")
    )
  }
}
