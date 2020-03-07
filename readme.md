-v 0.0.2
添加web项目中常用的字符串转化 ’ “ < > 四个字符转换  以及中文转码解决方案


-v 0.0.1
--添加不需要通过if/else的代码形式来进行 代码流线型编程

DEMO

public class ConditionTest {
  @Test
  public void testSet(){
      Condition.empty().when(false).set("1").orWhenEquals("1").toDo(p -> System.out.println("1"));
      Condition.of(1).whenEquals(1).toDo(p -> System.out.println("2")).elseDo(System.out::print);
      Condition.of(1).whenEquals(2).setResult(1).elseWhen(p -> p > 0)
              .setResult(2).handleResult(result -> System.out.println(result));
      Condition.of("123").map(Integer::parseInt).whenEquals(123).setConditionValueAsResult().result();
      Condition.of(5)
              .when(t -> t % 2 == 0).toDo(t -> System.out.println(t + " is Even number"))
              .elseDo(t -> System.out.println(t + " is Odd number"));
 }

  @Test
  public void testMap(){
      Condition.of("123").whenEquals("123").map(Integer::parseInt).setResult("a").andWhenEquals(123);
  }
}