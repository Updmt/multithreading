import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Foo {
    Lock lock = new ReentrantLock();
    Condition conditionForFirst = lock.newCondition();
    Condition conditionForSecond = lock.newCondition();
    volatile boolean isFirstDone = false;
    volatile boolean isSecondDone = false;

    public void first(Runnable r) {
        lock.lock();
        try {
            System.out.print("first");
            isFirstDone = true;
            conditionForFirst.signal();
        } finally {
            lock.unlock();
        }
    }
    public void second(Runnable r) {
        lock.lock();
        try {
            while (!isFirstDone) {
                conditionForFirst.await();
            }
            System.out.print("second");
            isSecondDone = true;
            conditionForSecond.signal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
    public void third(Runnable r) {
        lock.lock();
        try {
            while (!isSecondDone) {
                conditionForSecond.await();
            }
            System.out.print("third");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
