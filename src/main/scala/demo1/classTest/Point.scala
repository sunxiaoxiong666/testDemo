package demo1.classTest

/*
* 定义类时可以有参数，成为类参数
* */
class Point(val xc: Int, val yc: Int) {
  var x: Int = xc;
  var y: Int = yc;

  def move(dx: Int, dy: Int): Unit = {
    x = x + dx;
    y = y + dy;
    println("x的坐标点：" + x);
    println("y的坐标点：" + y);
  }
}

class Location(override val xc: Int, override val yc: Int, val zc: Int) extends Point(xc, yc) {
  var z = zc;

  def move(dx: Int, dy: Int, dz: Int): Unit = {
    x = x + dx;
    y = y + dy;
    z = z + dz;
    println("x的坐标：" + x);
    println("y的坐标：" + y);
    println("z的坐标：" + z);
  }
}

/*
* 一个scala源文件中可以有多个类
* */
object Test {
  def main(args: Array[String]): Unit = {
    //可以通过new类实例化对象，并可以访问其中的方法和变量
    var pt = new Point(6, 8);
    println(pt);
    pt.move(1, 1);

    var lt = new Location(1, 2, 3);
    lt.move(1, 1, 1);
  }
}

class Person {
  var name = "";

  override def toString = getClass.getName + "[name=" + name + "]"
}

class Em extends Person {
  var sal = 0.0;

  override def toString = super.toString + "[sal=" + sal + "]";
}

object Test2 extends App {
  val dd = new Em;
  dd.name = "xiaoming";
  dd.sal = 4000;
  println(dd);
}