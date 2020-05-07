package demo1

object StringTest {

  def main(args: Array[String]): Unit = {
    var str: String = "abc";
    println(str);
    str = str + "dd";
    println(str);

    val str2 = new StringBuilder;
    str2 += 'a';
    str2 ++="bc";
    println(str2);
  }

}
