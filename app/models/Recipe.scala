package models

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.lifted

import scala.concurrent.Future

case class Recipe(id: Long, name: String, description: String, instructions: String, notes: String)

class RecipeRepo @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) {

  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db
  import dbConfig.profile.api._

  private[models] val recipes = lifted.TableQuery[RecipesTable]

  private def _findById(id: Long): DBIO[Option[Recipe]] =
    recipes.filter(_.id === id).result.headOption

  private def _findByName(name: String): Query[RecipesTable, Recipe, List] =
    recipes.filter(_.name === name).to[List]

  def findById(id: Long): Future[Option[Recipe]] =
    db.run(_findById(id))

  def findByName(name: String): Future[List[Recipe]] =
    db.run(_findByName(name).result)

  def all: Future[List[Recipe]] =
    db.run(recipes.to[List].result)

  def create(name: String, description: String, instructions: String, notes: String): Future[Long] = {
    val recipe = Recipe(0, name, description, instructions, notes)
    db.run(recipes.returning(recipes.map(_.id)) += recipe)
  }

  // TODO delete

  // TODO modify

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
          import r._
          _1.map(_ => Recipe.tupled((_1.get, _2.get, _3.get, _4.get, _5.get)))
      },
      (_: Any) => throw new Exception("Inserting into ? projection not supported.")
    )
  }
}
