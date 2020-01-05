import scala.collection.mutable.ArrayBuffer

/*
* @author: sunxiaoxiong
* @date  : Created in 2019/12/24 17:33
*/

class NetWork {

  class Member(val name: String) {
    val contacts = new ArrayBuffer[Member]
  }

  private val members = new ArrayBuffer[Member]

  def join(name: String) = {
    val m = new Member(name)
    members += m
    m
  }

}
