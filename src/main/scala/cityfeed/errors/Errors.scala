package cityfeed.errors

object Errors {
  case class NonExistentAccount(email_address: String) extends Throwable {
    override def getMessage: String = s"Didn't find any account with email address: $email_address"
  }

  case class WrongCredentials(email_address: String) extends Throwable {
    override def getMessage: String = s"The email address or password are incorrect"
  }

  case class NoFetchedPosts() extends Throwable {
    override def getMessage: String = s"Couldn't find any posts at the moment"
  }
}
