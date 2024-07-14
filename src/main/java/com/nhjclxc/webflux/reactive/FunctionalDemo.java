package com.nhjclxc.webflux.reactive;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author LuoXianchao
 * @since 2024/07/10 21:56
 */
public class FunctionalDemo {


    public static void main(String[] args) {

//        test1();
//        test2();
//        test3();
//        test4();
//        test5();

//        test6();


        IntStream.range(1, 100).parallel().peek(FunctionalDemo::print).count();


    }

    public static void print(int i){
        System.out.println(Thread.currentThread().getName() + " - " + i);
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void test6() {
        String str = "my name is Tom";

        System.out.println(Stream.of(str.split(" ")).map(e -> e.length()).collect(Collectors.toList()));
        Stream.of(str.split(" ")).map(String::length).forEach(System.out::println);

        Stream.of(str.split(" ")).flatMap(e -> e.chars().boxed()).forEach(System.out::println);
    }

    private static void test5() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Collections.shuffle(integers);
        System.out.println(integers);

        List<Integer> integers2 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Collections.shuffle(integers2, new Random(5));
        System.out.println(integers2);

        System.out.println(integers2.stream().reduce(0, Integer::sum));
    }

    private static void test4() {

        // 生产者接口

        Supplier<Integer> supplier = () -> new Random(10).nextInt();
        System.out.println(supplier.get());
        System.out.println(supplier.get());
        System.out.println(supplier.get());

        IntSupplier intSupplier = () ->  new Random(10).nextInt() + 100;
        System.out.println(intSupplier.getAsInt());
        System.out.println(intSupplier.getAsInt());
    }

    private static void test3() {

        // 数据转化接口Function<T, R>：输入一个数据T，返回一个数据R
        Function<Integer, String> function = (num) -> "实现数据转化：" + num;
        System.out.println(function.apply(666));
        System.out.println(function.apply(888));

        IntToDoubleFunction intToDoubleFunction = num -> num * 6;
        System.out.println(intToDoubleFunction.applyAsDouble(5));

    }

    private static void test2() {

        // 消费测试
        Consumer<Integer> integerConsumer = (num) -> System.out.println("消费一个数字：" + num);
        integerConsumer.accept(666);
        integerConsumer.accept(888);
        integerConsumer.accept(999);

        IntConsumer intConsumer = num -> System.out.println("消费一个数字2：" + num * num);
        intConsumer.accept(6);
        intConsumer.accept(8);
        intConsumer.accept(9);
    }

    private static void test1() {

        // 断言测试

        Predicate<Integer> integerPredicate = (num) -> num > 0;
        System.out.println(integerPredicate.test(-1));
        System.out.println(integerPredicate.test(0));
        System.out.println(integerPredicate.test(1));
        System.out.println();

        IntPredicate intPredicate = num -> num > 0;
        System.out.println(intPredicate.test(-1));
        System.out.println(intPredicate.test(0));
        System.out.println(intPredicate.test(1));
    }


}
