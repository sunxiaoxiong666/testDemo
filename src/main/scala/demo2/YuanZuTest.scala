package demo2

/*
* @author: sunxiaoxiong
*/

object YuanZuTest {
  def main(args: Array[String]): Unit = {
    //元组是不可变的，但是可以包含不同类型的数值
    val t = (1, 3.14, "aa")
    val tuple = new Tuple3(1, 3.2, "vv")
    //使用t._1访问第一个元组，t._2访问第二个元组，脚标是从1开始的
    val sum = t._1 + t._2 + t._3
    println("元组元素的和为：" + sum)

    println("=====Tuple.productIterator()迭代输出元组的所有元素======")
    t.productIterator.foreach {
      i => println("value=" + i)
    }

    println("=====Tuple.toString()元组转换为字符串=======")
    println("转换后的字符串为：" + t.toString())

    println("=======Tuple.swap交换元组的元素===========")
    //注意此方法只能适用于两个元素
    val t2 = new Tuple2("aa", 2)
    println("交换后的元组为：" + t2.swap)

    println("=========将对偶的集合转换为map集合=====")
    val arr = Array(("aa", 11), ("bb", 22))
    println(arr.toMap)


  }
}
