package ua.yaskal.controller.configuration;

import ua.yaskal.model.entity.User;


/**
 * This configuration used to check if the user has access to the requested url.
 *
 * @author Nazar Yaskal
 */
public class AccessConfiguration {

    public static boolean isAccessAllowed(String url, User.Role role) {
        return url.contains("/" + role.getStringRole() + "/") | url.equals("/mybank/") | url.equals("/mybank/home");

    }
}
