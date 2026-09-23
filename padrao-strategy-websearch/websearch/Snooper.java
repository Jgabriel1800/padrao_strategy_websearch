/**
 * Watches the search queries
 */
public class Snooper {
    private static final int LONG_QUERY_LIMIT = 60;

    private final WebSearchModel model;

    public Snooper(WebSearchModel model) {
        this.model = model;

        // Observador 1 e estrategia FriendFilter
        model.addQueryObserver(
                query -> System.out.println("Oh Yes! " + query),
                new FriendFilter());

        // Observador 2 e estrategia LongQueryFilter (> 60 caracteres)
        model.addQueryObserver(
                query -> System.out.println("So long " + query),
                new LongQueryFilter(LONG_QUERY_LIMIT));
    }
}
