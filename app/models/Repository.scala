package models

case class Repository(name: String, description: String)

object Repository{
  var repositories = Set(
    Repository("AnonymousMicrosoftWebData","Anonymous Microsoft Web Data"),
    Repository("CuisinePredictions", "Cuisine Predictions"),
    Repository("GitHubArchives", "GitHub Archives")
  )

  def findAll: List[Repository] = repositories.toList.sortBy(_.name)

  def findRepository(name: String) = repositories.find(name == _.name)
}



