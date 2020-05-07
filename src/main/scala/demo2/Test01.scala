package demo2

import java.io.IOException

import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import scala.util.Random

/*
* @author: sunxiaoxiong
* @date  : Created in 2019/11/20 17:16
*/

object Test01 {
  def main(args: Array[String]): Unit = {
    for (i <- 1 to 3; j <- 1 to 3 if i != j) {
      println(i * 10 + j)
    }

    val a = for (i <- 1 to 3) yield i * 10
    for (x <- a) {
      println(x + "=")
    }
    println(a)

    var bb = aa(1, 2);
    println(bb)

    val ad = add(f1);
    println(ad + "===")

  }

  def add(ff: (Int, Int) => Int): Int = {
    ff(2, 3)
  }

  val f1 = (x: Int, y: Int) => x + y

  def aa(ab: Int, B: Int): Int = {
    return ab + B;
  }

}

object Test02 {
  def main(args: Array[String]): Unit = {
    val arr1 = new Array[Int](6);
    println(arr1)
    println(arr1.toBuffer)

    val arr2 = Array[Int](5)
    println(arr2.toBuffer)

    var arr3 = Array("hadoop", "hive", "spark")
    arr3 ++= Array("hbase")
    println(arr3(0))
    println(arr3.toBuffer)

    val arr4 = ArrayBuffer[Int]()
    arr4 += 1
    arr4 += (2, 3, 4)
    arr4 ++= Array(5, 6)
    arr4.insert(1, 7, 8)
    arr4.remove(1, 2)
    println(arr4)
    for (x <- arr4) {
      println(x)
    }

    val arr5 = for (x <- arr4 if x % 2 == 0) yield x * 10
    val arr6 = arr4.filter(_ % 2 == 0).map(_ * 10)

    println(arr5)
    println(arr6)

    val arr7 = Array(3, 5, 1, 2, 7, 9)
    println(arr7.sum)
    println(arr7.max)
    println(arr7.min)
    println(arr7.sorted.toBuffer)
  }
}

object Test03 {
  def main(args: Array[String]): Unit = {
    val aa = Map("hadoop" -> 1, "hive" -> 2, "spark" -> 3, "storm" -> 4)
    println(aa("spark"))
    println(aa.getOrElse("spark3", 8))

    //元组
    val bb = ("aaa", 1, 2.1)
    println(bb._3)
  }

  val list1 = List(1, 2, 3)
  val list2 = 0 :: list1;
  val list3 = list1.::(0);
  val list4 = 0 +: list1;
  val list5 = list1.+:(0);
  val list6 = list1 :+ 4;
  println(list6)
  val list7 = list1 :+ 4;
  println(list7)
  val list8 = list6 ++ list7
  println(list8)

  val lst0 = ListBuffer[Int](1, 2, 3);

  val lst1 = new ListBuffer[Int]
  lst1 += 4;
  println("lst1=" + lst1)
  lst1.append(5)
  println("lst1=" + lst1)
}

object Test04 {
  def main(args: Array[String]): Unit = {
    val set1 = new mutable.HashSet[Int]()
    val set2 = set1 + 4
    println(set1)
  }
}

class Student(val name: String, val age: Int) {
  println("执行主构造器")

  try {
    println("读取文件")
    throw new IOException(" io exception");
  } catch {
    case e: NullPointerException => println("打印异常：" + e)
    case e: IOException => println("打印异常：" + e)

  } finally {
    println("执行finally部分")
  }

  private var sex = "mela"

  def this(name: String, age: Int, sex: String) {
    this(name, age)
    println("执行辅助构造器")
    this.sex = sex
  }
}

class Quee private(val name: String, prop: Array[String], private var age: Int = 18) {
  println(prop.size)

  def des = name + "is" + age + "years " + prop.toBuffer
}

object Quee {
  def main(args: Array[String]): Unit = {
    val q = new Quee("hha", Array("bb", "cc"), 20)
    println(q.des)

    new Student("小明", 8)
  }
}

object SingleTon {
  def main(args: Array[String]): Unit = {
    val session = SessionFactory.getSession()
    println(session.toString)
  }
}

object SessionFactory {
  var counts = 5
  val sessions = new ArrayBuffer[Session]()
  while (counts > 0) {
    sessions += new Session
    counts -= 1
  }

  def getSession(): Session = {
    sessions.remove(0)
  }
}

class Session {

}

class Dog {
  val id = 1
  private var name = "itcast"

  def printName(): Unit = {
    println(name)
    println(Dog.CONSTANT + name)
  }
}

object Dog {
  private val CONSTANT = "汪汪汪:"

  def main(args: Array[String]): Unit = {
    val p = new Dog
    //    p.name = "123"
    p.printName()
  }
}


object ClazzDemo extends App {
  val arr = Array("aa", "bb", "cc")
  val name = arr(Random.nextInt(arr.length))
  name match {
    case "aa" => println("AA")
    case "bb" => println("BB")
    case _ => println("CC")
  }
}






