package demo2

/*
* @author: sunxiaoxiong
*/

object ForTest {
  def main(args: Array[String]): Unit = {
    var a = 0;
    var b = 0;
    //包含10
    for (a <- 1 to 10) {
      println("value of a:" + a)
    }
    //不包含10
    for (a <- 1 until 10) {
      println("value of a:" + a)
    }

    println("=======双重循环=========")
    for (a <- 1 to 3; b <- 1 to 3) {
      println("value of a:" + a)
      println("value of b:" + b)
    }

    println("=====for 遍历集合========")
    val list = List(1, 2, 3, 4, 5, 6);
    for (a <- list) {
      println("value of a:" + a)
    }

    println("======for循环过滤========")
    for (a <- list if a != 4; if a > 2) {
      println("value of a:" + a)
    }

    println("========for 使用yield===========")
    var x = 0;
    val numList = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    var list2 = for {x <- numList if x > 2; if x != 4} yield x
    println(list2)
    for (a <- list2) {
      println("value of a:" + a)
    }

    println("=========调用方法==========")
    println("方法的返回值为：" + addInt(2, 3));
    hello();

    println("======匿名函数=========")
    var inc = (x: Int) => x + 1;
    var inc2 = inc(6) - 1;
    println(inc2)

    var mul = (x: Int, y: Int) => x * y
    var mul2 = mul(2, 3);
    println(mul2)

    println("=========闭包函数=========")
    var i = 2;
    //使用到了外界的变量i
    var i2 = (ia: Int) => ia + i;
    println(i2(4))
  }

  //有返回值的方法
  def addInt(a: Int, b: Int): Int = {
    var sum: Int = 0;
    sum = a + b;
    return sum;
  }

  //没有返回值的方法
  def hello(): Unit = {
    println("hello world")
  }


}
