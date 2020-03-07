import com.gitHub.past.Invariable;
import com.gitHub.past.characterEncoding.CharEncoding;


public class testMain {
    public static void main(String[] args) {
        System.out.println(Invariable.UTF_8);
        System.out.println(Invariable.UTF_8.name());
        System.out.println(Invariable.UTF_8.ordinal());
        System.out.println(Invariable.UTF_8.toString());
        System.out.println(Invariable.UTF_8.toString() == "UTF-8");


        System.out.println(CharEncoding.codeing.apply("%3A%2F",Invariable.UTF_8));
//                    .decode("&#39;&#34;&lt;&gt;",Invariable.UTF_8.toString());
    }
}
