import com.avaje.ebean.Ebean;

public class Application {

    public static void main(String[] args) {
        Ebean.beginTransaction();
        System.out.println(">>>");
        Ebean.commitTransaction();

        System.out.println("<<<");

        return;


    }
}
