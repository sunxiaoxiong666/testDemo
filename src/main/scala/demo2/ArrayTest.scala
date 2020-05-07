package demo2

import scala.Array._

/*
* @author: sunxiaoxiong
*/

object ArrayTest {
  def main(args: Array[String]): Unit = {
    //声明数组的方式
    var arr: Array[String] = new Array[String](3);
    arr(0) = "aa";
    arr(1) = "2";
    var arr2 = new Array[String](3);
    var arr3 = Array("aa", "bb", "cc");
    var arr32 = Array("aa", "bb", "cc", 3);
    //二维数组
    var arr12 = Array(Array("a", "b"), Array("c", "d"), Array("e", "f"));

    //遍历数组
    for (a <- arr12) {
      println(a)
      for (b <- a) {
        println(b)
      }
    }

    //计算数组所有元素的和
    var arr4 = Array(1.2, 3, 5.4);
    var total = 0.0;
    for (i <- 0 to arr4.length - 1) {
      total += arr4(i);
    }
    println("总和为：" + total)

    //计算数组中最大的元素
    var max = arr4(0);
    for (i <- 1 to arr4.length - 1) {
      if (arr4(i) > max) {
        max = arr4(i);
      }
    }

    println("最大值为：" + max)
    println("最大值为max：" + arr4.max)
    println("最小值为min：" + arr4.min)
    //使用concat()方法合并数组
    var arr33 = Array("dd", "ee", "ff");
    var arr5 = concat(arr33, arr3);
    for (a <- arr5) {
      println("value of a:" + a)
    }

    //使用range()方法，创建区间范围内的数组。
    var arr6 = range(10, 20);
    var arr7 = range(10, 20, 2); //最后一个参数为步长
    for (a <- arr6) {
      print(a + " ")
    }
    println()
    for (a <- arr7) {
      print(a + " ")
    }
    println()

    println("========将偶数取出乘以10，再生成一个新的数组==========")
    var arr8 = Array(1, 2, 3, 4, 5, 6, 7, 8)
    //生成新数组
    var arr9 = for (a <- arr8 if a % 2 == 0) yield a * 10
    println(arr9.toBuffer)

    //filter是过滤，接受一个返回值为Boolean的函数
    //map相当于将数组中的每一个元素取出来，应用传进去的函数
    var arr10 = arr8.filter(_ % 2 == 0).map(_ * 10)
    println(arr10.toBuffer)

    println("========拉链操作 zip将多个值绑定在一起=======")
    //如果两个数组元素个数不一样时，生成的数组为元素个数少的
    var a = Array("战士", "射手", "法师")
    var b = Array("铠", "后羿", "妲己")
    val tuples = a.zip(b)
    println(tuples.toBuffer)
  }
}
