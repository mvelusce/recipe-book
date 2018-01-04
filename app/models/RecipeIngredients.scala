package models

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.lifted

case class RecipeIngredients(id: Long, recipe: Long, ingredient: Long, quantity: String)

class RecipeIngredientsRepo @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) {

  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db
  import dbConfig.profile.api._

  private[models] val recipeIngredients = lifted.TableQuery[RecipeIngredientsTable]

  private[models] class RecipeIngredientsTable(tag: Tag) extends Table[RecipeIngredients](tag, "recipe_ingredients") {

    def id = column[Long]("id", O.AutoInc, O.PrimaryKey)
    def recipe = column[Long]("recipe")
    def ingredient = column[Long]("ingredient")
    def quantity = column[String]("quantity")

    override def * = (id, recipe, ingredient, quantity) <> (RecipeIngredients.tupled, RecipeIngredients.unapply)
    def ? = (id.?, recipe.?, ingredient.?, quantity.?).shaped.<>(
      {
        r =>
          import r._
          _1.map(_ => RecipeIngredients.tupled((_1.get, _2.get, _3.get, _4.get)))
      },
      (_: Any) => throw new Exception("Inserting into ? projection not supported.")
    )
  }
}
