package com.charlezz.opencvtutorial

import org.junit.Test
import org.opencv.core.Point

class SquareTracingTest {

    val image = arrayOf(
        arrayOf(0,0,0,0,0,0,0,0,0,0),
        arrayOf(0,0,255,255,0,0,0,0,0,0),
        arrayOf(0,0,255,255,0,0,0,0,0,0),
        arrayOf(0,0,255,255,0,0,0,0,0,0),
        arrayOf(0,0,0,0,0,0,0,0,0,0),
        arrayOf(0,0,0,0,0,0,0,0,0,0),
        arrayOf(0,0,0,0,0,0,0,0,0,0),
        arrayOf(0,0,0,0,0,0,0,0,0,0),
        arrayOf(0,0,0,0,0,0,0,0,0,0),
        arrayOf(0,0,0,0,0,0,0,0,0,0)
    )

    @Test
    fun findContour(){
        for(row in image.indices){
            for(col in image[row].indices){
                if(image[row][col]==255){
                    squreTrace(Point(row.toDouble(),col.toDouble()))
                }
            }
        }
    }

    private fun squreTrace(start : Point){
        val boundaryPoints = LinkedHashSet<Point>()
        boundaryPoints.add(start)

        var nextStep = goLeft(Point(1.0,0.0))
        var next = start+nextStep

        while(next != start){
            if(image[next.x.toInt()][next.y.toInt()] == 0){
                next -= nextStep
                nextStep = goRight(nextStep)
                next += nextStep
            }else{
                boundaryPoints.add(next)
                nextStep= goLeft(nextStep)
                next += nextStep
            }
            println("next = $next")
        }
    }

    private fun goLeft(p:Point) = Point(p.y, -p.x)
    private fun goRight(p:Point) = Point(-p.y, p.x)

    operator fun Point.plus(point:Point):Point{
        return Point(this.x+point.x,this.y+point.y)
    }

    operator fun Point.minus(point:Point):Point{
        return Point(this.x-point.x,this.y-point.y)
    }
}