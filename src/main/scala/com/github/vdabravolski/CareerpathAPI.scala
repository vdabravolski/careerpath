package com.github.vdabravolski

import org.scalatra._
// JSON-related libraries
import org.json4s.{DefaultFormats, Formats}
// JSON handling support from Scalatra
import org.scalatra.json._
import com.mongodb.casbah.Imports._


// this is routes class

class CareerAPIServlet(mongoColl : MongoCollection) extends ScalatraServlet with NativeJsonSupport {

  // Sets up automatic case class to JSON output serialization
  protected implicit val jsonFormats: Formats = DefaultFormats

  // Before every action runs, set the content type to be in JSON format.
  before() {
    contentType = formats("json")
  }

  get("/accounts") {
    val q = MongoDBObject("name" -> params("name"))

//    mongoColl.findOne(q).get("status") // how to retrieve form the Mongo query

    mongoColl.findOne(q) match {
      case Some(x) => Account(mongoColl.findOne(q).get("name"),mongoColl.findOne(q).get("status"))
      case None => APIMessage("account",params("name"),"not found")
    }
  }

  get("/projects"){
    //todo is coming
    "functionality is coming"
  }

  post("/addAccount"){
    var name=params("name")
    var status=params("status")
    var newObj=MongoDBObject("name"->name, "status" -> status)
    mongoColl+=newObj
    APIMessage("account",name, "saved")
  }

  post("/addProject"){
    //todo
  }

  // below is a routing to handle errors.
  error {
  case e: Throwable => {
    redirect("/api")
  }
}
}

case class Account(name: Any, status:Any)
case class APIMessage(entityType: String, entityID: String, entityStatus: String)
