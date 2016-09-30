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
     params.get("name") match {
       case Some(name) => name.toLowerCase()
       case None => "empty"}
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
    "successfully added new account:"+name
  }

  post("/addProject"){
    //todo
  }


}
