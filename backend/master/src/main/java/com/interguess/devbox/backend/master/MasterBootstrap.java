package com.interguess.devbox.backend.master;

import console.ConsoleReader;

public class MasterBootstrap {
    private ConsoleReader reader;

    public MasterBootstrap(String[] args) {
        this.reader = new ConsoleReader();

        this.reader.getDispatcher().register(
                "echo",
                "This Command respond the inputed text",
                "echo <inputText>",
                (echoArgs) -> {
                    if(echoArgs.length == 0) return;
                    this.reader.println("[DevBox] Response from Master: " + echoArgs[0]);
                }
        );

        this.reader.getDispatcher().register(
                "exit",
                "This Command exit Master",
                "exit",
                (exitArgs) -> {
                    shutdown();
                }
        );

        this.reader.start();
        this.reader.println("[DevBox] Master started");
    }

    private void shutdown() {
        this.reader.println("[DevBox] Master is shutting down");
        this.reader.flush();
        this.reader.shutdown();
        System.exit(0);
    }

    public static void main(String[] args) {
        new MasterBootstrap(args);
    }
}
