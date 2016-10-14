import com.typesafe.config.Config
import controllers.TEAHubController
import play.api.{Application, ApplicationLoader, BuiltInComponentsFromContext, LoggerConfigurator}
import play.api.ApplicationLoader.Context
import play.api.libs.ws.ahc.AhcWSClient
import router.Routes
import services.impl.{ApiGitHubService, ApiTogglService}

import scala.concurrent.ExecutionContext

/**
  * Instantiates all parts of the application and wires everything together.
  */
class AppLoader extends ApplicationLoader {
  /** Loads the application given the context
    *
    * @param context is the context for loading an application. It includes Environment, initial configuration,
    *                web command handler, and optional source mapper
    * */
  override def load(context: Context): Application = {
    implicit val ec: ExecutionContext = play.api.libs.concurrent.Execution.defaultContext

    LoggerConfigurator(context.environment.classLoader).foreach {
      _.configure(context.environment)
    }
    new AppComponent(context).application
  }
}

/** Provides all the built in components dependencies from the application loader context
  *
  * @param context is the context for loading an application. It includes Environment, initial configuration,
  *                web command handler, and optional source mapper */
class AppComponent(context: Context)(implicit val ec: ExecutionContext) extends BuiltInComponentsFromContext(context) {
  val githubAuthId = context.initialConfiguration.underlying.getString("github.client.id")
  val githubAuthSecret = context.initialConfiguration.underlying.getString("github.client.secret")
  lazy val togglService = new ApiTogglService(AhcWSClient())
  lazy val gitHubService = new ApiGitHubService(AhcWSClient(), githubAuthId, githubAuthSecret)
  lazy val teahubController = new TEAHubController(AhcWSClient(), togglService, gitHubService)
  lazy val assetsController = new controllers.Assets(httpErrorHandler)
  //  implicit lazy val githubAuthId = application.configuration.getString("github.client.id").get
  //  implicit lazy val githubAuthSecret = application.configuration.getString("github.client.secret").get

  lazy val router = new Routes(httpErrorHandler,
    teahubController,
    assetsController
  )
}
