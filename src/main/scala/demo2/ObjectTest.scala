package demo2

/*
* @author: sunxiaoxiong
*/

/*
* 单例对象实例
* */
/*class Test5(val xc: Int, val yc: Int) {
  var x: Int = xc
  var y: Int = yc

  def move(dx: Int, dy: Int): Unit = {
    x = x + dx
    y = y + dy
  }
}

object ObjectTest {
  def main(args: Array[String]): Unit = {
    val TEST = new Test5(1, 2);
    TEST.move(1, 1)
    print

    def print {
      println("x的坐标为：" + TEST.x)
      println("y的坐标为：" + TEST.y)
    }
  }
}*/


/*
* 伴生对象实例
* */
//私有构造方法
class Marker private(val color: String) {
  println("创建：" + this)

  override def toString: String = "颜色标记：" + color

}

//伴生对象，与类名字相同，可以访问类的私有属性和方法
object Marker {
  private val markers: Map[String, Marker] = Map(
    "red" -> new Marker("red"),
    "blue" -> new Marker("blue"),
    "green" -> new Marker("green"))

  def apply(color: String): Unit = {
    if (markers.contains(color)) markers(color) else null
  }

  def getMarker(color: String): Unit = {
    if (markers.contains(color)) markers(color) else null
  }

  def main(args: Array[String]): Unit = {
    val unit = Marker("red")
    println(Marker("red"))
    //单例函数调用，省略了.(点)符号
    println(Marker getMarker "blue")
  }

}
