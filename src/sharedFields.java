// Simulating the dangerous bug: A globally shared, static variable tracking the "current cart"
// In production frameworks like Spring, shared Singleton beans cause this exact behavior!
import java.util.List;
import java.util.ArrayList;


public class sharedFields {
    public static void main(String[] args) throws InterruptedException{
        ECommerceSimu simu = new ECommerceSimu();
        final List<String> sharedCart = new ArrayList<>();
        System.out.println("⚠️ Simulating Concurrent Users (The Shared State Glitch)...\n");

        Thread SarfoThread =  new Thread(() ->{
            sharedCart.clear();  // clear to start fresh
            sharedCart.add("Thinkpad");
            sharedCart.add("Macbook");

            try {
                Thread.sleep(1000); // simulate a latency or delay
            } catch (InterruptedException e) {
            }
            System.out.println("Sarfo looks at his UI,He sees his :" + sharedCart);

        });
        // Thread 2: Ama checks out or edits her cart at the exact same millisecond!
        Thread amaThread = new Thread(() -> {
            // Ama steps in while Sarfo is still processing and alters the shared state!
            sharedCart.add("MacBook");
            sharedCart.add("Lipstick");
            System.out.println("👩 Ama looks at her screen. She sees in her cart: " + sharedCart);
        });
        // Start both user requests simultaneously
        SarfoThread.start();
        // Ama hits the server right after Sarfo starts processing
        Thread.sleep(10);

        amaThread.start();
        SarfoThread.join();
        amaThread.join();

        /* * This program simulates how real world systems run into the problem whereby a java singleton bean
        * leads to state leakage
        * where concurrent user requests overites same memory space
        * */



    }
}

