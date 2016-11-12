package voice.core

import java.nio.ByteBuffer

/**
  * Created by maprohu on 12-11-2016.
  */
object DBSchema {

  object PrimitiveTypes extends Enumeration {
    case class Item(
      size: Int
    ) extends Val

    val byte = Item(
      java.lang.Byte.BYTES
    )
    val short = Item(
      java.lang.Short.BYTES
    )
    val int = Item(
      java.lang.Integer.BYTES
    )
    val long = Item(
      java.lang.Long.BYTES
    )
    val float = Item(
      java.lang.Float.BYTES
    )
    val double = Item(
      java.lang.Double.BYTES
    )
    val boolean = Item(
      1
    )
    val char = Item(
      java.lang.Character.BYTES
    )
  }

  val PrimitiveTypeBitCount = 4
  val MaxPrimitiveTypeCount = 1 << (PrimitiveTypeBitCount - 1)
  val PrimitiveTypeMask = MaxPrimitiveTypeCount - 1
  require(PrimitiveTypes.values.size <= MaxPrimitiveTypeCount)

  object DataTypes extends Enumeration {
    val

      primitive,
      structure,
      array

    = Value
  }

  val DataTypeBitCount = 3
  val MaxDataTypeCount = 1 << (DataTypeBitCount - 1)
  val DataTypeMask = MaxDataTypeCount - 1
  require(DataTypes.values.size <= MaxDataTypeCount)

  sealed trait DataType

  case class PrimitiveType(
    element: PrimitiveTypes.Value
  ) extends DataType

  case class ArrayType(
    element: DataType
  ) extends DataType

  case class Tag(
    id: Long
  )

  case class Field(
    tag: Tag,
    dataType: DataType
  )

  case class StructureType(
    fields: Seq[Field]
  ) extends DataType

  def sizeOf(dataType: DataType) : Int = dataType match {
    case _ : PrimitiveType => 1
    case a : ArrayType => 1 + sizeOf(a.element)
    case s : StructureType =>
      1 +
        java.lang.Integer.BYTES +
        s
          .fields
          .map({ f =>
            java.lang.Long.BYTES +
              sizeOf(f.dataType)
          })
          .sum
  }

  val Version = 0.toShort

  def serialize(dataType: DataType) : Array[Byte] = {
    val a = Array.ofDim[Byte](
      java.lang.Short.BYTES +
        sizeOf(dataType)
    )
    val bb = ByteBuffer.wrap(a)
    bb.putShort(Version)

    def write(dt: DataType) : Unit = {
      dt match {
        case p : PrimitiveType =>
          bb.put(
            (
              DataTypes.primitive.id |
                (p.element.id << DataTypeBitCount)
            ).toByte
          )
        case at : ArrayType =>
          bb.put(
            DataTypes.array.id.toByte
          )
          write(at.element)
        case st: StructureType =>
          bb.put(
            DataTypes.structure.id.toByte
          )
          bb.putInt(st.fields.size)
          st
            .fields
            .foreach({ f =>
              bb.putLong(f.tag.id)
              write(f.dataType)
            })
      }
    }

    write(dataType)

    a
  }

  def deserialize(ba: Array[Byte]) : DataType = {
    val bb = ByteBuffer.wrap(ba)
    require(bb.getShort() == Version)

    def read() : DataType = {
      val b = bb.get()

      val dtIdx = b & DataTypeMask

      DataTypes(dtIdx) match {
        case DataTypes.primitive =>
          val ptIdx = (b >> DataTypeBitCount) & PrimitiveTypeMask
          PrimitiveType(
            PrimitiveTypes(ptIdx)
          )

        case DataTypes.array =>
          val adt = read()
          ArrayType(adt)

        case DataTypes.structure =>
          val count = bb.getInt
          StructureType(
            (0 until count).map({ _ =>
              val tag = Tag(bb.getLong)
              Field(
                tag,
                read()
              )
            })
          )


      }


    }

    read()
  }

}
