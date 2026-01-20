package libs.query.impl;

import libs.query.Query;
import libs.query.QueryBus;
import libs.query.QueryHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@SuppressWarnings("unchecked")
public class SpringQueryBus implements QueryBus {

    private final Map<Class<?>, QueryHandler> handlers = new HashMap<>();

    public SpringQueryBus(List<QueryHandler> queryHandlers) {
        for (QueryHandler handler : queryHandlers) {
            Class<?> queryType = getQueryType(handler);
            handlers.put(queryType, handler);
        }
    }

    @Override
    public <R, Q extends Query<R>> R execute(Q query) {
        QueryHandler<Q, R> handler = handlers.get(query.getClass());
        if (handler == null) {
            throw new RuntimeException("No handler found for query: " + query.getClass().getName());
        }
        return handler.handle(query);
    }

    private Class<?> getQueryType(QueryHandler handler) {
        Type[] interfaces = handler.getClass().getGenericInterfaces();
        for (Type type : interfaces) {
            if (type instanceof ParameterizedType parameterizedType) {
                if (parameterizedType.getRawType().equals(QueryHandler.class)) {
                    return (Class<?>) parameterizedType.getActualTypeArguments()[0];
                }
            }
        }

        throw new RuntimeException("Query handler type not found: " + handler.getClass());
    }
}