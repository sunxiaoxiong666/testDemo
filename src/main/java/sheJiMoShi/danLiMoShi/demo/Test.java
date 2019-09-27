package sheJiMoShi.danLiMoShi.demo;

//从SingleProject类获取唯一的对象
public class Test {

    public static void main(String[] args) {

        //此构造函数是SingleProject类私有的构造函数，不能调用，编译出错
        //SingleProject singleProject=new SingleProject();

        //获取对象
        SingleProject instance = SingleProject.getInstance();
        instance.showMessage();
    }
}
