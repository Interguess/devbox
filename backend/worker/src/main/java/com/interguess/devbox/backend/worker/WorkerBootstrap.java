package com.interguess.devbox.backend.worker;

import console.ConsoleReader;

public class WorkerBootstrap {
    private ConsoleReader reader;

    public WorkerBootstrap(String[] args) {
        this.reader = new ConsoleReader();

        this.reader.getDispatcher().register(
                "echo",
                "This Command respond the inputed text",
                "echo <inputText>",
                (echoArgs) -> {
                    if(echoArgs.length == 0) return;
                    this.reader.println("[DevBox] Response from Worker: " + echoArgs[0]);
                }
        );

        this.reader.getDispatcher().register(
                "exit",
                "This Command exit Worker",
                "exit",
                (exitArgs) -> {
                    shutdown();
                }
        );

        this.reader.start();
        this.reader.println("[DevBox] Worker started");
    }

    private void shutdown() {
        this.reader.println("[DevBox] Worker is shutting down");
        this.reader.flush();
        this.reader.shutdown();
        System.exit(0);
    }

    public static void main(String[] args) {
        new WorkerBootstrap(args);
    }
}
