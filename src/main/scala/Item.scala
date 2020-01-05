

/*
* @author: sunxiaoxiong
* @date  : Created in 2019/12/16 15:47
*/

abstract class Item

case class Artical(desc: String, price: Double) extends Item

case class Bundle(desc: String, discount: Double, item: Item*) extends Item



