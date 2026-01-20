package libs.query;

public interface QueryBus {
    <R, Q extends Query<R>> R execute(Q query);
}