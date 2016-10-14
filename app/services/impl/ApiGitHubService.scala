package services.impl

import play.api.Play
import play.api.http.{HeaderNames, MimeTypes}
import play.api.libs.ws.{WS, WSClient}
import play.api.mvc.Results
import services.GitHubService

import scala.concurrent.{ExecutionContext, Future}

class ApiGitHubService(ws: WSClient, githubAuthId: String, githubAuthSecret: String) (implicit val ec: ExecutionContext) extends GitHubService {


  /** Returns a Future List of projects that a user can access in github */
  override def getGitHubProjects(code: String): Future[List[String]] = {
    val request = getToken(code)

    ???
  }

  //implicit val githubAuthId: String = ""

  def getAuthorizationUrl(redirectUri: String, scope: String, state: String): String = {
    val baseUrl = Play.current.configuration.getString("github.redirect.url").get
    baseUrl.format(githubAuthId, redirectUri, scope, state)
  }

  def getToken(code: String): Future[String] = {

    val tokenResponse = ws.url("https://github.com/login/oauth/access_token").
      withQueryString("client_id" -> githubAuthId,
        "client_secret" -> githubAuthSecret,
        "code" -> code).
      withHeaders(HeaderNames.ACCEPT -> MimeTypes.JSON).
      post(Results.EmptyContent())

    tokenResponse.flatMap { response =>
      (response.json \ "access_token").asOpt[String].fold(Future.failed[String](new IllegalStateException("Sod off!"))) { accessToken =>
        Future.successful(accessToken)
      }
    }
  }


}
