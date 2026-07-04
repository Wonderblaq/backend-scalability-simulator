
public class Caching {
    static void main(String[] args){
        ECommerceSimu simu = new ECommerceSimu();
        // A user cart with 5 items (notice duplicates like iPhone and ThinkPad)
        String[] userCart = {"iPhone", "ThinkPad", "iPhone", "MacBook", "ThinkPad"};

        long startTime = System.currentTimeMillis();
        int totalCost = 0;

        System.out.println("🛒 Processing cart items with Smart Caching...\n");

        for (String item : userCart) {
            int price = simu.getPrice(item); // Calling our new smart method
            totalCost += price;
        }

        long endTime = System.currentTimeMillis();
        System.out.println("\n✅ Total Cart Cost: $" + totalCost);
        System.out.println("⏱️ Total Time Taken: " + (endTime - startTime) + " ms");

           }
}