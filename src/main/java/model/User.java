package model;

public class User {

    private int userId;
    private String email;
    private String passwordHash;
    private int roleId;

    public User(int userId, String email, String passwordHash, int roleId) {
        this.userId = userId;
        this.email = email;
        this.passwordHash = passwordHash;
        this.roleId = roleId;
    }

    public int getUserId() { return userId; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public int getRoleId() { return roleId; }
}
