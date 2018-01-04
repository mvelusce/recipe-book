package models

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider
import slick.{dbio, lifted}
import slick.dbio.Effect.Read
import slick.jdbc.JdbcProfile
import slick.lifted.TableQuery

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class Recipe(id: Long, name: String, description: String, instructions: String, notes: String)

class RecipeRepo @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) {

  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db
  import dbConfig.profile.api._

  private[models] val recipes = lifted.TableQuery[RecipesTable]

  private[models] class RecipesTable(tag: Tag) extends Table[Recipe](tag, "recipes") {

    def id = column[Long]("id", O.AutoInc, O.PrimaryKey)
    def name = column[String]("name")
    def description = column[String]("description")
    def instructions = column[String]("instructions")
    def notes = column[String]("notes")

    override def * = (id, name, description, instructions, notes) <> (Recipe.tupled, Recipe.unapply)
    def ? = (id.?, name.?, description.?, instructions.?, notes.?).shaped.<>(
      {
        r =>
          import r._;
          _1.map(_ => Recipe.tupled((_1.get, _2.get, _3.get, _4.get, _5.get)))
      },
      (_: Any) => throw new Exception("Inserting into ? projection not supported.")
    )
  }
}
