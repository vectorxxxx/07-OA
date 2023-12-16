package xyz.funnyboy.security.custom;

/**
 * @author VectorX
 * @version V1.0
 * @description 获取当前用户信息帮助类
 * @date 16/12/2023
 */
public class LoginUserInfoHelper
{
    private static ThreadLocal<Long> userId = new ThreadLocal<>();
    private static ThreadLocal<String> username = new ThreadLocal<>();

    public static void setUserId(Long _userId) {
        userId.set(_userId);
    }

    public static Long getUserId() {
        return userId.get();
    }

    public static void removeUserId() {
        userId.remove();
    }

    public static void setUsername(String _username) {
        username.set(_username);
    }

    public static String getUsername() {
        return username.get();
    }

    public static void removeUsername() {
        username.remove();
    }
}
