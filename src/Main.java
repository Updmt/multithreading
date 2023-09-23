public class Main {
    public static void main(String[] args) {
        Foo foo = new Foo();
        Thread threadA = new Thread(() -> foo.first(() -> {}));
        Thread threadB = new Thread(() -> foo.second(() -> {}));
        Thread threadC = new Thread(() -> foo.third(() -> {}));

        threadA.start();
        threadB.start();
        threadC.start();
    }
}

