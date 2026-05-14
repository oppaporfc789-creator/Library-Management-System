/**
 * Represents a user in the Library Management System.
 * Manages personal details, role, login, borrowing and account status.
 */
public class User {

    // ===================== FIELDS =====================
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private boolean active;
    private int maxBorrowLimit;
    private String username;
    private String password;

    // ===================== CONSTRUCTORS =====================

    /**
     * Default constructor with safe default values.
     */
    public User() {
        this.userId = "UNKNOWN";
        this.firstName = "Unknown";
        this.lastName = "User";
        this.email = "no-email@library.com";
        this.role = Role.STUDENT;
        this.active = true;
        this.maxBorrowLimit = 3;
        this.username = "guest";
        this.password = "guest";
    }

    /**
     * Constructor used by Main class (login-enabled user).
     */
    public User(String userId, String firstName, String lastName,
                String email, Role role,
                String username, String password) {

        this.userId = (userId == null || userId.isBlank())
                ? "UNKNOWN" : userId;

        this.firstName = (firstName == null || firstName.isBlank())
                ? "Unknown" : firstName;

        this.lastName = (lastName == null || lastName.isBlank())
                ? "User" : lastName;

        this.email = (email != null && email.contains("@"))
                ? email : "no-email@library.com";

        this.role = (role == null)
                ? Role.STUDENT : role;

        this.username = (username == null || username.isBlank())
                ? "guest" : username;

        this.password = (password == null || password.isBlank())
                ? "guest" : password;

        this.active = true;
        this.maxBorrowLimit = 3;
    }

    // ===================== GETTERS & SETTERS =====================

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        if (userId != null && !userId.isBlank()) {
            this.userId = userId;
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName != null && !firstName.isBlank()) {
            this.firstName = firstName;
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName != null && !lastName.isBlank()) {
            this.lastName = lastName;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email != null && email.contains("@")) {
            this.email = email;
        }
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        if (role != null) {
            this.role = role;
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getMaxBorrowLimit() {
        return maxBorrowLimit;
    }

    public void setMaxBorrowLimit(int maxBorrowLimit) {
        if (maxBorrowLimit > 0) {
            this.maxBorrowLimit = maxBorrowLimit;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username != null && !username.isBlank()) {
            this.username = username;
        }
    }

    public String getPassword() {
        return password;
    }

    // (For real systems, never expose passwords like this)
    public void setPassword(String password) {
        if (password != null && !password.isBlank()) {
            this.password = password;
        }
    }

    // ===================== BUSINESS / UTILITY METHODS =====================

    /**
     * Returns full name of user.
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Checks whether user can borrow more books.
     */
    public boolean canBorrow(int currentBorrowedCount) {
        return active && currentBorrowedCount < maxBorrowLimit;
    }

    /**
     * Deactivates the user account.
     */
    public void deactivate() {
        this.active = false;
    }

    /**
     * Activates the user account.
     */
    public void activate() {
        this.active = true;
    }

    /**
     * Returns a formatted string with all user details (password excluded for security).
     */
    public String getDetails() {
        return "------------------------------------------------\n" +
                "User ID: " + userId + "\n" +
                "Full Name: " + getFullName() + "\n" +
                "Email: " + email + "\n" +
                "Role: " + role + "\n" +
                "Username: " + username + "\n" +
                "Active: " + active + "\n" +
                "Max Borrow Limit: " + maxBorrowLimit + "\n" +
                "------------------------------------------------";
    }
}
