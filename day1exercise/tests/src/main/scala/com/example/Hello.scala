package com.example

// http://scala-exercises.47deg.com/koans#valandvar

object Hello {

	val myList = List(
  			(1, "Jade"),
  			(2, "JC"),
  			(3, "Cams"),
  			(4, "Mikel"),
  			(5, "Anthony"),
  			(6, "Lesmes"),
  			(7, "Karl")
  		)

  def main(args: Array[String]): Unit = {
  	println("madam: " + palindrome("madam")) 
  	println("karl: " + palindrome("karl"))

  	println("======================");

  	println("3: " + fizzbuzz(3))
  	println("5: " + fizzbuzz(5))
  	println("15: " + fizzbuzz(15))

  	println("======================");

  	processList(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
  	
  	println("======================");

  	println("Karl: " + findByName("Karl"))
  	println("7: " + findById(7))

  	println("======================");

  	fib
  }

  def fib (): Unit = {
  	var fib = List.empty[Int]

  	fib = fib :+ 0
  	fib = fib :+ 1

  	for (i <- 2 to 10) {
  		fib = fib :+ (fib(i - 2) + fib(i - 1))
  	}

  	println(fib)
  }

  def findByName(name: String): Option[(Int, String)] = {
  	myList.filter(_._2 == name).headOption
  }

  def findById(id: Int): Option[(Int, String)] = {
  	myList.filter(_._1 == id).headOption
  } 

  def processList(list: List[Int]): Unit = {
  	println(list)
  	println(list.filter(_ % 2 == 0))
  	println(list.filterNot(_ % 2 == 0))
  	println(list(list.sum / list.length))
  	println(list.sum / list.length)
  	println(list.max)
  	println(list.min)
  }

  def palindrome(word: String): Boolean = {
  	return word.reverse == word
  }

  def fizzbuzz(num: Int): String = {
  	val divisibleBy3 = num % 3 == 0
  	val divisibleBy5 = num % 5 == 0
  
  	if (divisibleBy3 && divisibleBy5) {
  		return "Fizz Buzz"
  	} else if (divisibleBy3) {
  		return "Fizz"
  	} else if (divisibleBy5) {
  		return "Buzz"
  	} 

  	return ""
  }
}
