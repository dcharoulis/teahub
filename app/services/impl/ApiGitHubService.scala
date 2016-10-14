package services.impl

import play.api.Play
import play.api.http.{HeaderNames, MimeTypes}
import play.api.libs.ws.{WS, WSClient}
import play.api.mvc.Results
import services.GitHubService

import scala.concurrent.{ExecutionContext, Future}

class ApiGitHubService(ws: WSClient) (implicit val ec: ExecutionContext) extends GitHubService {


  /** Returns a Future List of projects that a user can access in github */
  override def getGitHubProjects(code: String): Future[List[String]] = {
    val request = getToken(code)

    ???
  }

  def getToken(code: String)(implicit githubAuthId: String, githubAuthSecret: String): Future[String] = {

    val tokenResponse = WS.url("https://github.com/login/oauth/access_token")(Play.current).
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
