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
    var results=mongoColl.find()
    var allAccounts= AccountsColl
    allAccounts.all=ListBuffer()
    for (x <- results){
      allAccounts.all += convertDBObjectToAccount(x)
    }
    allAccounts.all
  }

  get("/:id") {
    var q = MongoDBObject("name" -> params("id"))
    var id = params("id")

    mongoColl.findOne(q) match {
      case Some(x) => Account(mongoColl.findOne(q).get("name"),mongoColl.findOne(q).get("status"))
      case None => SuccessMessage("account",id,"not found")
    }
  }

  post("/"){
//    var id=params("id")
    println("############")
    println("printin request body"+request.body)
    println(params.get("status"))
    var id="js test"
    var status=params("status")
    var newObj=MongoDBObject("name"->id, "status" -> status)
    mongoColl+=newObj
    SuccessMessage("account",id, "saved")
  }
  
  delete("/:id"){
    var q = MongoDBObject("name" -> params("id"))
    var id = params("id")

    mongoColl.findAndRemove(q) match {
      case Some(x) => SuccessMessage("project", id, "was deleted")
      case None => SuccessMessage("project",id,"not found")
    }
  }

  //below is a routing to handle errors.
  error {
  case e: Throwable => {
    halt(500,ErrorMessage("Error happened during handling of account entity", e.toString()))
  }
}

  def convertDBObjectToAccount(obj: MongoDBObject): Account ={
    var id = obj.getAs[String]("name").get
    var status = obj.getAs[String]("status").get
    Account(id, status)
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
    var results=mongoColl.find()
    var allProjects= ProjectsColl
    allProjects.all=ListBuffer()
    for (x <- results){
      allProjects.all += convertDBObjectToProject(x)
    }
    allProjects.all
  }

  get("/:id"){
    var id= params("id")
    val q = MongoDBObject("name" -> id)

    mongoColl.findOne(q) match {
      case Some(x) => convertDBObjectToProject(x)
      case None => SuccessMessage("project",id,"not found")
    }
  }

  post("/"){
    var id=params("id")
    var status=params("status")
    var clients=params("clients")
    var team=params("team")
    var newObj=MongoDBObject("name"->id, "status" -> status, "clients" -> clients, "team" -> team)
    mongoColl+=newObj
    SuccessMessage("project",id, "saved")
  }

  delete("/:id"){
    var id=params("id")
    val q = MongoDBObject("name" -> id)
    mongoColl.findAndRemove(q) match {
      case Some(x) => SuccessMessage("project", id, "was deleted")
      case None => SuccessMessage("project",id,"not found")
    }
  }

  //below is a routing to handle errors.
  error {
  case e: Throwable => {
    halt(500,ErrorMessage("Error happened during handling of project entity", e.toString()))
  }
  }

  def convertDBObjectToProject(obj: MongoDBObject): Project ={
    var id = obj.getAs[String]("name").get
    var status = obj.getAs[String]("status").get
    var clients = obj.getAs[String]("clients").get
    var team = obj.getAs[String]("team").get
    Project(id, status,clients, team)
  }

}


case class Account(id: Any, status:Any)
object AccountsColl {var all = ListBuffer[Account]()}
case class Project(id: Any, status:Any, clients:Any, team: Any)
object ProjectsColl {var all = ListBuffer[Project]()}
case class SuccessMessage(entityType: String, entityID: String, entityStatus: String)
case class ErrorMessage(message: String, details: String = "")
