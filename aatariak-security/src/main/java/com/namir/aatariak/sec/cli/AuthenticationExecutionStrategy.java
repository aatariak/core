package com.namir.aatariak.sec.cli;

import picocli.CommandLine;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import com.namir.aatariak.sec.ApiKeyAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.HashSet;

@Component
public class AuthenticationExecutionStrategy implements CommandLine.IExecutionStrategy {

    private AuthenticationManager authenticationManager;

    public AuthenticationExecutionStrategy(
            AuthenticationManager authenticationManager
    )
    {
        this.authenticationManager = authenticationManager;
    }
    @Override
    public int execute(CommandLine.ParseResult parseResult) throws CommandLine.ExecutionException, CommandLine.ParameterException {
        CommandLine.Model.OptionSpec usernameOption = parseResult.matchedOption("--usr");
        CommandLine.Model.OptionSpec passwordOption = parseResult.matchedOption("--pwd");
        CommandLine.Model.OptionSpec apiOption = parseResult.matchedOption("--api-key");

        String username = usernameOption != null ? usernameOption.getValue() : null;
        String password = passwordOption != null ? passwordOption.getValue(): null;
        String apiKey = apiOption != null ? apiOption.getValue() : null;

        Authentication authentication = null;
        if (username != null && !username.isBlank() && password != null && !password.isBlank()) {
            authentication = new UsernamePasswordAuthenticationToken(username, password);
        } else if (apiKey != null && !apiKey.isBlank()) {
            authentication = new ApiKeyAuthenticationToken(apiKey);
        } else {
            HashSet<GrantedAuthority> authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_Anyone"));
            authentication = new AnonymousAuthenticationToken("Aatariak", "anonymous", authorities);
        }

        Authentication authenticated = authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authenticated);

        return 0;
    }
}
