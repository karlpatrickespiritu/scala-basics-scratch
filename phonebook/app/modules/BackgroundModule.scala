package modules

import play.api.libs.concurrent.AkkaGuiceSupport
import com.google.inject.AbstractModule
import actors.BackgroundActor

class BackgroundModule extends AbstractModule with AkkaGuiceSupport {
  def configure {
    bindActor[BackgroundActor]("background-actor")
  }
}