package demo2

/*
* @author: sunxiaoxiong
*/

object IteratorTest {
  def main(args: Array[String]): Unit = {
    val list = List(1, 2, 34, 5)
    val iterator = list.iterator
    while (iterator.hasNext) {
      println(iterator.next())
    }
    println("============")
    //Iterator不是集合，而是访问集合的一种方法
    val a = Iterator(2, 3, 4, 5, 6)
    while (a.hasNext) {
      println(a.next())
    }

    println("====it.max it.min获取最大和最小元素========")
    val ita = Iterator(12, 13, 5, 14, 15, 16);
    val itb = Iterator(12, 13, 5, 14, 15, 16);
    println("最大元素为：" + ita.max)
    println("最小元素为：" + itb.min)

    println("=====it.size it.length获取迭代器元素的个数=========")
    val itc = Iterator(12, 13, 5, 14, 15, 16);
    val itd = Iterator(12, 13, 5, 14, 15, 16);
    println(itc.size)
    println(itd.length)

  }

}
