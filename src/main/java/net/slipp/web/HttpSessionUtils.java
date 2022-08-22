package net.slipp.web;

import net.slipp.domain.Users;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "sessionedUser";

    public static boolean isLoginUser(HttpSession session) {
        Object sessionedUser = session.getAttribute(USER_SESSION_KEY);
        if(sessionedUser == null) {
            return false;
        }
        return true;
    }

    public static Users getUserFromSession(HttpSession session) {
        if (!isLoginUser(session)) {
            return null;
        }

        return (Users)session.getAttribute(USER_SESSION_KEY);
    }
}
