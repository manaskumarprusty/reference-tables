package dao

sealed trait ArtemisReferenceTable {
  val tableName: String
}

object ArtemisReferenceTable {
  case object month extends ArtemisReferenceTable {
    override val tableName: String = "months"
  }

  case object zipCode extends ArtemisReferenceTable {
    override val tableName: String = "zip_codes"
  }
}