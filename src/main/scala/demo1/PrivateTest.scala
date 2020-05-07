package demo1

class PrivateTest {

  class outer {
    private def f() {
      println("sssss")
    }

    class inner {
      f();
    }

  }

  //  (new outer).f();

}
