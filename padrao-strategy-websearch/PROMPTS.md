# Uso de IA nesta questão

## Prompt utilizado

Ferramenta: Claude (Anthropic). Anexos: `Lista Avaliativa I.pdf` (enunciado) e `src.zip` (código inicial).

> Contexto: projeto Java com o pacote `websearch` em anexo (`WebSearchModel`, `Snooper`, `Main` e o arquivo
> `Hamlet.txt`). Hoje o `WebSearchModel` lê o arquivo linha a linha e notifica todos os observadores
> registrados, e o `Snooper` imprime todas as consultas.
>
> Objetivo: refatorar para o padrão Strategy, de forma que cada observador seja registrado junto com um
> objeto que define a política de filtragem das consultas, e que o modelo só notifique o observador quando
> essa política aceitar a consulta. Restrição de acoplamento: o modelo de busca não pode conhecer nenhuma
> implementação concreta de filtro, apenas a interface.
>
> Não me dê a solução pronta. Me dê um passo a passo (tutorial) incremental para eu implementar, seguindo
> estas condições:
> 1. cada etapa deve deixar o projeto compilando, para virar um commit isolado;
> 2. em cada etapa, diga quais arquivos mudam e qual é o papel de cada classe no padrão (contexto,
>    estratégia, estratégia concreta, cliente);
> 3. indique em que ponto do fluxo a estratégia deve ser consultada e por quê;
> 4. termine com uma etapa de verificação, comparando a saída do programa com o exemplo do enunciado
>    ("Oh Yes! ..." para consultas com 'friend', sem diferenciar maiúsculas, e "So long ..." para consultas
>    com mais de 60 caracteres).

## Passo a passo (tutorial) sugerido pela IA para esta questão

| Passo | O que fazer | Commit correspondente |
|---|---|---|
| 1 | Criar, **dentro do modelo de busca**, a interface `QueryFilter` com o método `boolean shouldNotify(String query)` (a *Strategy*). | `Passo 1` |
| 2 | Mudar o registro de observador para `addQueryObserver(QueryObserver, QueryFilter)` e guardar o par observador+filtro. | `Passo 2` |
| 3 | Na notificação, perguntar ao filtro de cada observador se ele quer aquela consulta antes de chamar `onQuery`. | `Passo 3` |
| 4 | No `Snooper`, registrar dois observadores: "Oh Yes!" (contém *friend*, sem diferenciar maiúsculas) e "So long" (mais de 60 caracteres). | `Passo 4` |
| 5 | Compilar, rodar e comparar com a saída de exemplo do enunciado. | `Ajuste` (commits seguintes) |

## Ajustes feitos sobre o que a IA sugeriu
Cada ajuste tem um commit próprio começando com **"Ajuste:"**, e a mensagem explica
(1) por que a sugestão original não servia totalmente e (2) por que o ajuste melhora a solução.
Resumo:

1. **Caminho do arquivo** — o `Main` procura `data/Hamlet.txt`, mas o arquivo veio na raiz. Ao rodar, dava
   `FileNotFoundException` e nenhuma saída. Movemos o arquivo para `data/` (em vez de mudar o código),
   pois é a estrutura que o próprio código inicial documenta.
2. **Filtros como classes nomeadas** — a primeira versão usava lambdas anônimas dentro do `Snooper`.
   Extraímos `FriendFilter` e `LongQueryFilter` para deixar explícito que são *estratégias concretas*
   intercambiáveis (facilita a arguição e o reuso), e o limite de 60 virou parâmetro.
