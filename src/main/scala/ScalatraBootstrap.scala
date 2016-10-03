import com.github.vdabravolski._
import org.scalatra._
import javax.servlet.ServletContext
import com.mongodb.casbah.Imports._


class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    val mongoClient=MongoClient("10.6.198.231", 27017)

    val accountColl = mongoClient("test")("accounts")
    val projectColl = mongoClient("test")("projects")

    //mounting multiple controllers
    context.mount(new CareerServlet(accountColl), "/*") // controller for HTML pages
    context.mount(new AccountAPIServlet(accountColl),"/api/accounts/*") //controller for Account API
    context.mount(new ProjectAPIServlet(projectColl),"/api/projects/*") //controller for Project API
  }
}
