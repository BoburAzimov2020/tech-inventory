package uz.dynamic.techinventory.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    // Администратор системный рол. Все - Полные права (Супер пользователь)
    public static final String ADMIN = "ROLE_ADMIN";

    // Пользователь - обычный*/
    public static final String USER = "ROLE_USER";

    // Модератор - системный рол с ограниченными возможностями
    public static final String MODERATOR = "ROLE_MODERATOR";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {
    }
}
