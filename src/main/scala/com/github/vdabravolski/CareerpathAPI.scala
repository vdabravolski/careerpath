package com.github.vdabravolski

import org.scalatra._
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json._
import com.mongodb.casbah.Imports._
import scala.collection.mutable.ListBuffer


// TODO:
//1.   extend number of data fiels of account entity. Right now only status and name is collected.
//2. Generate page with API documentation using Swagger.


// this is routes class
class AccountAPIServlet(mongoColl : MongoCollection) extends ScalatraServlet with NativeJsonSupport {

  // Sets up automatic case class to JSON output serialization
  protected implicit val jsonFormats: Formats = DefaultFormats

  // Before every action runs, set the content type to be in JSON format.
  before() {
    contentType = formats("json")
  }

  get("/") {
    //for { x <- mongoColl} yield Account(x.get("name"),x.get("status"))
    val results=mongoColl.find()
    val allAccounts= AccountsColl
    for (x <- results){
      allAccounts.all += convertDBObjectToAccount(x)
    }
    allAccounts.all
  }

  get("/:name") {
    val q = MongoDBObject("name" -> params("name"))

    mongoColl.findOne(q) match {
      case Some(x) => Account(mongoColl.findOne(q).get("name"),mongoColl.findOne(q).get("status"))
      case None => SuccessMessage("account",params("name"),"not found")
    }
  }

  post("/addAccount"){
    var name=params("name")
    var status=params("status")
    var newObj=MongoDBObject("name"->name, "status" -> status)
    mongoColl+=newObj
    SuccessMessage("account",name, "saved")
  }

  delete("/deleteAccount/:name"){
    val q = MongoDBObject("name" -> params("name"))
    mongoColl.findAndRemove(q) match {
      case Some(x) => SuccessMessage("project", params("name"), "was deleted")
      case None => SuccessMessage("project",params("name"),"not found")
    }
  }

  //below is a routing to handle errors.
  error {
  case e: Throwable => {
    ErrorMessage("Error happened during handling of account entity", e.toString())
  }
}

  def convertDBObjectToAccount(obj: MongoDBObject): Account ={
    val name = obj.getAs[String]("name").get
    val status = obj.getAs[String]("status").get
    Account(name, status)
  }
}

class ProjectAPIServlet(mongoColl : MongoCollection) extends ScalatraServlet with NativeJsonSupport {

  // Sets up automatic case class to JSON output serialization
  protected implicit val jsonFormats: Formats = DefaultFormats

  // Before every action runs, set the content type to be in JSON format.
  before() {
    contentType = formats("json")
  }

  get("/"){
    val results=mongoColl.find()
    val allProjects= ProjectsColl
    for (x <- results){
      allProjects.all += convertDBObjectToProject(x)
    }
    allProjects.all
  }

  get("/:name"){
    val q = MongoDBObject("name" -> params("name"))

    mongoColl.findOne(q) match {
      case Some(x) => convertDBObjectToProject(x)
      case None => SuccessMessage("project",params("name"),"not found")
    }
  }

  post("/addProject"){
    var name=params("name")
    var status=params("status")
    var clients=params("clients")
    var team=params("team")
    var newObj=MongoDBObject("name"->name, "status" -> status, "clients" -> clients, "team" -> team)
    mongoColl+=newObj
    SuccessMessage("project",name, "saved")
  }

  delete("/deleteProject/:name"){
    val q = MongoDBObject("name" -> params("name"))
    mongoColl.findAndRemove(q) match {
      case Some(x) => SuccessMessage("project", params("name"), "was deleted")
      case None => SuccessMessage("project",params("name"),"not found")
    }
  }

  //below is a routing to handle errors.
  error {
  case e: Throwable => {
    ErrorMessage("Error happened during handling of project entity", e.toString())
  }
  }

  def convertDBObjectToProject(obj: MongoDBObject): Project ={
    val name = obj.getAs[String]("name").get
    val status = obj.getAs[String]("status").get
    val clients = obj.getAs[String]("clients").get
    val team = obj.getAs[String]("team").get
    Project(name, status,clients, team)
  }

}


case class Account(name: Any, status:Any)
object AccountsColl {var all = ListBuffer[Account]()}
case class Project(name: Any, status:Any, clients:Any, team: Any)
object ProjectsColl {var all = ListBuffer[Project]()}
case class SuccessMessage(entityType: String, entityID: String, entityStatus: String)
case class ErrorMessage(message: String, details: String = "")
