package controllers

import play.api.mvc.{Action, Controller}

object Repositories extends Controller {
  def list = Action{ implicit request =>
    val repositories = models.Repository.findAll
    Ok(views.html.repositories.list(repositories))
  }
}
