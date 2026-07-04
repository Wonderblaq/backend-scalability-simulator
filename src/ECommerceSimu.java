import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


/**
 * THE ULTIMATE BACKEND PRODUCTION ARCHITECTURE SIMULATOR
 * This space contains all 4 major constrains in backend development.
 */
public class ECommerceSimu {
   public static final Map<String, Integer> databasePrices = new HashMap<>();
    // Simulating the dangerous bug: A globally shared, static variable tracking the "current cart"
    // In production frameworks like Spring, shared Singleton beans cause this exact behavior!
    private static final List<String> sharedCart = new ArrayList<>();

    // NEW: Our fast, in-memory Cache (RAM)
    public static final Map<String, Integer> priceCache = new HashMap<>();
    static {
        databasePrices.put("IdeaPad", 2000);
        databasePrices.put("Thinkpad", 2500);
        databasePrices.put("Macbook", 3000);
        databasePrices.put("ExpertBook", 1500);
        databasePrices.put("Elitebook", 2500);
        databasePrices.put("Latitude", 2500);
    }
    // Simulating a heavy slow payment gateway check
    public static void makePayment(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {}
        System.out.println("💳 [BACKGROUND THREAD] Payment successfully captured via API!");
    }

    // Simulating a slow email server connection (1.5 seconds)
    public static void sendEmailReceipt() {
        try { Thread.sleep(1500); } catch (InterruptedException e) {}
        System.out.println("📧 [BACKGROUND THREAD] Email receipt sent to user's inbox!");
    }


    /* *
     * Simulates a direct, raw database query.
     * Every time this is called, it hits the disk/network, costing 100ms.

     * */
    public static int getDataBasePrice(String product){
        // simulate database latency
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return databasePrices.getOrDefault(product, 0);
    }
    /**
     * NEW: Intelligent fetching method.
     * Checks the fast RAM cache first. If it's missing, hits the slow DB and saves it.
     */
    public static int getPrice(String product) {
        // 1. Check if the price is already inside our fast RAM cache
        if (priceCache.containsKey(product)) {
            System.out.println("⚡ [CACHE HIT] Found " + product + " in RAM. Instant retrieval!");
            return priceCache.get(product);
        }

        // 2. Cache Miss: We don't have it, so we must pay the heavy 1-second DB penalty
        System.out.println("🐌 [CACHE MISS] " + product + " not in RAM. Fetching from database...");
        int priceFromDb = getDataBasePrice(product);
        priceCache.put(product, priceFromDb);
        return priceFromDb;

    }

}
