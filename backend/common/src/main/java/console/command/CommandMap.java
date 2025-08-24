package console.command;

import java.util.HashMap;

public class CommandMap extends HashMap<String, Command> {

    public CommandMap() {
        super();
    }

    public void addCommand(Command command) {
        this.put(command.getName(), command);
    }

    public Command getCommand(String name) {
        return this.get(name);
    }

    public void removeCommand(String name) {
        this.remove(name);
    }
}
