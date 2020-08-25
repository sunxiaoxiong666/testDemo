package demo2

/*
* @author: sunxiaoxiong
*/
/*
* 定义方法使用def
* 函数可以像任何其他数据类型一样被传递和操作
* */

object MethodTest {
  //定义个方法，参数必须是函数，函数的参数是两个Int类型，返回值类型也是Int类型
  def m1(f: (Int, Int) => Int)={
    f(2, 6)
  }

  //定义函数f1，参数为两个Int类型，返回值为Int类型
  val f1 = (x: Int, y: Int) => x + y
  //在定义一个函数f2
  val f2 = (x: Int, y: Int) => x * y

  def main(args: Array[String]): Unit = {
    //调用m1方法，传入f1函数
    val r1 = m1(f1)
    println(r1)
    //调用m1方法，传入f2函数
    val r2 = m1(f2)
    println(r2)
  }
}
