package libs.command;

public interface CommandBus {
    <R, C extends Command<R>> R execute(C command);
}