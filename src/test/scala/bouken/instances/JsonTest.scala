package bouken.instances

import scala.util.Try

import io.circe._
import io.circe.syntax._
import io.circe.literal._
import io.circe.generic.semiauto.deriveEncoder
import org.scalatest.{FreeSpec, Matchers}

class JsonTest extends FreeSpec with Matchers {
  import JsonTest._

  case class CoolClass(value: BigDecimal)
  "Decoding" - {
//    implicit val encodeBigDecimalUnsafe: Encoder[BigDecimal] =
//      Encoder.instance(a ⇒ Json.fromJsonNumber(JsonNumber.fromDecimalStringUnsafe(a.bigDecimal.toPlainString)))

    implicit val encodeBigDecimalSafe: Encoder[BigDecimal] =
      Encoder.instance{ a ⇒
        Try(JsonNumber.fromIntegralStringUnsafe(a.bigDecimal.toPlainString)).toOption.asJson
      }

    implicit val enc: Encoder[CoolClass] = deriveEncoder[CoolClass]

    "works with good numbers" in {
      val magicNumber = BigDecimal("4930592405923095023950238502395.3259023950235902")

      val json = CoolClass(magicNumber).asJson

      println(json)
      json shouldBe correct
    }

    "handles the nulls" in {
      val json = CoolClass(null).asJson

      println(json)
      json shouldBe nullJson
    }

    "returns error with bad ones" in {
      val bg = CoolClass(BigDecimal(bd = null))
      val json = bg.asJson

      println(json)
      json shouldBe nullJson
    }
  }



}
object JsonTest{
  val correct: Json = json"""{
    "value" : 4930592405923095023950238502395.3259023950235902
  }"""

  val nullJson: Json = json"""{
    "value" : null
  }"""
}
