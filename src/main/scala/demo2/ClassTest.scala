package demo2

/*
* @author: sunxiaoxiong
*/

/*
* 类是抽象的，不占用空间；对象是具体的，占用空间
* scala类定义可以有参数，成为类参数，在这个类中都可以被访问
*一个scala源文件中可以有多个class类
* */
class ClassTest(xc: Int, yc: Int) {
  var x: Int = xc
  var y: Int = yc

  def move(dx: Int, dy: Int): Unit = {
    x = x + dx
    y += dy
    println("x的坐标为：" + x)
    println("y的坐标为：" + y)
  }
}

object Test {
  def main(args: Array[String]): Unit = {
    //使用new生成类对象
    val test = new ClassTest(2, 3)
    test.move(1, 1)
  }
}
