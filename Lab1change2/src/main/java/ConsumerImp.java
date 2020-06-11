import java.util.ArrayList;
import java.util.List;

public class ConsumerImp implements Consumer{

    private List<Producer> producers= new ArrayList<>();
    private Thread t;

    public void addProducer (Producer p) {
        producers.add(p);

    }
    public void startConsuming () {
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(16000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    double bestPrice = Double.MAX_VALUE;
                    Producer bestProducer = null;
                    for (Producer producer : producers) {
                        Double price = producer.getPrice(50);
                        if (bestPrice > price && price != -1) {
                            bestProducer = producer;
                            bestPrice = price;
                        }
                    }
                    if (bestProducer != null) {
                        bestProducer.Buy(50);
                        System.out.println("Value has been bought successfully");

                    } else {
                        System.out.println("Value can not be consumed");
                    }
                }

            }

        });
        t.start();
    }
}
