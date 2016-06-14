package com.example
import scala.util.control.Breaks._

object Hello {

  /*
  * Legend
  * x - SL
  * y - SP
  * z - GU / NA
  */

  val tierPercentage = List(
                        (1, "NA", 3),
                        (1, "SP", 8),
                        (2, "SP", 7),
                        (2, "SL", 7),
                        (3, "SP", 1),
                        (3, "SL", 1),
                        (1, "SL", 15))
  val maxTiers = List(
                ("NA",1),
                ("SP",1),
                ("SL",3))
  val users = List(
              (1,"NA",0),
              (2,"SP",0),
              (3,"SL",0),
              (4,"NA",1),
              (5,"NA",2),
              (6,"NA",3),
              (7,"NA",6),
              (8,"SP",2),
              (9,"NA",8),
              (10,"SP",3),
              (11,"NA",10),
              (12,"SL",3),
              (13,"NA",12),
              (14,"NA",13),
              (15,"SP",14),
              (16,"SP",12),
              (17,"NA",16),
              (18,"SL",12),
              (19,"NA",18),
              (20,"SL",2),
              (21,"SP",1),
              (22,"SL",1),
              (23,"SL",20),
              (24,"SL",10),
              (25,"SL",22),
              (26,"SL",6),
              (27,"SL",8),
              (28,"SP",20),
              (29,"SP",10),
              (30,"SL",21),
              (31,"SL",5),
              (32,"SP",22),
              (33,"SP",6),
              (34,"NA",20),
              (35,"SL",4),
              (36,"NA",22),
              (37,"SP",8),
              (38,"SP",21),
              (39,"SP",5),
              (40,"SP",4),
              (41,"NA",21),
              (42,"NA",5),
              (43,"NA",4),
              (44,"SL",18),
              (45,"SL",23),
              (46,"SL",24),
              (47,"SL",16),
              (48,"SP",18),
              (49,"SL",25),
              (50,"SL",26),
              (51,"SL",19),
              (52,"NA",18),
              (53,"SL",27),
              (54,"SL",28),
              (55,"SL",29),
              (56,"SP",23),
              (57,"SP",24),
              (58,"SP",16),
              (59,"SL",30),
              (60,"SL",31),
              (61,"SL",32),
              (62,"SL",33),
              (63,"SL",34),
              (64,"SL",11),
              (65,"SP",25),
              (66,"SP",26),
              (67,"SP",13),
              (68,"NA",23),
              (69,"NA",24),
              (70,"NA",48),
              (71,"SL",35),
              (72,"SL",36),
              (73,"SL",7),
              (74,"NA",25),
              (75,"NA",26),
              (76,"NA",37),
              (77,"SP",27),
              (78,"SP",28),
              (79,"SP",29),
              (80,"SL",38),
              (81,"SL",39),
              (82,"SL",9),
              (83,"SP",30),
              (84,"SP",31),
              (85,"SP",32),
              (86,"SP",33),
              (87,"SP",34),
              (88,"SP",11),
              (89,"NA",27),
              (90,"NA",28),
              (91,"NA",29),
              (92,"SL",40),
              (93,"SL",41),
              (94,"SL",42),
              (95,"SP",35),
              (96,"SP",36),
              (97,"SP",7),
              (98,"NA",30),
              (99,"NA",31),
              (100,"NA",32),
              (101,"NA",33),
              (102,"NA",34),
              (103,"NA",11),
              (104,"SL",43),
              (105,"NA",35),
              (106,"NA",36),
              (107,"NA",7),
              (108,"SP",37),
              (109,"SP",38),
              (110,"SP",39),
              (111,"SP",9),
              (112,"SP",40),
              (113,"SP",41),
              (114,"SP",42),
              (115,"NA",38),
              (116,"NA",39),
              (117,"NA",9),
              (118,"SP",43),
              (119,"NA",40),
              (120,"NA",41),
              (121,"NA",42),
              (122,"NA",43),
              (123,"NA",17))

  var counter = 1
  def main(args: Array[String]): Unit = {

    //println(allCombinations("XYZ", 2))
    val result = findUser(123,1)
    println("--------------------\n")
    if(result.size > 2){
        println(result)
        breakable {
          result.sliding(2) foreach{
            r => //println(r)
              val x = r.head
              val y = r.last

              println( if(counter > 2) counter - 1 else counter)
              val percent = getPercentage(if(counter > 2) counter - 1 else counter, y._2)
              if(!percent.isDefined || counter > getMaxTier(y._2).get){
                break
              }else{
                println(counter)
                println(y._2 +" "+ percent)
              }
              counter = counter + 1


          }
        }
    }else{
      val percent = getPercentage(counter, result.last._2)
      println(result.last._2 +" "+ percent)
    }
    println("\n--------------------")
    //println(getMaxTier("SL"))
    //println(getPercentage(1,"SL"))
  }

  def allCombinations[A](items: Seq[A], replication: Int = 0): List[List[A]] ={
    List.fill(if(replication > 0) replication else items.length )(items)
    .flatten.combinations( if(replication > 0) replication else items.length)
    .flatMap(_.permutations).toList
  }

  def findUser(id: Int, ctr: Int): Seq[(Int, String, Int)] ={
    val user = users.find(_._1 == id)
    user match {
      case Some(user) => if(user._3 > 0) {
                          println("recursion counter:" + ctr)
                            if(ctr < 4) // limit tiers to 3 only
                              Seq(user) ++ findUser(user._3, ctr + 1)
                            else
                              Seq(user)
                          }
                         else Seq(user)
      case None => Seq()
    }
  }

  def getMaxTier(role: String): Option[Int] = {
    maxTiers.find(_._1 == role).map(_._2)
  }

  def getPercentage(tier: Int, role: String): Option[Int] = {
    tierPercentage.find(r => r._1 == tier && r._2 == role).map(_._3)
  }
}
