package cityfeed.model

import java.util.UUID

case class UserCredentials(
  id: UUID,
  username: String,
  email_address: String,
  password: String,
)

case class User(
  id: Option[Int],
  fullName: String,
  city: String,
  homeAddress: String,
  neighborhood: String,
  credentials: UserCredentials
)
