package models

import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Reads}

case class Owner(
                  login: String,
                  id: Int,
                  avatar_url: String,
                  gravatar_id: String,
                  url: String,
                  html_url: String,
                  followers_url: String,
                  following_url: String,
                  gists_url: String,
                  starred_url: String,
                  subscriptions_url: String,
                  organizations_url: String,
                  repos_url: String,
                  events_url: String,
                  received_events_url: String,
                  `type`:String,
                  site_admin:Boolean
                )

object Owner{

  implicit val ownerReads: Reads[Owner] = (
    (JsPath \ "login").read[String] and
    (JsPath \ "id").read[Int] and
    (JsPath \ "avatar_url").read[String] and
    (JsPath \ "gravatar_id").read[String] and
    (JsPath \ "url").read[String] and
    (JsPath \ "html_url").read[String] and
    (JsPath \ "followers_url").read[String] and
    (JsPath \ "following_url").read[String] and
    (JsPath \ "gists_url").read[String] and
    (JsPath \ "starred_url").read[String] and
    (JsPath \ "subscriptions_url").read[String] and
    (JsPath \ "organizations_url").read[String] and
    (JsPath \ "repos_url").read[String] and
    (JsPath \ "events_url").read[String] and
    (JsPath \ "received_events_url").read[String] and
    (JsPath \ "type").read[String] and
    (JsPath \ "site_admin").read[Boolean]
  )(Owner.apply _)
}

case class Repo(
                 id: Int,
                 name:String,
                 full_name:String,
                 owner: Owner,
                 `private` : Boolean,
                 html_url :String,
                 description: Option[String],
                 fork: Boolean,
                 url: String,
                 forks_url: String,
                 keys_url: String,
                 collaborators_url:String,
                 teams_url:String,
                 hooks_url:String,
                 issue_events_url:String,
                 events_url:String,
                 assignees_url:String,
                 branches_url:String,
                 tags_url:String,
                 blobs_url:String,
//                 git_tags_url:String,
//                 git_refs_url:String,
//                 trees_url:String,
//                 statuses_url:String,
//                 languages_url:String,
//                 stargazers_url:String,
//                 contributors_url:String,
//                 subscribers_url:String,
//                 subscription_url:String,
//                 commits_url:String,
//                 git_commits_url:String,
//                 comments_url:String,
//                 issue_comment_url:String,
//                 contents_url:String,
//                 compare_url:String,
//                 merges_url:String,
//                 archive_url:String,
//                 downloads_url:String,
//                 issues_url:String,
//                 pulls_url:String,
//                 milestones_url:String,
//                 notifications_url:String,
//                 labels_url:String,
//                 releases_url:String,
//                 deployments_url:String,
//                 created_at:String,
//                 updated_at:String,
//                 pushed_at:String,
//                 git_url:String,
//                 ssh_url:String,
//                 clone_url:String,
//                 svn_url:String,
//                 homepage:String,
//                 size:Int,
//                 stargazers_count:Int,
//                 watchers_count:Int,
//                 language:String,
//                 has_issues: Boolean,
//                 has_downloads:Boolean,
//                 has_wiki:Boolean,
//                 has_pages:Boolean,
//                 forks_count:Int,
//                 mirror_url:String,
//                 open_issues_count:Int,
//                 forks:Int,
//                 open_issues:Int,
//                 watchers:Int,
//                 default_branch:String,
                 permissions: Permission
               )

object Repo {

  implicit val reposReads : Reads[Repo] = (
    (JsPath \ "id").read[Int] and
    (JsPath \ "name").read[String] and
    (JsPath \ "full_name").read[String] and
    (JsPath \ "owner").read[ Owner] and
    (JsPath \ "private").read[ Boolean] and
    (JsPath \ "html_url").read[String] and
    (JsPath \ "description").read[Option[String]] and
    (JsPath \ "fork").read[ Boolean] and
    (JsPath \ "url").read[ String] and
    (JsPath \ "forks_url").read[ String] and
    (JsPath \ "keys_url").read[ String] and
    (JsPath \ "collaborators_url").read[String] and
    (JsPath \ "teams_url").read[String] and
    (JsPath \ "hooks_url").read[String] and
    (JsPath \ "issue_events_url").read[String] and
    (JsPath \ "events_url").read[String] and
    (JsPath \ "assignees_url").read[String] and
    (JsPath \ "branches_url").read[String] and
    (JsPath \ "tags_url").read[String] and
    (JsPath \ "blobs_url").read[String] and
//    (JsPath \ "git_tags_url").read[String] and
//    (JsPath \ "git_refs_url").read[String] and
//    (JsPath \ "trees_url").read[String] and
//    (JsPath \ "statuses_url").read[String] and
//    (JsPath \ "languages_url").read[String] and
//    (JsPath \ "stargazers_url").read[String] and
//    (JsPath \ "contributors_url").read[String] and
//    (JsPath \ "subscribers_url").read[String] and
//    (JsPath \ "subscription_url").read[String] and
//    (JsPath \ "commits_url").read[String] and
//    (JsPath \ "git_commits_url").read[String] and
//    (JsPath \ "comments_url").read[String] and
//    (JsPath \ "issue_comment_url").read[String] and
//    (JsPath \ "contents_url").read[String] and
//    (JsPath \ "compare_url").read[String] and
//    (JsPath \ "merges_url").read[String] and
//    (JsPath \ "archive_url").read[String] and
//    (JsPath \ "downloads_url").read[String] and
//    (JsPath \ "issues_url").read[String] and
//    (JsPath \ "pulls_url").read[String] and
//    (JsPath \ "milestones_url").read[String] and
//    (JsPath \ "notifications_url").read[String] and
//    (JsPath \ "labels_url").read[String] and
//    (JsPath \ "releases_url").read[String] and
//    (JsPath \ "deployments_url").read[String] and
//    (JsPath \ "created_at").read[String] and
//    (JsPath \ "updated_at").read[String] and
//    (JsPath \ "pushed_at").read[String] and
//    (JsPath \ "git_url").read[String] and
//    (JsPath \ "ssh_url").read[String] and
//    (JsPath \ "clone_url").read[String] and
//    (JsPath \ "svn_url").read[String] and
//    (JsPath \ "homepage").read[String] and
//    (JsPath \ "size").read[Int] and
//    (JsPath \ "stargazers_count").read[Int] and
//    (JsPath \ "watchers_count").read[Int] and
//    (JsPath \ "language").read[String] and
//    (JsPath \ "has_issues").read[ Boolean] and
//    (JsPath \ "has_downloads").read[Boolean] and
//    (JsPath \ "has_wiki").read[Boolean] and
//    (JsPath \ "has_pages").read[Boolean] and
//    (JsPath \ "forks_count").read[Int] and
//    (JsPath \ "mirror_url").read[String] and
//    (JsPath \ "open_issues_count").read[Int] and
//    (JsPath \ "forks").read[Int] and
//    (JsPath \ "open_issues").read[Int] and
//    (JsPath \ "watchers").read[Int] and
//    (JsPath \ "default_branch").read[String] and
    (JsPath \ "permissions").read[Permission]
    )(Repo.apply _)
}

case class Permission(admin: Boolean, push: Boolean, pull: Boolean)

object Permission {

  implicit val premissionReads : Reads[Permission] = (
    (JsPath \ "admin").read[Boolean] and
    (JsPath \ "push").read[Boolean] and
    (JsPath \ "pull").read[Boolean]
    )(Permission.apply _)
}
