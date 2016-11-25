package voice.testing

import voice.core.DBSchema
import voice.core.DBSchema._

/**
  * Created by maprohu on 12-11-2016.
  */
object RunDBSchema {

  def main(args: Array[String]): Unit = {

    val ba = DBSchema
      .serialize(
        StructureType(
          Seq(
            Field(
              Tag(1),
              PrimitiveType(
                PrimitiveTypes.byte
              )
            ),
            Field(
              Tag(4),
              ArrayType(
                StructureType(
                  Seq(
                    Field(
                      Tag(2),
                      PrimitiveType(
                        PrimitiveTypes.float
                      )
                    ),
                    Field(
                      Tag(3),
                      ArrayType(
                        PrimitiveType(
                          PrimitiveTypes.int
                        )
                      )
                    )
                  )
                )
              )

            )
          )
        )
      )

    val t = DBSchema.deserialize(ba)

    println(t)

  }

}
