package demo1

object ArrayTest {

  def main(args: Array[String]): Unit = {
    var arr = Array(1, 2, 3, 4, 5, 6);
    //遍历数组
    for (x <- arr) {
      println(x);
    }

    //计算数组元素的和
    var a = 0;
    for (x <- 0 to arr.length - 1) {
      a = a + arr(x);
    }
    println(a);

    //查找数组中最大的元素
    var max = 0;
    for (i <- 0 to arr.length - 2) {
      if (arr(i) < arr(i + 1)) {
        max = arr(i + 1);
      }
    }
    println(max);

    //
  }
}
