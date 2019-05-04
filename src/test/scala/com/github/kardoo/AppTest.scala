package com.github.kardoo

import org.junit._
import Assert._

@Test
class AppTest {

  @Test
  def testOK() = {

    val x: Short = 1177
    println(x.toHexString)

   assertTrue(x.toHexString.equals("499"))
  }

  //    @Test
  //    def testKO() = assertTrue(false)

}


