package demo1

object MapList {
  def main(args: Array[String]): Unit = {
    var aa = Map("a" -> "aa", "b" -> "bb");
    var bb = Map();
    println("返回所有的key" + aa.keys);
    println("返回所有的value" + aa.values);
    println("map集合是否为空" + aa.isEmpty + bb.isEmpty);

    var cc = Map("c" -> "cc", "d" -> "dd");
    //合并set集合
    var dd = aa ++ cc;
    println(dd);
    var ddd = aa.++(cc);
    println(ddd);

    //使用foreach循环输出key和value
    dd.keys.foreach { i =>
      print("key=" + i+" ")
      println("value=" + dd(i))
    }
  }

}
