package demo1.classTest

/*
* 单例对象实例
* */
class SingleTon(val xc: Int, val yc: Int) {
  var x: Int = xc;
  var y: Int = yc;

  def move(dx: Int, dy: Int): Unit = {
    x = x + dx;
    y = y + dy;
  }
}

object Test3 {
  def main(args: Array[String]): Unit = {
    val singleTon = new SingleTon(1, 2);
    printPoint

    def printPoint: Unit = {
      println("x的坐标为：" + singleTon.x);
      println("y的坐标为：" + singleTon.y);
    }
  }
}