package app;

import dao.UserDAO;
import model.User;

public class AuthService {

    private final UserDAO userDAO = new UserDAO();

    public User login(String email, String password) throws Exception {
        User user = userDAO.findByEmail(email);
        if (user != null && PasswordUtil.checkPassword(password, user.getPasswordHash())) {
            return user;
        }
        return null;
    }
}
