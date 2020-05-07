package demo2

/*
* @author: sunxiaoxiong
*/

class ExtendsTest(val xc: Int, val yc: Int) {
  var x: Int = xc
  var y: Int = yc

  def move(dx: Int, dy: Int): Unit = {
    x = x + dx
    y = y + dy
    println("x的坐标为：" + x)
    println("y的坐标为：" + y)
  }
}

class Location(override val xc: Int, override val yc: Int, val zc: Int) extends ExtendsTest(xc, yc) {
  var z: Int = zc

  def move(dx: Int, dy: Int, dz: Int): Unit = {
    x = x + dx
    y = y + dy
    z = z + dz
    println("x的坐标为：" + x)
    println("y的坐标为：" + y)
    println("z的坐标为：" + z)
  }
}

object Test2 {
  def main(args: Array[String]): Unit = {
    val loc = new Location(1, 2, 3)
    loc.move(1, 1, 1)
  }
}


class Person {
  var name = ""

  override def toString = getClass.getName + "名字：" + name
}

class Employee extends Person {
  var salary = 0.0

  override def toString = super.toString + "工资：" + salary
}

object Test3 {
  def main(args: Array[String]): Unit = {
    val employee = new Employee
    employee.name = "LISI"
    employee.salary = 222
    println(employee)
  }
}