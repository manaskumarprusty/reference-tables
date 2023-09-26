package dao



object ArtemisReferenceTable {
  sealed trait ArtemisReferenceTable {
    val tableName: String
  }
  case object Month extends ArtemisReferenceTable {
    override val tableName: String = "months"
  }

  case object zipCode extends ArtemisReferenceTable {
    override val tableName: String = "zip_codes"
  }
}