/**
 * Estrategia concreta: interessa-se por consultas com mais de
 * maxLength caracteres.
 */
public class LongQueryFilter implements WebSearchModel.QueryFilter {
    private final int maxLength;


    public LongQueryFilter(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public boolean shouldNotify(String query) {
        return query.length() > maxLength;
    }
}
