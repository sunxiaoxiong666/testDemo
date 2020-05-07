package demo2

/*
* @author: sunxiaoxiong
*/

object OptionTest {
  def main(args: Array[String]): Unit = {
    val map: Map[String, String] = Map("a" -> "b")
    val val1: Option[String] = map.get("a")
    val val2: Option[String] = map.get("b")
    //Option有两个子类，一个是some，一个是none，有返回值时是some，没有返回值时是none
    println("val1:" + val1)
    println("val2:" + val2)


    println("=====使用匹配模式输出匹配值========")

    def show(x: Option[String]) = x match {
      case Some(s) => s
      case None => "?"
    }

    println(show(map.get("a")))
    println(show(map.get("b")))

    println("=====getOrElse()获取元组中存在的元素或者使用其默认值==========")
    val a: Option[Int] = Some(5)
    val b: Option[Int] = None
    println("a.getOrElse(0):" + a.getOrElse(2))
    println("b.getOrElse(0):" + b.getOrElse(6))
  }

}
