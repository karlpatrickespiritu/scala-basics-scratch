// https://www.youtube.com/watch?v=DzFt0YkZo8M

import scala.io.StdIn.{ readLine, readInt }
import scala.math._
import scala.collection.mutable.ArrayBuffer
import java.io.PrintWriter
import scala.io.Source

object ScalaTutorial {

	def main(args: Array[String]) {

		/********* LOOPS ************/
		// var i = 0 

		// while (i <= 10) {
		// 	println(i)
		// 	i += 1
		// }

		// do {
		// 	println(i)
		// 	i += 1
		// } while (i < 10)

		// for (i <- 1 to 10) 
		// 	println(i)

		// val randLetters = "ABCSDEFGHIJK"
		// for (i <- 0 until randLetters.length) 
		// 	println(randLetters(i))

		// val aList = List(5, 4, 3, 2, 1)
		// for (i <- aList) {
		// 	println("List item: " + i)
		// }

		// var eventList = for { i <- i to 20
		// 	if (i % 2) == 0 
		// } yield i

		// for (i <- eventList) {
		// 	println(i)
		// }

		// double dimention arrays
		// for (i <- 1 to 5; j <- 6 to 10) {
		// 	println("i :" + i)
		// 	println("j :" + j)
		// }

		// break and continue equivalent
		// def printPrimes() {
		// 	val primeList = List(1, 2, 3, 5, 7, 11)

		// 	for (i <- primeList) {
		// 		if (i == 11) 
		// 			return

		// 		if (i != 1)
		// 			println(i)
		// 	}
		// }

		// printPrimes

		// reading lines
		// var numberGuess  = 0

		// do {
		// 	print("Guess a number: ")
		// 	numberGuess = readLine.toInt
		// } while (numberGuess != 15)

		// printf("You guessed the secret number %d\n", 15)

		/******* PRINTING *******/

		// val name = "Derek"
		// val age = 39
		// val weight = 175.1

		// println(s"Hello $name")
		// println(f"I am ${age + 1} and weights $weight%.2f") // 2 decimal places

		// printf("'%5d'\n", 6) // justified
 	// 	printf("'%-5d'\n", 6)

		// %c - char
		// %d - integer types
		// %f - floating points
		// %s - strings


		/******** STRINGS ***********/
		var sTestString = "I am a string hehehe"

		println("Third: " + sTestString(3))
		println("length " + sTestString.length)
		println(sTestString.concat(" hahaaha"))
		println("test".equals("test"))
		println(sTestString.indexOf("hehehe"));


	}
}