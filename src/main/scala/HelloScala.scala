import scala.collection.mutable.ArrayBuffer

/*
* @author: sunxiaoxiong
* @date  : Created in 2019/12/3 10:04
*/

object HelloScala {
  def main(args: Array[String]): Unit = {
    println("hello scala")
    val arr = Array(1, 2);
    val a4 = Array.apply(1, 3, 4);
    println(arr.mkString("+"))
    println("hellp" (4))
    "hello".apply(4)
    sum(1, 3)
    lazy val msg = init()
    println("lazy方法没有执行")
    println(msg)

    try {
      println(divid(1, 0))
    } catch {
      case e: Exception => println("捕获异常：" + e)
    } finally {}
  }

  def sum(args: Int*): Unit = {
    for (aeg <- args) {
      println(aeg)
    }
  }

  def init(): String = {
    println("init方法")
    return "返回值"
  }

  def divid(x: Int, y: Int): Float = {
    if (y == 0) throw new Exception("0作为了负数")
    else x / y
  }
}


object ArrTest {
  def main(args: Array[String]): Unit = {
    val arr = Array.ofDim[Double](3, 4)
    println(arr.toString)

    val arr2 = ArrayBuffer("1", "2", "3")
    import scala.collection.JavaConversions.bufferAsJavaList
    val java1 = new ProcessBuilder(arr2)
    println(java1.command())

    import scala.collection.JavaConversions.asScalaBuffer
    import scala.collection.mutable.Buffer
    val scalaArr: Buffer[String] = java1.command()
    println(scalaArr)

    val ss = () => 0
    println(ss)

    val list1 = List(1, 2, 3)
    val i = list1.foldRight(2)(_ - _)
    println(i + "==")

    val list2 = List(1, 9, 2, 8)
    val i4 = list2.fold(5)((sum, y) => sum + y)
    println(i4)

    //拉链
    val i5 = list1 zip list2
    println(i5)

    //匹配嵌套
    val sale = Bundle("愚人节大甩卖", 10,
      Artical("九阴真经", 40),
      Bundle("易筋经", 50),
      Artical("降龙十八掌", 60),
      Artical("打狗棒法", 70))
    val result = sale match {
      case Bundle(_, _, Artical(desc, _), _*) => desc
    }
    println(result)

    //偏函数
    val f: PartialFunction[Char, Int] = {
      case '+' => 1
      case '-' => -1
    }
    println(f('-'))
    println(f.isDefinedAt('0'))
    println(f('+'))
    //    println(f('0'))

    //闭包
    def minusxy(x: Int) = (y: Int) => x - y

    val f1 = minusxy(10)
    val f2 = minusxy(10)
    println(f1(3) + f2(3))

    //柯里化应用
    val a = Array("Hello", "World")
    val b = Array("hello", "world")
    println(a.corresponds(b)(_.equalsIgnoreCase(_)))

    //控制抽象
    def runInThread(f1: () => Unit): Unit = {
      new Thread {
        override def run() = {
          f1()
        }
      }.start()
    }

    runInThread {
      () =>
        println("干活")
        Thread.sleep(1000)
        println("干完了。")
    }
  }
}

object ClassTest {
  def main(args: Array[String]): Unit = {
    var dog = new Dog
    dog.setName("大黄")
    println(dog.getName)
    dog shout ("汪汪汪")
    println(dog currentLeg)
    dog.leg = 10
    println(dog.leg)

    //创建两个局域网
    val chatter1 = new NetWork
    val chatter2 = new NetWork

    //Fred 和 Wilma加入局域网1
    val fred = chatter1.join("Fred")
    val wilma = chatter1.join("Wilma")
    //Barney加入局域网2
    val barney = chatter2.join("Barney")

    //Fred将同属于局域网1中的Wilma添加为联系人
    fred.contacts += wilma
    //    fred.contacts += barney //这样做是不行的，Fred和Barney不属于同一个局域网，即，Fred和Barney不是同一个class Member实例化出来的对象


    //伴生类
    val man1 = Man("大黄")
    val man2 = Man("小黄")

    man1.describe
    man2.describe

    //枚举
    println(Color.Red)
    println(Color.Yellow)
    println(Color.Green)

    println("Hello".isInstanceOf[String])
    try {
      println("Hello".asInstanceOf[Int])
    } catch {
      case e: Exception => println("捕获异常：" + e)

    }
    println(classOf[String])

    //隐式转换
    implicit def a(d: Double) = d.toInt
    val i1: Int = 3.9
    println(i1)

  }
}


