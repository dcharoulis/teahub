package controllers

import java.util.UUID

import play.api.Play
import play.api.libs.json.Json
import play.api.mvc.Action
import play.api.mvc._
import services._

import scala.concurrent.{ExecutionContext, Future}
import util.OAuth2

/** Is a controller for the project
  *
  * @param togglService Service that requests Toggl the list of the projects */
class TEAHubController(togglService: TogglService)(implicit executionContext: ExecutionContext) extends Controller {
  /** Request List of Toggl projects' name from Toggl API and show it in TEAHub UI
    */
  //TODO: So far this method just takes the projects name in future it should show the result in the related page in TEAHUB
  def togglProjects = Action.async { implicit request =>
    val toggleToken: Option[String] = request.getQueryString("apiToken")
    val result = toggleToken match {
      case Some(token) => togglService.getTogglProjects(token)
      case None => Future.successful(List.empty)
    }
    result.map {
      theResult =>
        Ok(Json.obj("Projects" -> theResult))
    }
  }

  def githubRepositories = Action.async{ implicit request =>
    val code = request.getQueryString("code")
    GitHubService.
//    val callbackUrl = util.routes.OAuth2.callback(None, None).absoluteURL()
//    val scope = "repo"   // github scope - request repo access
//  val state = UUID.randomUUID().toString  // random confirmation string
//  val redirectUrl = oauth2.getAuthorizationUrl(callbackUrl, scope, state)
//    Ok(views.html.index("Your new application is ready.", redirectUrl)).
//      withSession("oauth-state" -> state)

  }
}
