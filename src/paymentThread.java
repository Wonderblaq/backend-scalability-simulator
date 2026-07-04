import java.util.concurrent.CompletableFuture;

public class paymentThread {
    public static void main(String[] args) throws InterruptedException{
        ECommerceSimu simu = new ECommerceSimu();
        long startTime = System.currentTimeMillis();
        System.out.println("🚀 User clicked 'Checkout'. Processing order asynchronously...\n");

        // 1. Critical Task: Instantly acknowledge the order on the main thread
        System.out.println("✅ [MAIN THREAD] Order ID #789 created in local DB.");

        // 2. Heavy Tasks: Kick them off asynchronously in the background!
        // This tells Java: "Run these blocks on separate background threads immediately."
        CompletableFuture.runAsync(()-> simu.makePayment());
        CompletableFuture.runAsync(() ->  simu.sendEmailReceipt());

        // 3. Complete the request immediately for the user
        long endTime = System.currentTimeMillis();
        System.out.println("\n🎉 [MAIN THREAD] Success screen shown to user!");
        System.out.println("⏱️ Response time experienced by user: " + (endTime - startTime) + " ms");

        System.out.println("\n⏳ (Keeping application alive briefly to watch background tasks finish...)\n");
        Thread.sleep(4000); // Just holding the terminal open so you see the background output
}
}
