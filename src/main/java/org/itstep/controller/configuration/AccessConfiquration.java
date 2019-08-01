package org.itstep.controller.configuration;

import org.itstep.model.entity.User;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccessConfiquration {
    private static Map<User.Role, List<String>> accessPages = new HashMap<>();
    static {
        init();
    }

    private static void init() {
        accessPages.put(User.Role.GUEST, Arrays.asList(
                "/mybank/",
                "/mybank/login",
                "/mybank/exception",
                "/mybank/",
                "/mybank/reg_form",
                "/mybank/login_form"
        ));
        accessPages.put(User.Role.USER, Arrays.asList(
                "/mybank/",
                "/mybank/logout",
                "/mybank/exception",
                "/mybank/user"
        ));
        accessPages.put(User.Role.ADMIN, Arrays.asList(
                "/mybank/",
                "/mybank/logout",
                "/mybank/exception"
        ));
    }

    public static boolean isAccessAllowed(String url, User.Role role){
        return url.contains(role.name().toLowerCase()) | url.equals("/mybank/")| url.equals("/mybank/home");

    }
}
