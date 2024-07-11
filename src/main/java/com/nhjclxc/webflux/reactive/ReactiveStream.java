package com.nhjclxc.webflux.reactive;


import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 *
 * 被压 back press
 */
public class ReactiveStream {

    public static void main(String[] args) throws InterruptedException {
        // 1. 定义发布者
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();

        // 2. 定义订阅者
        Flow.Subscriber<String> subscriber = new Flow.Subscriber<>() {
            private Flow.Subscription subscription;
            private int count = 0;
            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                // 订阅成功，一般用作保存订阅这对象
                this.subscription = subscription;
                // 订阅成功之后向发布者请求一条数据
                this.subscription.request(1);
                count++;
            }
            @Override
            public void onNext(String item) {
                // 处理数据
                System.out.println("数据处理完毕：" + item);
                // 本条数据处理完毕之后再次向发布者请求一条数据
                this.subscription.request(1);
                count++;
                if (count > 5){
                    throw new RuntimeException("消费次数已达上限，请开题VIP");
                }

            }
            @Override
            public void onError(Throwable throwable) {
                // onNext数据处理过程中发生异常会跳到这里，类似于全局异常处理
                System.out.println("处理异常" + throwable.getMessage());
                throwable.printStackTrace();
            }
            @Override
            public void onComplete() {
                // 当发布者被关闭的时候【publisher.close()】，本订阅者会调用这个完成方法
                System.out.println("发布者被关闭，本订阅者所有数据处理完毕");
            }
        };

        // 3. 订阅者订阅发布者
        publisher.subscribe(subscriber);

        // 4. 发布者发布数据
        publisher.submit("数据1");
        publisher.submit("数据2");
        publisher.submit("数据3");
        publisher.submit("数据4");
        publisher.submit("数据5");
        publisher.submit("数据6");

        // 5.发布者数据都发生完毕，关闭发布者，这时会调用订阅者的subscriber.onComplete方法
        publisher.close();


        // 等待发布订阅关系处理数据完毕后关闭程序
        Thread.currentThread().join(1000);

    }
}
