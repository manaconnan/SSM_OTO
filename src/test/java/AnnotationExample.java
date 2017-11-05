import org.junit.Test;

import javax.swing.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AnnotationExample {


    private SpringConfig springConfig = new SpringConfig();

    @Test
    public void testAsyncAnnotationForMethodsWithReturnType()
            throws InterruptedException, ExecutionException {
        System.out.println("Invoking an asynchronous method. "
                + Thread.currentThread().getName());
        Future<String> future = springConfig.asyncMethodWithReturnType();

        while (true) {  ///这里使用了循环判断，等待获取结果信息
            if (future.isDone()) {  //判断是否执行完毕
                System.out.println("Result from asynchronous process - " + future.get());
                break;
            }
            System.out.println("Continue doing something else. ");
            Thread.sleep(1000);
        }
    }
}
