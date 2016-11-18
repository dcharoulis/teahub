package controllers

import java.util.UUID

import play.api.libs.json.Json
import play.api.mvc.Action
import play.api.mvc._
import services.{GitHubService, TogglService}
import services.impl.ApiGitHubService

import scala.concurrent.{ExecutionContext, Future}

/** Is a controller for the project
  *
  * @param togglService Service that requests Toggl the list of the projects */
class TEAHubController(togglService: TogglService, apiGitHubService: ApiGitHubService)(implicit executionContext: ExecutionContext) extends Controller {
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
    val absoluteCallback = request.getQueryString("_oauth-callback")
    val callbackUrl = routes.TEAHubController.callback(None, None).absoluteURL()
    val scope = "repo"   // github scope - request repo access
    val state = UUID.randomUUID().toString  // random confirmation string
    val something = absoluteCallback.getOrElse("_oauth-callback")
    val redirectUrl = apiGitHubService.getAuthorizationUrl(callbackUrl, scope, state)
    Future.successful(Ok(views.html.index("Your new application is ready.", redirectUrl)).
      withSession("oauth-state" -> state))

  }

  def callback(codeOpt: Option[String] = None, stateOpt: Option[String] = None) = Action.async { implicit request =>
    (for {
      code <- codeOpt
      state <- stateOpt
      oauthState <- request.session.get("oauth-state")
    } yield {
      if (state == oauthState) {
        apiGitHubService.getToken(code).map { accessToken =>
          Redirect(controllers.routes.TEAHubController.success()).withSession("oauth-token" -> accessToken)
        }.recover {
          case ex: IllegalStateException => Unauthorized(ex.getMessage)
        }
      }
      else {
        Future.successful(BadRequest("Invalid github login"))
      }
    }).getOrElse(Future.successful(BadRequest("No parameters supplied")))
  }

  def success() = Action.async { request =>
    val oauthToken = request.session.get("oauth-token")
    val result: Future[List[String]] = apiGitHubService.getGitHubProjects(oauthToken)

    result.map {res => Ok(Json.obj("repositories" -> res))}
  }
}
