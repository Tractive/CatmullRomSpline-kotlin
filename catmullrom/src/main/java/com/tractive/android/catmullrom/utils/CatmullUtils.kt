package com.tractive.android.catmullrom.utils

import android.graphics.Point
import android.support.annotation.Nullable
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import com.tractive.android.catmullrom.CatmullRomSpline
import com.tractive.android.catmullrom.data.Vector
import java.util.*

/**
 * Created by stephan on 14/12/16.
 */
class CatmullUtils {


    companion object {

        fun create(_type: CatmullRomSpline.Type, _numberOfPoints: Int, p1: Point, p2: Point, p3: Point, p4: Point): List<Point> {
            return CatmullRomSpline(_type, _numberOfPoints, p1, p2, p3, p4, object : CatmullRomSpline.Companion.Converter<Point> {

                override fun convertTo(_vector: Vector): Point = Point(_vector.x.toInt(), _vector.y.toInt())

                override fun convertFrom(_source: Point): Vector {
                    return Vector(_source.x.toDouble(), _source.y.toDouble())
                }
            }).createCatmullRomPointList()

        }

        fun create(_type: CatmullRomSpline.Type, _numberOfPoints: Int, p1: LatLng, p2: LatLng, p3: LatLng, p4: LatLng): List<LatLng> {
            val catmullRomSpline = CatmullRomSpline(_type, _numberOfPoints, p1, p2, p3, p4, object : CatmullRomSpline.Companion.Converter<LatLng> {

                override fun convertTo(_vector: Vector): LatLng {
                    return LatLng(_vector.x, _vector.y)
                }


                override fun convertFrom(_source: LatLng): Vector {
                    return Vector(_source.latitude, _source.longitude)
                }
            })

            return catmullRomSpline.createCatmullRomPointList()
        }


        fun create(_latLngs: List<LatLng>?, _type: CatmullRomSpline.Type, _numberOfPoints: Int): List<LatLng> {

            if (_latLngs == null) {
                return LinkedList()
            }
            if (_latLngs.size <= 1) {
                return _latLngs
            }

            val extendedLatLngList = LinkedList(_latLngs)
            val firstElement = _latLngs.first()
            val secondElement = _latLngs[1]
            val lastElement = _latLngs.last()
            val nextToLastElement = _latLngs[_latLngs.size - 2]

            extendedLatLngList.addFirst(SphericalUtil.computeOffset(firstElement, SphericalUtil.computeDistanceBetween(firstElement, secondElement), SphericalUtil.computeHeading(firstElement, secondElement) - 180))
            extendedLatLngList.addLast(SphericalUtil.computeOffset(lastElement, SphericalUtil.computeDistanceBetween(nextToLastElement, lastElement), SphericalUtil.computeHeading(nextToLastElement, lastElement)))

            val allPoints = LinkedList<LatLng>()

            //last point in catmull rom sequence is always p3 in [p1,p2,p3,p4], therefore we have to add p2 manually
            allPoints.add(firstElement)

            1.rangeTo(extendedLatLngList.size - 3).forEach { i ->
             val listResult =   CatmullUtils.create(_type, _numberOfPoints, extendedLatLngList[i - 1], extendedLatLngList[i], extendedLatLngList[i + 1], extendedLatLngList[i + 2])
                allPoints.addAll(listResult)
            }


            return allPoints
        }
    }

}