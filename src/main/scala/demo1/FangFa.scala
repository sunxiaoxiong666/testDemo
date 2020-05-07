package demo1

object FangFa {
  def main(args: Array[String]): Unit = {
    var aa: Int = test(2, 3);
    println(aa);
  }

  def test(a: Int, b: Int): Int = {
    var sum: Int = 0;
    sum = a + b;
    return sum;
  }
}
