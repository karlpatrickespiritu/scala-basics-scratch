// https://www.youtube.com/watch?v=DzFt0YkZo8M

import scala.io.StdIn.{ readLine, readInt }
import scala.math._
import scala.collection.mutable.ArrayBuffer
import java.io.PrintWriter
import scala.io.Source

object ScalaTutorial {

	def main(args: Array[String]) {

		/********* LOOPS ************/
		var i = 0 

		while (i <= 10) {
			// println(i)
			i += 1
		}

		do {
			// println(i)
			i += 1
		} while (i < 10)

		for (i <- 1 to 10) {
			// println(i)
		}

		val randLetters = "ABCSDEFGHIJK"
		for (i <- 0 until randLetters.length) {
			// println(randLetters(i))
		}

		val aList = List(5, 4, 3, 2, 1)
		for (i <- aList) {
			// println("List item: " + i)
		}

		var eventList = for { i <- i to 20
			if (i % 2) == 0 
		} yield i

		for (i <- eventList) {
			// println(i)
		}

		// double dimention arrays
		for (i <- 1 to 5; j <- 6 to 10) {
			// println("i :" + i)
			// println("j :" + j)
		}

		// break and continue equivalent
		def printPrimes() {
			val primeList = List(1, 2, 3, 5, 7, 11)

			for (i <- primeList) {
				if (i == 11) 
					return

				if (i != 1) {
					// println(i)
				}
			}
		}

		printPrimes

		// reading lines
		var numberGuess  = 0

		// do {
		// 	print("Guess a number: ")
		// 	numberGuess = readLine.toInt
		// } while (numberGuess != 15)

		// printf("You guessed the secret number %d\n", 15)

		/******* PRINTING *******/

		val name = "Derek"
		val age = 39
		val weight = 175.1

		// println(s"Hello $name")
		// println(f"I am ${age + 1} and weights $weight%.2f") // 2 decimal places

		// printf("'%5d'\n", 6) // justified
 		// printf("'%-5d'\n", 6)

		// %c - char
		// %d - integer types
		// %f - floating points
		// %s - strings


		/******** STRINGS ***********/
		var sTestString = "I am a string hehehe"

		// println("Third: " + sTestString(3))
		// println("length " + sTestString.length)
		// println(sTestString.concat(" hahaaha"))
		// println("test".equals("test"))
		// println(sTestString.indexOf("hehehe"));


		/***** FUNCTIONS ********/

		def getSum (num1: Int = 1, num2: Int = 1) : Int = {
			return num1 + num2
		}

		// println(getSum(5, 4));


		// no return function
		def noReturn () : Unit = {
			println("I have no return!")
		}

		// noReturn

		// bunch of arguments
		def bunchArgs (arguments: Int*): Int = {
			var sum: Int = 0
			for (argument <- arguments) {
				sum += argument
			} 
			return sum
		}

		// println("Bunch of ints sum: " + bunchArgs(1, 2, 3, 5))

		// recursion
		def factorial (num: BigInt): BigInt = {
			if (num <= 1) 
			 return 1
			else 
			 return num * factorial(num - 1)
		}

		// println("factorial of 4: " + factorial(4))


		/********** ARRAYS *********/
		var myNums = Array(1, 2,  3, 4, 5, 6, 7)

		// println(myNums.min)
		// println(myNums.max)
		// println(myNums.sum)

		var myNumsSorted = myNums.sortWith(_>_)

		// println(myNumsSorted)

		/********* MAPS ************/
		// key pair values
		var employees = Map("Dev" -> "Karl", "SenDev" -> "Brian") // default is mutable
		var employeesMutable = collection.mutable.Map("Dev" -> "Karl", "SenDev" -> "Brian") // mutable

		employeesMutable("Dev") = "Rex"
		// println(employeesMutable)

		// looping key-pairs
		for ((k, v) <- employeesMutable) {
			// println(s"${k}: ${v}")
		}

		/********** TOUPLES **************/
		// normally immutable

		var myTouple = (1.3, "Karl", 5)

		// println(myTouple._1 + " " + myTouple._2) // accessing touple
		// println(myTouple.toString())

		// loop through touple 
		// myTouple.productIterator.foreach { i => println(i) }


		/********* CLASESS ***********/

		var tiger = new Animal
		tiger.setName("Karl")
		tiger.setSound("Raawrrr!")
		// println(tiger.toString())

		var dog = new Animal
		dog.setName("Manna")
		dog.setSound("Meows!")
		// println(dog.toString())

		var simon = new Dog("Simon", "ayaw shimoon")
		simon.setGrowl("shimonGrowls")
		// println(simon.toString())
		
	}


	/********** CLASSES ****************/

	object Animal {
		private var id = 0
		def generateId(): Int = { id += 1; id }
	}

	class Animal(private var name: String = "", private var sound: String = "") {

		protected val id = Animal.generateId()

		def getName(): String = name
		
		def getSound(): String = sound

		def getId(): Int = { this.id }

		def setSound(sound: String) {
			this.sound = sound
		}

		def setName(sound: String) {
			this.name = sound
		}

		override def toString(): String = {
			s"${this.name} that has an ID of ${this.id} says ${this.sound}"
		}
	}

	class Dog(name: String , sound: String, private var growl: String = "") extends Animal(name, sound) {
		
		def getGrowl(): String = {
			this.growl
		}

		def setGrowl(growl: String) {
			this.growl = growl
		}

		override def toString(): String = {
			s"${this.getName()} that has an ID of ${this.getId()} says `${this.getSound()}` growl: ${this.getGrowl()}"
		}
	}

} // end of object