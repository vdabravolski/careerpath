package com.github.vdabravolski

import org.scalatra._
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json._
import com.mongodb.casbah.Imports._
import scala.collection.mutable.ListBuffer



// TODO:
//1. Extend number of data fiels of account entity. Right now only status and name is collected.
//2. Generate page with API documentation using Swagger.
//3. Update Project API similar to account


// this is routes class
class AccountAPIServlet(mongoColl : MongoCollection) extends ScalatraServlet with NativeJsonSupport {

  // Sets up automatic case class to JSON output serialization
  protected implicit val jsonFormats: Formats = DefaultFormats

  // Before every action runs, set the content type to be in JSON format.
  before() {
    contentType = formats("json")
  }

  get("/") {
    var results=mongoColl.find()
    var allAccounts= AccountsColl //collection of Account instances
    allAccounts.all=ListBuffer()
    for (x <- results){
      allAccounts.all += convertDBObjectToAccount(x)
    }
    allAccounts.all
  }

  get("/:id") {
    var objectId = new ObjectId(params("id"))
    var q = MongoDBObject("_id" -> objectId)

    mongoColl.findOne(q) match {
      case Some(result) => convertDBObjectToAccount(result)
      case None => SuccessMessage("account",params("id"),"not found")
    }
  }

  post("/"){
    var newAccount=parsedBody.extract[Account]
    var newObj=MongoDBObject("name"->newAccount.name, "status" -> newAccount.status)
    mongoColl+=newObj
    SuccessMessage("account",newObj.getAs[ObjectId]("_id").get.toString, "created")
  }

  delete("/:id"){
    var objectId = new ObjectId(params("id"))
    var q = MongoDBObject("_id" -> objectId)

    mongoColl.findAndRemove(q) match {
      case Some(result) => SuccessMessage("project", params("id"), "was deleted")
      case None => SuccessMessage("project",params("id"),"not found")
    }
  }

  put("/:id"){
    var newAccount=parsedBody.extract[Account]
    var objectId = new ObjectId(params("id"))
    var q = MongoDBObject("_id" -> objectId)
    var newAccountDB=MongoDBObject("name"->newAccount.name, "status" -> newAccount.status)

    mongoColl.findAndModify(q, newAccountDB)
    SuccessMessage("account",params("id"), "updated")
  }

  //below is a routing to handle errors.
  error {
  case e: Throwable => {
    halt(500,ErrorMessage("Error happened during handling of account entity", e.toString()))
  }
}

  def convertDBObjectToAccount(obj: MongoDBObject): Account ={
    var id = obj.getAs[ObjectId]("_id").get.toString()
    var name = obj.getAs[String]("name").get
    var status = obj.getAs[String]("status").get
    Account(id, name, status)
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
    var objectId = new ObjectId(params("id"))
    var q = MongoDBObject("_id" -> objectId)

    mongoColl.findOne(q) match {
      case Some(result) => convertDBObjectToProject(result)
      case None => SuccessMessage("project",params("id"),"not found")
    }
  }

  post("/"){
    var newProject=parsedBody.extract[Project]

    var newObj=MongoDBObject("name"->newProject.name, "status" -> newProject.status,
      "team" -> newProject.team, "clients" -> newProject.clients)
    mongoColl+=newObj
    SuccessMessage("project",newObj.getAs[ObjectId]("_id").get.toString, "created")
  }

  delete("/:id"){
    var objectId = new ObjectId(params("id"))
    var q = MongoDBObject("_id" -> objectId)

    mongoColl.findAndRemove(q) match {
      case Some(result) => SuccessMessage("project", params("id"), "was deleted")
      case None => SuccessMessage("project",params("id"),"not found")
    }
  }

  put("/:id"){
    var newProject=parsedBody.extract[Project]
    var objectId = new ObjectId(params("id"))
    var q = MongoDBObject("_id" -> objectId)
    var newProjectDB=MongoDBObject("name"->newProject.name, "status" -> newProject.status
      ,"team" -> newProject.team, "clients" -> newProject.clients)

    mongoColl.findAndModify(q, newProjectDB)
    SuccessMessage("project",params("id"), "updated")
  }

  // //below is a routing to handle errors.
  error {
  case e: Throwable => {
    halt(500,ErrorMessage("Error happened during handling of project entity", e.toString()))
  }
  }

  def convertDBObjectToProject(obj: MongoDBObject): Project ={
    var id = obj.getAs[ObjectId]("_id").get.toString()
    var name = obj.getAs[String]("name").get
    var status = obj.getAs[String]("status").get
    var clients = obj.getAs[String]("clients").get
    var team = obj.getAs[String]("team").get
    Project(id, name, status, clients, team)
  }

}


case class Account(_id: Any ="", name: String, status: String)
object AccountsColl {var all = ListBuffer[Account]()}
case class Project(_id: Any="", name: String, status:String, clients:String, team:String)
object ProjectsColl {var all = ListBuffer[Project]()}
case class SuccessMessage(entityType: String, entityID: String, entityStatus: String)
case class ErrorMessage(message: String, details: String = "")
