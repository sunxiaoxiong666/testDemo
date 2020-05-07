package demo2

/*
* @author: sunxiaoxiong
*/

/*
* 模式匹配：使用case关键字，=>符号将模式和表达式隔开
* */
object CaseTest {
  def main(args: Array[String]): Unit = {
    println(matchTest(3))
  }

  def matchTest(x: Int): String = x match {
    case 1 => "one"
    case 2 => "two"
    case _ => "others"
  }
}


/*
* 样例类：使用case关键字定义的类
* */
object CaseTest2 {
  def main(args: Array[String]): Unit = {
    val lisi = new Person("李四", 44)
    val wangwu = new Person("王五", 55)
    val zhaoliu = new Person("赵六", 66)

    for (person <- List(lisi, wangwu, zhaoliu)) {
      person match {
        case Person("李四", 44) => println("hi 李四")
        case Person("王五", 55) => println("hi 王五")
        case Person("赵六", 66) => println("hi 赵六")
      }
    }
  }

  //样例类
  case class Person(name: String, age: Int)

}
