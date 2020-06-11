import java.util.Random;

public class FixedRateFixedProducer implements Producer {
    private double value = 0;

    private Thread thread;

    boolean f=true;

    public void runProducing() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (f == true) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    value += value*4;
                    System.out.println("value = " + value);
                    if (value > 100) {
                        value = 100;
                        f = false;
                    }
                }

            }
        });
        thread.start();
    }

    public double getPrice(int boughtValue) {
        if (value >= boughtValue)
            return (100-value)*2;
        else
            return -1;
    }

    public void Buy(int boughtValue) {
        this.value -= boughtValue;

    }
}
