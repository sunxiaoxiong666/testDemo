package demo2

/*
* @author: sunxiaoxiong
*/

/*
* 注意：在Scala中，有两种Map，一个是immutable包下的Map，该Map中的内容不可变；另一个是mutable包下的Map，该Map中的内容可变
* 通常我们在创建一个集合是会用val这个关键字修饰一个变量（相当于java中的final），
* 那么就意味着该变量的引用不可变，该引用中的内容是不是可变，取决于这个引用指向的集合的类型
* */
object MapTest {
  def main(args: Array[String]): Unit = {

    //创建map
    var map = Map("a" -> "aa", "b" -> "bb", "c" -> "cc")
    val map2: Map[Int, Int] = Map()
    //使用元组创建map
    var map22 = Map(("a", "aa"), ("b", "bb"), ("b", "bb"))

    println("map中的键为：" + map.keys)
    println("map中的值为：" + map.values)
    println("map是否为空：" + map.isEmpty)
    println("map2是否为空：" + map2.isEmpty)

    println("======使用++  Map.++()来合并map集合，会删除重复的值=======")
    var map3 = Map("c" -> "cc", "d" -> "dd", "e" -> "ee");
    map = map ++ map3
    println("map ++ map3为：" + map)
    map = map.++(map3)
    println("map.++(map3):" + map)
    //使用+=在map中追加元素,会去重
    map3 += ("a" -> "aa", "c" -> "cc")
    println("map3中追加元素" + map3)

    println("====使用foreach循环输出map的key和values======")
    val map4 = Map("打野" -> "兰陵王", "射手" -> "后羿", "法师" -> "妲己")
    for (elem <- map4.values) {
      println(elem)
    }
    println("============")
    map4.keys.foreach { i =>
      print("key=" + i)
      println(",value=" + map4(i))
    }

    println("=====使用Map.contains方法来查看Map中是否存在指定的key======")
    val map5 = Map("辅助" -> "蔡文姬", "坦克" -> "亚瑟", "战士" -> "铠")
    if (map5.contains("辅助")) {
      println("辅助对应的值：" + map5("辅助"))
    } else if (map5.contains("坦克")) {
      println("坦克为：" + map5.get("坦克"))
    } else {
      println("战士是：" + map5("战士"))
    }

    println("======getOrElse(),有值返回对应的值，反之返回默认值========")
    println(map5.getOrElse("辅助", "瑶"))
    println(map5.getOrElse("刺客", "猴子"))
  }

}
