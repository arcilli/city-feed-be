package cityfeed.model

import java.util.UUID

case class UserCredentials(
  id: UUID,
  username: String,
  email_address: String,
  password: String,
)

case class User(
  id: Option[UUID],
  fullName: String,
  city: String,
  homeAddress: String,
  neighborhood: String,
  credentials: Option[UserCredentials],
  seenPosts: List[Int] = List.empty
)
