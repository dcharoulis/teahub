package services.impl

import play.api.http.{HeaderNames, MimeTypes}
import play.api.libs.ws.WSClient
import play.api.mvc.{Action, Results}
import services.GitHubService

import scala.concurrent.{ExecutionContext, Future}

class ApiGitHubService(ws: WSClient, githubAuthId: String, githubAuthSecret: String) (implicit val ec: ExecutionContext) extends GitHubService {


  /** Returns a Future List of projects that a user can access in github */
  override def getGitHubProjects(code: String): Future[List[String]] = {
    val request = getToken(code)

    ???
  }

  def getToken(code: String): Future[String] = {

    val tokenResponse = ws.url("https://github.com/login/oauth/access_token")
        .withQueryString("client_id" -> githubAuthId,
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

  def success(authToken: String): Future[List[(Int, String, Option[String])]] ={
    val request =  ws.url("https://api.github.com/user/repos").
            withHeaders(HeaderNames.AUTHORIZATION -> s"token $authToken")
    val resp = request.get()
    resp.map { response =>
      response.json.as[List[GitHubService.Repo]].map(x => (x.id, x.name, x.description))
    }
  }

}
