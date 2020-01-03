import scala.beans.BeanProperty

/*
* @author: sunxiaoxiong
* @date  : Created in 2019/12/24 11:29
*/

class Dog {
  var leg = 4
  @BeanProperty var name: String = _

  def shout(content: String): Unit = {
    println(content)
  }

  def currentLeg = 4
}
