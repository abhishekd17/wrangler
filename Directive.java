import java.util.List;

public interface Directive {
    UsageDefinition define();

    void initialize(Arguments args);

    List<Row> execute(List<Row> rows, ExecutorContext context) throws DirectiveExecutionException;

    void destroy();
}