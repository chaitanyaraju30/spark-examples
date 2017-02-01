package com.github.mrpowers.spark.examples

import org.scalatest._
import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{StringType, StructField, StructType}

class HelloWorldSpec extends FunSpec with ShouldMatchers with DataFrameSuiteBase {

  import spark.implicits._

  it("appends a greeting column to a Dataframe") {

    val sourceDf = Seq(
      ("miguel"),
      ("luisa")
    ).toDF("name")

    val actualDf = HelloWorld.withGreeting(sourceDf)

    val expectedSchema = List(
      StructField("name", StringType, true),
      StructField("greeting", StringType, false)
    )

    val expectedData = Seq(
      Row("miguel", "hello world"),
      Row("luisa", "hello world")
    )

    val expectedDf = spark.createDataFrame(
      spark.sparkContext.parallelize(expectedData),
      StructType(expectedSchema)
    )

    assertDataFrameEquals(actualDf, expectedDf)

  }

}
