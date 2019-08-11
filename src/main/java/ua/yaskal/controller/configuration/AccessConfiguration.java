package ua.yaskal.controller.configuration;

import ua.yaskal.model.entity.User;



public class AccessConfiguration {

    public static boolean isAccessAllowed(String url, User.Role role){
        return url.contains("/"+role.getStringRole()+"/") | url.equals("/mybank/")| url.equals("/mybank/home");

    }
}
