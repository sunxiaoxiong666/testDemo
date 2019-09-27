package sheJiMoShi.danLiMoShi.demo;


//创建一个饿汉式单例类
public class SingleProject {

    //1.该类必须有自己的对象的实例化
    private static SingleProject singleProject = new SingleProject();

    //2.有自己私有的构造函数，不能为别的类调用
    private SingleProject() {
    }

    //3.获取唯一对象的方法，可供外界调用得到实例化的对象
    public static SingleProject getInstance() {
        return singleProject;
    }

    public void showMessage() {
        System.out.println("获取单例实例化对象。");
    }
}
