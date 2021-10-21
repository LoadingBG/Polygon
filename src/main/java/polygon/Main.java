package polygon;

public final class Main {
    public static void main(final String[] args) {
        final Bot bot = new Bot("ODk5MzEyOTcyNzU1Nzg3Nzk2.YWw8eQ.ye0-RwvRqcXnlh-z_AInC3hN01s");
        Runtime.getRuntime().addShutdownHook(new Thread(bot::shutdown));
    }
}
