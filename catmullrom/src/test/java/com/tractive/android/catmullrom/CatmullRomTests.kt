package com.tractive.android.catmullrom

import android.graphics.Point
import com.google.android.gms.maps.model.LatLng
import com.tractive.android.catmullrom.data.Vector
import com.tractive.android.catmullrom.utils.CatmullUtils
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).

 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(JUnit4::class)
class CatmullRomTests {
    @Test
    fun testNumberOfPoints() {

        val p1 = LatLng(42.28, 12.36)
        val p2 = LatLng(43.28, 13.36)
        val p3 = LatLng(44.28, 14.36)
        val p4 = LatLng(45.28, 15.36)

        val combinedList = Arrays.asList(p1, p2, p3, p4)
        val numberOfPoints = 5


        val list = CatmullUtils.Companion.create(combinedList, CatmullRomSpline.Type.CHORDAL, numberOfPoints)

        assertTrue(list.size == (combinedList.size - 1) * (numberOfPoints + 1))
    }

    @Test
    fun testNanResult() {

        val p1 = LatLng(42.28, 12.36)

        val combinedList = Arrays.asList(p1, p1, p1, p1)
        val numberOfPoints = 5


        val list = CatmullUtils.Companion.create(CatmullRomSpline.Type.CHORDAL, numberOfPoints, p1, p1, p1, p1)

        assertTrue(list.size == 1)


    }

}