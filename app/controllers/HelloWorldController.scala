package controllers

import javax.inject.Inject
import javax.inject.Singleton
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, Request}

/**
 * This controller handles hello world http requests
 */
@Singleton
class HelloWorldController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  /**
   * Sample action that returns hello world when called.
   * The path is mappend uin the `conf/routes` file.
   */
  def say_hello(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok("Hello World")
  }
}
