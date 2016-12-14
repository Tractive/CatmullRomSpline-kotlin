package com.tractive.android.catmullrom

import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Example local unit test, which will execute on the development machine (host).

 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(JUnit4::class)
class CatmullRomTests {
    @Test
    @Throws(Exception::class)
    fun testNanValues() {
        assertEquals(4, (2 + 2).toLong())

        val p1: DoubleArray = doubleArrayOf(48.282906, 14.362198)
        val p2: DoubleArray = doubleArrayOf(48.282906, 14.362198)
        val p3: DoubleArray = doubleArrayOf(48.2829, 14.362198)
        val p4: DoubleArray = doubleArrayOf(48.2829, 14.3622)


        val list: List<DoubleArray> = CatmullRomSpline(CatmullRomSpline.Companion.Type.CHORDAL, 10, p1, p2, p3, p4, mConverter = object : CatmullRomSpline.Companion.Converter<DoubleArray> {
            override fun convertFrom(_source: DoubleArray): Vector = Vector(_source[0], _source[1])

            override fun convertTo(_vector: Vector): DoubleArray = doubleArrayOf(_vector.x, _vector.y)
        }).createCatmullRomPointList()

        assertTrue(list.isEmpty())
    }
}