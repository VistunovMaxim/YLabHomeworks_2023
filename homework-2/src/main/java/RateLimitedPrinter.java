public class RateLimitedPrinter {
    int interval;
    long lastMessageTimeMillis = 0;

    public RateLimitedPrinter(int interval) {
        this.interval = interval;
    }

    public void print(String message) {
        if (System.currentTimeMillis() - interval >= lastMessageTimeMillis) {
            lastMessageTimeMillis = System.currentTimeMillis();
            System.out.print(message);
            System.out.print(" (currentTimeMillis - " + System.currentTimeMillis() + ")\n");
        }
    }
}