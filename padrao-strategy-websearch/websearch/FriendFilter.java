/**
 * Estrategia concreta: interessa-se por consultas que contem a palavra
 * "friend", sem diferenciar maiusculas de minusculas.
 */
public class FriendFilter implements WebSearchModel.QueryFilter {
    private static final String WORD = "friend";
    

    @Override
    public boolean shouldNotify(String query) {
        return query.toLowerCase().contains(WORD);
    }
}
