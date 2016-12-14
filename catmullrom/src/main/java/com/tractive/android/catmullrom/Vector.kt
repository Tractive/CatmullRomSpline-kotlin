package com.tractive.android.catmullrom

/**
 * Created by stephan on 13/12/16.
 */

class Vector(val x: Double, val y: Double) {
    operator fun plus(vector: Vector) = Vector(x + vector.x, y + vector.y)
    operator fun times(coefficient: Double) = Vector(x * coefficient, y * coefficient)
    override fun toString(): String = "[$x, $y]"
}