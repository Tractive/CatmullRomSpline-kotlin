package com.tractive.android.catmullrom

import java.util.*

/**
 * Created by stephan on 13/12/16.
 */

class CatmullRomSpline<out T> constructor(private val mType: Type, private val mNumberOfPoints: Int, _p1: T, _p2: T, _p3: T, _p4: T, private val mConverter: Converter<T>) {


    private val vector0 = mConverter.convertFrom(_p1)
    private val vector1 = mConverter.convertFrom(_p2)
    private val vector2 = mConverter.convertFrom(_p3)
    private val vector3 = mConverter.convertFrom(_p4)


    fun createCatmullRomPointList(): List<T> {


        val t0 = 0.0
        val t1 = calculateKnotFor(t0, vector0, vector1)
        val t2 = calculateKnotFor(t1, vector1, vector2)
        val t3 = calculateKnotFor(t2, vector2, vector3)

        val points = LinkedList<T>()

        var t = t1

        while (t < t2) {

            val a1 = vector0 * ((t1 - t) / (t1 - t0)) + vector1 * ((t - t0) / (t1 - t0))
            val a2 = vector1 * ((t2 - t) / (t2 - t1)) + vector2 * ((t - t1) / (t2 - t1))
            val a3 = vector2 * ((t3 - t) / (t3 - t2)) + vector3 * ((t - t2) / (t3 - t2))

            val b1 = a1 * ((t2 - t) / (t2 - t0)) + a2 * ((t - t0) / (t2 - t0))
            val b2 = a2 * ((t3 - t) / (t3 - t1)) + a3 * ((t - t1) / (t3 - t1))

            val c = b1 * ((t2 - t) / (t2 - t1)) + b2 * ((t - t1) / (t2 - t1))

            if (!java.lang.Double.isNaN(c.x) && !java.lang.Double.isNaN(c.y)) {
                points.add(mConverter.convertTo(c))

            }

            t += (t2 - t1) / mNumberOfPoints
        }

        return points
    }

    private fun calculateKnotFor(_previousKnot: Double, _vectorA: Vector, _vectorB: Vector): Double {

        return Math.pow(Math.sqrt(Math.pow(_vectorB.x - _vectorA.x, 2.0) + Math.pow(_vectorB.y - _vectorA.y, 2.0)), mType.alpha) + _previousKnot
    }

    enum class Type(val alpha: Double) {
        UNIFORM(0.0),
        CHORDAL(1.0),
        CENTRIPETAL(0.5)
    }

    companion object {

        interface Converter<T> {

            fun convertTo(_vector: Vector): T

            fun convertFrom(_source: T): Vector
        }

    }
}
