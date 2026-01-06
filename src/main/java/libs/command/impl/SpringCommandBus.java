package libs.command.impl;

import libs.command.Command;
import libs.command.CommandBus;
import libs.command.CommandHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SpringCommandBus implements CommandBus {
    private final Map<Class<?>, CommandHandler> handlers = new HashMap<>();

    public SpringCommandBus(List<CommandHandler> commandHandlers) {
        for (CommandHandler handler : commandHandlers) {
            Class<?> commandType = getCommandType(handler);
            handlers.put(commandType, handler);
        }
    }

    @Override
    public <R, C extends Command<R>> R execute(C command) {
        CommandHandler<C, R> handler = handlers.get(command.getClass());
        if (handler == null) {
            throw new RuntimeException("No handler found for " + command.getClass().getName());
        }
        return handler.handle(command);
    }

    private Class<?> getCommandType(CommandHandler handler) {
        Type[] interfaces = handler.getClass().getGenericInterfaces();
        for (Type type : interfaces) {
            if (type instanceof ParameterizedType parameterizedType) {
                // Check if this interface is CommandHandler
                if (parameterizedType.getRawType().equals(CommandHandler.class)) {
                    // The first generic argument is the Command type
                    return (Class<?>) parameterizedType.getActualTypeArguments()[0];
                }
            }
        }

        throw new RuntimeException("Could not find command type for handler: " + handler.getClass());
    }
}
