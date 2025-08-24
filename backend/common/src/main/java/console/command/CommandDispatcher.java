package console.command;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;

public class CommandDispatcher {
    private final CommandMap commandMap;

    public CommandDispatcher() {
        this.commandMap = new CommandMap();
    }

    public void register(String name, String desc, String usage, Consumer<String[]> command) {
        this.commandMap.put(name.toLowerCase(), new Command() {
            @Override
            public void execute(String[] args) {
                command.accept(args);
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public String getDescription() {
                return desc;
            }

            @Override
            public String getUsage() {
                return usage;
            }
        });
    }

    public Collection<String> getCommandNames() {
        return this.commandMap.keySet();
    }

    public void register(Command command) {
        this.commandMap.put(command.getName().toLowerCase(), command);
    }

    public void execute(String input) {
        String[] split = input.trim().split("\\s+");
        if (split.length == 0) return;

        String cmd = split[0].toLowerCase();
        String[] args = Arrays.copyOfRange(split, 1, split.length);

        Command command = this.commandMap.get(cmd);
        if (command != null) {
            command.execute(args);
        } else {
            System.out.println("Unknown Command: " + cmd);
        }
    }
}
