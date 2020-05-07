package demo1

/*
* @author: sunxiaoxiong
*/

/*
*模式匹配
* */
object RegexTest {
  def main(args: Array[String]): Unit = {
    println(regTest(3));
    println("ss".isInstanceOf[String]);
  }

  def regTest(x: Int): String = x match {
    case 1 => "小花";
    case 2 => "小明";
    case _ => "小三";
  }
}

object RegTest {
  def main(args: Array[String]): Unit = {
    val alice = new Person("alice", 12);
    val tom = new Person("tom", 13);
    val xiaoming = new Person("xiaoming", 14);
    for (person <- List(alice, tom, xiaoming)) {
      person match {
        case Person("alice", 12) => println("hi,alice");
        case Person("tom", 13) => println("hi,tom");
        case Person(name, age) => println("age:" + age + "year,name:" + name);
      }
    }
  }

  //样例类
  case class Person(name: String, age: Int)

}
