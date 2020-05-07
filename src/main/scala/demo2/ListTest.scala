package demo2

/*
* @author: sunxiaoxiong
*/

object ListTest {
  def main(args: Array[String]): Unit = {
    val list: List[String] = List("a", "b");
    val list11 = "aa" :: ("bb" :: ("cc" :: Nil))
    val list2 = List(1, 2, 3, "a");
    val list22 = 1 :: (2 :: (3 :: Nil))
    //空列表
    val empty: List[Nothing] = List();
    //Nil可以表示一个空的集合
    val empty2 = Nil;
    //二维列表
    val list3: List[List[Int]] = List(List(1, 2), List(3, 4), List(5, 6))
    val list33 = (1 :: (2 :: Nil)) :: (3 :: (4 :: Nil)) :: (5 :: (6 :: Nil)) :: Nil

    for (a <- list33) {
      println(a)
      for (b <- a) {
        println(b)
      }
    }

    println("list11的第一个元素为：" + list11.head)
    println("list11除了第一个元素的其它元素：" + list11.tail)
    println("list11是否为空:" + list11.isEmpty)
    println("empty2是否为空:" + empty2.isEmpty)

    println("=========连接列表  :::   List.:::()    List.concat()=========")
    val l1 = "aa" :: ("bb" :: Nil)
    val l2 = "cc" :: ("dd" :: Nil)

    val l3 = l1 ::: l2
    val l4 = l1.:::(l2)
    val l5 = List.concat(l1, l2)
    println(l3)
    println(l4)
    println(l5)

    println("========List.fill()创建指定重复数量的元素列表===========")
    val l6 = List.fill(5)(6);
    val l7 = List.fill(4)("vv");
    println(l6)
    println(l7)

    println("=======List.tabulate()方法是通过给定的函数创建列表===========")
    val l8 = List.tabulate(6)(n => n * n);
    println(l8)
    val l9 = List.tabulate(2, 4)(_ + _);
    println("二维数组：" + l9)

    println("=============List.reverse将集合反转排序============")
    val l10 = "aa" :: ("bb" :: ("bb" :: Nil))
    println("反转后为：" + l10.reverse)
    val distinct = l10.distinct //将集合中的元素去重
    println("去重后的集合为：" + distinct)

    println("=============查找最大元素和最小元素=========")
    val li=List(1,2,3);
    println("最大元素为："+li.max)
    println("最小元素为："+li.min)
  }

}
