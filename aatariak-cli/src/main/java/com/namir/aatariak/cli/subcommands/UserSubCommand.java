package com.namir.aatariak.cli.subcommands;

import picocli.CommandLine;
import org.springframework.stereotype.Component;
import com.namir.aatariak.user.domain.entity.User;
import org.springframework.security.core.Authentication;
import com.namir.aatariak.user.application.service.UserService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@CommandLine.Command(
        name = "users",
        description = "Manage the creation and retrieval of the users",
        mixinStandardHelpOptions = true
)
@Component
public class UserSubCommand {
    protected UserService userService;

    public UserSubCommand(
            UserService userService
    )
    {
        this.userService = userService;
    }

    @CommandLine.Command(
        name = "get",
        description = "get all users",
        mixinStandardHelpOptions = true
    )
    public void get() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (
                auth == null
                || !auth.isAuthenticated()
                || !auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_Admin"))
        ) {
            throw new AccessDeniedException("Granted authority is not sufficient for this operation");
        }

        try {
            List<User> users = this.userService.loadAll();

            for (User user: users) {
                System.out.println(user.getName() + " - " + user.getEmail() + " - " + user.getRoles().toString());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
