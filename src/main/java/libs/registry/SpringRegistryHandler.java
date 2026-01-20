package libs.registry;

import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.core.GenericTypeResolver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpringRegistryHandler<M, H> {

    private final Map<Class<?>, H> registry = new HashMap<>();
    private final Class<H> handlerInterface;
    private final String handlerName;

    public SpringRegistryHandler(List<H> handlers, Class<H> handlerInterface, String handlerName) {
        this.handlerInterface = handlerInterface;
        this.handlerName = handlerName;

        for (H handler : handlers) {
            Class<?> messageType = resolveMessageType(handler);
            if (registry.containsKey(messageType)) {
                throw new IllegalStateException("Duplicate handler found for " + handlerName + ": " + messageType.getName());
            }
            registry.put(messageType, handler);
        }
    }

    public H getHandler(Class<?> messageClass) {
        H handler = registry.get(messageClass);
        if (handler == null) {
            throw new RuntimeException("No " + handlerName + " Handler found for: " + messageClass.getName());
        }
        return handler;
    }

    private Class<?> resolveMessageType(H handler) {
        Class<?> handlerClass = AopProxyUtils.ultimateTargetClass(handler);
        Class<?>[] typeArgs = GenericTypeResolver.resolveTypeArguments(handlerClass, handlerInterface);

        if (typeArgs == null) {
            throw new IllegalStateException(
                    "Could not resolve generic types for " + handlerName + " Handler: " + handlerClass.getName() +
                            ". Ensure it implements " + handlerInterface.getSimpleName() + "<MessageType, ...>"
            );
        }

        return typeArgs[0];
    }
}