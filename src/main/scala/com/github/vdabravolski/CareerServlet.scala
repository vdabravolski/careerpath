package com.github.vdabravolski

import org.scalatra._
import com.mongodb.casbah.Imports._


// this is routes class

class CareerServlet(mongoColl : MongoCollection) extends CareerpathStack {

  get("/dashboard") {

    val person:String = params.getOrElse("person", halt(404, <h1>Pass person's name or go away!</h1>))
    val account:String = params.getOrElse("account", halt(404, <h1>Pass account name or go away!</h1>))
    val project:String = params.getOrElse("project", "all")

    // start retrieving data from MongoDB!
    val q = MongoDBObject("name" -> account)
    val accountRecord = mongoColl.findOne(q)


    <html>
      <body>
        <h1>Hello, {person}!</h1>
        <p>
        Showing info about past accounts and projects.
        </p>
        <p><b> Account details:</b>
          <ul>
            <li>Name: {accountRecord.get.getAs[String]("name").get}</li>
            <li>Status: {accountRecord.get.getAs[String]("status").get}</li>
            <!-- <li>Duration: {accountRecord.get.getAs[Int]("duration").get}</li>
            todo: for some reason duration doesn't work. Because of INT type??? -->
          </ul>
        </p>
      </body>
    </html>
  }

  get("/") {

    <html>
      <body>
        <h1>Default page!</h1>
        Nothing to see. Please pass person's name.
      </body>
    </html>
  }


}
