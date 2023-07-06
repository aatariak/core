package com.namir.aatariak.cli.subcommands;

import com.namir.aatariak.sec.application.service.UserDataAccess;
import com.namir.aatariak.sec.domain.entity.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.List;

@CommandLine.Command(
        name = "users",
        description = "Manage the creation and retrieval of the users",
        mixinStandardHelpOptions = true
)
@Component
public class UserSubCommand {
    protected UserDataAccess userService;

    public UserSubCommand(
            UserDataAccess userService
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
            List<User> users = this.userService.getUsers();

            for (User user: users) {
                System.out.println(user.getName() + " - " + user.getEmail() + " - " + user.getRoles().toString());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
