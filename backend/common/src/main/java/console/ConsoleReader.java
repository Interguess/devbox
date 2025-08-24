package console;

import console.command.CommandDispatcher;
import lombok.Getter;
import lombok.Setter;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

@Setter
@Getter
public class ConsoleReader extends Thread {
    private CommandDispatcher dispatcher;
    private String prompt;
    private Terminal terminal;
    private LineReader reader;

    public ConsoleReader() {
        try {
            this.setName("ConsoleReader");
            this.prompt = "devbox>";
            this.dispatcher = new CommandDispatcher();
            this.terminal = TerminalBuilder.builder()
                    .system(true)
                    .dumb(true)
                    .build();
            this.reader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void flush() {
        this.terminal.flush();
    }

    public void println(String msg) {
        this.terminal.writer().println(msg);
    }

    public void print(String msg) {
        this.terminal.writer().print(msg);
    }

    public ConsoleReader(String prompt) {
        try {
            this.setName("ConsoleReader");
            this.prompt = prompt;
            this.dispatcher = new CommandDispatcher();
            this.terminal = TerminalBuilder.builder()
                    .system(true)
                    .build();
            this.reader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .completer(new StringsCompleter(this.dispatcher.getCommandNames()))
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            try {
                String line = reader.readLine(this.prompt);
                dispatcher.execute(line);
            } catch (UserInterruptException | EndOfFileException e) {
                break;
            }
        }
    }

    public void shutdown() {
        this.interrupt();
    }
}
