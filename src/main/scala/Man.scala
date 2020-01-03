

/*
* @author: sunxiaoxiong
* @date  : Created in 2019/12/25 10:01
*/

class Man private(val sex: String, name: String) {
  def describe() = {
    println("sex:" + sex + ",name:" + name)
  }
}

object Man {
  def apply(name: String) = {
    var instance: Man = null
    if (instance == null) {
      instance = new Man("男", name)
    }
    instance
  }
}

object Hello {
  def main(args: Array[String]): Unit = {
    println("hello scala")
  }
}

object Hello2 extends App {
  if (args.length > 0) {
    println("hello" + args(0))
  } else {
    println("hello scala")
  }
}

object Color extends Enumeration {
  val Red = Value(0, "Stop")
  val Yellow = Value(1, "Slow")
  val Green = Value(2, "Go")
}

//带有特质的对象，动态混入
trait Logger {
  def log(msg: String)
}

trait ConsoleLogger extends Logger {
  def log(msg: String): Unit = {
    println(msg)
  }
}

class Account1 {
  protected var balance = 0.0
}

abstract class SavingCount1 extends Account1 with Logger {
  def withdraw(amount: Double): Unit = {
    if (amount > balance) log("余额不足")
    else balance -= amount
  }
}

object Main1 extends App {
  val acconut = new SavingCount1 with ConsoleLogger2
  acconut.withdraw(100)
}




