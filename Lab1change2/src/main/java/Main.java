public class Main {
    public static void main(String[] args) {
        FixedRateFixedProducer p = new FixedRateFixedProducer();
        p.runProducing();
        Consumer consumer = new ConsumerImp();
        consumer.addProducer(p);
        consumer.startConsuming();

    }
}
