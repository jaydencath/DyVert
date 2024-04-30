package com.DyVert.DyVert;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AppController {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;
    
    @Autowired
    public AppController(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/403")
    public String _403() {
        return "403";
    }
    
    @GetMapping("/home")
    public String redirectToHomePageAfterLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("Admin"))) {
            return "redirect:/admin/"+auth.getName()+"/all-unreviewed";
        } else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("User"))) {
            return "redirect:/user/" + auth.getName() + "/home/all";
        } else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("Creator")))
            return "redirect:/creator/" + auth.getName() + "/create";
        }
        return "redirect:/";
    }
    
    // Add POST mapping for /home
    @PostMapping("/home")
    public String handleLoginPost() {
        // Redirect to GET /home
        return "redirect:/home";
    }
    
    @PostMapping("/sn")
    public String handleSignUpPost(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("accountType") String accountType) {
        String sql = "INSERT INTO users (accountID, account_type, password) VALUES (:username, :accountType, :password)";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("username", username, java.sql.Types.VARCHAR);
        parameters.addValue("accountType", accountType, java.sql.Types.VARCHAR);
        parameters.addValue("password", password, java.sql.Types.VARCHAR);

        jdbcTemplate.update(sql, parameters);

        return "login";
    }
}
