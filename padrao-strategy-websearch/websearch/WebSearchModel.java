import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Perform "web search" (from a  file), notify the interested observers of each query.
 */
public class WebSearchModel {
    private final File sourceFile;
    // Cada observador fica guardado junto com o seu filtro (estrategia)
    private final List<FilteredObserver> observers = new ArrayList<>();

    public interface QueryObserver {
        void onQuery(String query);
        
    }

    /**
     * Strategy: politica de filtragem de consultas.
     * Retorna true se o observador deve ser notificado sobre a consulta,
     * false se ele nao tem interesse nela.
     * O modelo so conhece esta interface, nunca as implementacoes concretas.
     */
    public interface QueryFilter {
        boolean shouldNotify(String query);
    }

    public WebSearchModel(File sourceFile) {
        this.sourceFile = sourceFile;
    }

    public void pretendToSearch() {
        try (BufferedReader br = new BufferedReader(new FileReader(sourceFile))) {
            while ( true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                notifyAllObservers(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Registra um observador junto com o filtro que decide
     * quais consultas interessam a ele.
     */
    public void addQueryObserver(QueryObserver queryObserver, QueryFilter filter) {
        observers.add(new FilteredObserver(queryObserver, filter));
    }

    private void notifyAllObservers(String line) {
        for (FilteredObserver entry : observers) {
            // Pergunta a estrategia antes de notificar: o modelo nao sabe
            // COMO o filtro decide, so que ele implementa QueryFilter.
            if (entry.filter.shouldNotify(line)) {
                entry.observer.onQuery(line);
            }
        }
    }

    /** Par (observador, filtro) mantido internamente pelo modelo. */
    private static class FilteredObserver {
        private final QueryObserver observer;
        private final QueryFilter filter;

        FilteredObserver(QueryObserver observer, QueryFilter filter) {
            this.observer = observer;
            this.filter = filter;
        }
    }
}
