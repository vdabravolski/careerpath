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


  get("/api") {
    //todo: something useful goes here. Sample below of how to handle parameters in the request.
    // params.get("name") match {
    //   case Some(name) => FlowerData.all filter (_.name.toLowerCase contains name.toLowerCase())
    //   case None => FlowerData.all}
  }
}
