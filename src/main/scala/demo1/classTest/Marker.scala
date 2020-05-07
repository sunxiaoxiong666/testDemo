package demo1.classTest

//私有构造方法
class Marker private(val color: String) {
  println("创建" + this);

  override def toString: String = "颜色标记：" + color;
}

//伴生对象，与类有相同的名字，可以访问类的属性和方法
object Marker {
  private val markers: Map[String, Marker] = Map(
    "red" -> new Marker("red"),
    "blue" -> new Marker("blue"),
    "green" -> new Marker("green")
  )

  def apply(color: String): Unit = {
    if (markers.contains(color)) markers(color) else null;
  }

  def getMarker(color: String) = {
    if (markers.contains(color)) markers(color) else null;
  }

  def main(args: Array[String]): Unit = {
    //println(Marker("red"));
    //单例函数调用，省略了.(点)号
    println(Marker getMarker ("blue"));
  }
}
