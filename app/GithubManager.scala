import com.typesafe.config.Config

class GithubManager(setting: Config, githubAuthId: String) {
  val baseUrl = setting.getString("github.redirect.url").format(githubAuthId, redirectUri, scope, state)


}
