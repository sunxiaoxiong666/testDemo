package demo2

//import scala.collection.mutable.Set

/*
* @author: sunxiaoxiong
*/

object SetTest {
  def main(args: Array[String]): Unit = {
    val arr: Array[String] = new Array[String](4);
    val list: List[String] = List("aa", "bb");
    import scala.collection.mutable.Set
    val set = Set(1, 4, 3);
    println(set.getClass.getName)
    println(set.exists(_ % 2 == 0))
    println(set.drop(1))
    //如果使用可变集合则需要引入scala.collection.mutable.Set，在任何位置引入都可以
    set.add(2)
    println("添加元素后为：" + set)
    set.remove(4)
    println("删除元素后为：" + set)
    set += 4
    println(set)

    println("========使用++或者Set.++()方法连接两个集合，删除重复的元素============")
    val set2 = Set("aa", "bb", "cc")
    val set3 = Set("cc", "dd")
    var set4 = set2 ++ set3
    println("连接两个集合后的结果为：" + set4)

    set4 = set2.++(set3)
    println(set4)

    println("=====set.min查找最小的元素，set.max查找最大的元素========")
    val set5 = Set(1, 2, 3, 4, 5, 7);
    println("最大的元素为：" + set5.max)
    println("最小的元素为：" + set5.min)

    println("=========查找两个集合的交集元素set.&  set.intersect==========")
    println("set2和set3集合的交集为：" + set2.&(set3))
    println("set2和set3集合的交集为：" + set2.intersect(set3))
    
  }
}
