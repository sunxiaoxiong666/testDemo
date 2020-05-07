package demo1

trait TraitTest {

  def isEqual(x: Any): Boolean;

  def isNotEqual(x: Any): Boolean = !isEqual(x);
}

class Point(xc: Int, yc: Int) extends TraitTest {
  var x: Int = xc;
  var y: Int = yc;

  override def isEqual(obj: Any): Boolean = obj.isInstanceOf[Point] && obj.asInstanceOf[Point].x == x
}

object Test {
  def main(args: Array[String]): Unit = {
    var p1 = new Point(1, 2);
    var p2 = new Point(1, 2);
    var p3 = new Point(1, 2);

    println(p1.isNotEqual(p2));
    println(p1.isNotEqual(p3));
    println(p1.isNotEqual(2));
  }
}
