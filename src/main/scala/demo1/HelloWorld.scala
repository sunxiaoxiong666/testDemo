package demo1

object HelloWorld {
  def main(args: Array[String]): Unit = {
    var x = 0;
    val num = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    var list = for {x <- num if x != 4; if x < 9} yield x
    println(list);
    for (x <- list if x != 3; if x != 7) {
      println(x)
      var a, b = 100;
      var sa,sb:String="a"
    }
  }
}
