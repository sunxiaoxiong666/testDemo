package demo2

/*
* @author: sunxiaoxiong
*/

object IfTest {
  def main(args: Array[String]): Unit = {
    val x = 1;
    val y = if (x > 1) 1 else -1;
    println(y)
    //支持混合类型的表达式
    val y2 = if (x > 1) 1 else "ok"
    println(y2)
    //如果缺少else,相当于if(x>1) 1 else()
    val y3 = if (x > 1) 1
    println(y3)
    //if和else if
    val y4 = if (x >= 1) 1 else if (x < 1) 0 else -1
    println(y4)

  }

}
