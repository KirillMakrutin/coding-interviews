import java.util.concurrent.TimeUnit;
import java.util.random.RandomGenerator;
import java.util.stream.IntStream;

public class ProducerConsumerTask {

  public static void main(String[] args) throws Exception {
    // Implement a message queue where a producer thread push integers from 0 to 9
    // but waits before pushing the next one until the consumer thread consumes the value and sleep randomly for 100-1000ms
    Topic topic = new Topic();
    Producer producer = new Producer();
    Consumer consumer = new Consumer();
    Thread producerThread = new Thread(() -> producer.produce(topic));
    Thread consumerThread = new Thread(() -> consumer.consume(topic));
    producerThread.start();
    consumerThread.start();
  }
}

class Producer {

  void produce(Topic destination) {
    IntStream.range(0, 10).forEach(next -> {
      destination.send(next);
      System.out.println("Sent " + next);
    });
  }
}

class Consumer {

  void consume(Topic source) {
    IntStream.range(0, 10).forEach(iteration -> {
      System.out.println("Received " + source.receive());
      SleepBetween.sleep(100, 1000);
    });
  }
}

class Topic {

  void send(Integer value) {
  }

  Integer receive() {
    return null;
  }
}

interface SleepBetween {

  static void sleep(int minMillis, int maxMillis) {
    try {
      TimeUnit.MILLISECONDS.sleep(RandomGenerator.getDefault().nextInt(minMillis, maxMillis));
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
