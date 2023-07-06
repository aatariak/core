package com.namir.aatariak.cli;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.*;

import java.util.Optional;

@Component
public class AatariakCommandLineRunner implements CommandLineRunner, ExitCodeGenerator {
    private final IFactory factory;

    private final AatariakCommand command;

    private final Optional<IExecutionStrategy> executionStrategy;

    private int exitCode;

    public AatariakCommandLineRunner(
            IFactory factory,
            AatariakCommand command,
            // pluggable execution strategy (will be created in security module)
            Optional<IExecutionStrategy> executionStrategy
    )
    {
        this.command = command;
        this.factory = factory;
        this.executionStrategy = executionStrategy;
    }
    @Override
    public void run(String... args) throws Exception {
        try {
            exitCode = new CommandLine(this.command, factory)
                    .setExecutionStrategy(it -> {
                        if (executionStrategy.isPresent()) {
                            int result = executionStrategy.get().execute(it);
                            if (result != 0) {
                                return result;
                            }
                        }
                        return new CommandLine.RunLast().execute(it);
                    })
                    .setExecutionExceptionHandler((ex, cmd, parseResult) -> {
                        cmd.getErr().println(ex.getMessage());
                        return -1;
                    })
                    .setUsageHelpAutoWidth(true)
                    .execute(args);
            System.exit(exitCode);
        } catch (PicocliException e) {
            System.err.println(e.getMessage());
            exitCode = -1;
        } catch (Exception e) {
            System.err.println("A system error has occurred!");
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            exitCode = -9;
        }
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }
}
