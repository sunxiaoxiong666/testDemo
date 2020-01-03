

/*
* @author: sunxiaoxiong
* @date  : Created in 2019/12/27 17:06
*/

trait Logger2 {
  def log(msg: String);
}

trait ConsoleLogger2 extends Logger2 {
  def log(msg: String) {
    println(msg)
  }
}

trait TimestampLogger2 extends ConsoleLogger2 {
  override def log(msg: String) {
    super.log(new java.util.Date() + " " + msg)
  }
}

trait ShortLogger2 extends ConsoleLogger2 {
  override def log(msg: String) {
    super.log(if (msg.length <= 15) msg else s"${msg.substring(0, 12)}...")
  }
}

class Account2 {
  protected var balance = 0.0
}

abstract class SavingsAccount2 extends Account2 with Logger2 {
  def withdraw(amount: Double) {
    if (amount > balance) log("余额不足")
    else balance -= amount
  }
}

object Main2 extends App {
  /*val acct1 = new SavingsAccount2 with TimestampLogger2 with ShortLogger2
  acct1.withdraw(100)*/
  val acct2 = new SavingsAccount2 with ShortLogger2 with TimestampLogger2
  acct2.withdraw(100)
}

