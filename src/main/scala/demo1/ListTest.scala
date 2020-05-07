package demo1

object ListTest {
  def main(args: Array[String]): Unit = {

    //字符串集合
    var str: List[String] = List("aa", "bb", "cc");
    //数字集合
    var list: List[Int] = List(1, 2, 3);
    println(list.max);
    //空集合
    var non: List[Nothing] = List();

    println(str)
    for (x <- str) {
      println(x);
    }

    println(non);
    for (x <- non) {
      println(x)
    }

    /*
    * 构造列表的基本单位是Nil和::
    * Nil也可以表示一个空列表
    * */
    //字符串集合
    var aa = "aa" :: ("bb" :: ("cc" :: Nil))
    //数字集合
    var bb = 1 :: (2 :: (3 :: (4 :: Nil)))
    //空集合
    var cc = Nil;

    println(aa);
    for (x <- aa) {
      println(x);
    }
    println("-------------------");
    //返回第一个元素
    println(aa.head);
    //返回除了第一个元素外的其他元素
    println(aa.tail);
    //判断集合是否为空
    println(aa.isEmpty);
    println(cc.isEmpty);

    //连接列表
    println("连接列表------------");
    var dd = str ::: aa;
    println(dd);

    var ee = str.:::(aa);
    println(ee);

    var ff = List.concat(str, aa);
    println(ff);

    //List.fill()创建重复数量元素的集合
    var gg = List.fill(3)("aa");
    var ggg = List.fill(4)(2);
    println(gg);
    println(ggg);

    //List.tabulate()通过给定的函数创建列表
    var h = List.tabulate(6)(n => n * n);
    println(h);

    println("========================");
    var as = Iterator("a","b","c");
    println(as);
    while (as.hasNext) {
      println(as.next());
    }

  }
}
