import com.gitHub.past.common.SysFun;
import com.gitHub.past.listTool.ListUtil;

import java.util.Arrays;
import java.util.List;

/**
 * 测试listUtil
 */
public class listTest {

    public static void main(String[] args) {
        List<Integer> a = Arrays.asList(1, 2, 3);
        List<Integer> b = Arrays.asList(3, 4, 5);

        SysFun.sysPrintln.accept(ListUtil.getListUtil(a, b, null, null).ajiaoJib());
        SysFun.sysPrintln.accept(ListUtil.getListUtil(a, b, null, null).bjiaoJia());

        SysFun.sysPrintln.accept(ListUtil.getListUtil(a, b, null, null).aJianb());
        SysFun.sysPrintln.accept(ListUtil.getListUtil(a, b, null, null).bJiana());

        List<String> sa = Arrays.asList("1", "2", "3");
        List<Integer> sb = Arrays.asList(3, 4, 5);

        SysFun.sysPrintln.accept(ListUtil.getListUtil(sa, sb, Integer::parseInt, null).ajiaoJib());
        SysFun.sysPrintln.accept(ListUtil.getListUtil(sa, sb, Integer::parseInt, null).bjiaoJia());

        SysFun.sysPrintln.accept(ListUtil.getListUtil(sa, sb, Integer::parseInt, null).aJianb());
        SysFun.sysPrintln.accept(ListUtil.getListUtil(sa, sb, Integer::parseInt, null).bJiana());

        List<String> sa1 = Arrays.asList("1", "2", "3");
        List<Integer> sb1 = Arrays.asList(3, 4, 5);

        SysFun.sysPrintln.accept(ListUtil.getListUtil(sb1, sa1, null,Integer::parseInt).ajiaoJib());
        SysFun.sysPrintln.accept(ListUtil.getListUtil(sb1, sa1, null,Integer::parseInt).bjiaoJia());

        SysFun.sysPrintln.accept(ListUtil.getListUtil(sb1, sa1, null,Integer::parseInt).aJianb());
        SysFun.sysPrintln.accept(ListUtil.getListUtil(sb1, sa1,  null,Integer::parseInt).bJiana());

        List<String> sa2 = Arrays.asList("1", "2", "3");
        List<String> sb2 = Arrays.asList("3", "4", "5");

        SysFun.sysPrintln.accept(ListUtil.getListUtil(sb2, sa2, Integer::parseInt,Integer::parseInt).ajiaoJib());
        SysFun.sysPrintln.accept(ListUtil.getListUtil(sb2, sa2, Integer::parseInt,Integer::parseInt).bjiaoJia());

        SysFun.sysPrintln.accept(ListUtil.getListUtil(sb2, sa2, Integer::parseInt,Integer::parseInt).aJianb());
        SysFun.sysPrintln.accept(ListUtil.getListUtil(sb2, sa2,  Integer::parseInt,Integer::parseInt).bJiana());
    }

}
