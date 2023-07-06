package com.namir.aatariak.cli;

import picocli.CommandLine;
import org.springframework.stereotype.Component;
import com.namir.aatariak.cli.subcommands.UserSubCommand;

@Component
@CommandLine.Command(
        name = "aatariak",
        version = "aatariak 1.0",
        mixinStandardHelpOptions = true,
        description = "A command line interface for Aatariak",
        subcommands = {
                UserSubCommand.class
        }
)
public class AatariakCommand {
        @CommandLine.Option(names = {"--usr"}, description = {"The username to log in as. If the argument is empty, it can be entered interactively."}, interactive = true, arity = "0..1", echo = true)
        String authUser;

        @CommandLine.Option(names = {"--pwd"}, description = {"The password for the given username. If the argument is empty, it can be entered interactively, but typed text will not show on the screen."}, interactive = true, arity = "0..1")
        String authPassword;

        @CommandLine.Option(names = {"--api-key"}, description = {"The API Key to use for submitting API requests. Use either this or a username with password."}, interactive = true, arity = "0..1")
        String apiKey;
}
