import java.util.Arrays;
import java.util.Scanner;
/**
 * Main class of the Library Management System.
 * Handles menus, login, account creation, and role-based actions.
 */
public class Main {

    // ===================== STORAGE =====================
    private static User[] userLists = new User[100];
    private static int userCount = 0;

    private static Book[] bookLists = new Book[100];
    private static int bookCount = 0;

    private static Scanner scanner = new Scanner(System.in);

    // ===================== MAIN =====================
    public static void main(String[] args) {
        seedData();
        createMenu();
    }

    // ===================== INITIAL DATA =====================
    /**
     * Pre-populates users and books for testing.
     */
    private static void seedData() {
        userLists[userCount++] = new User("A001", "Admin", "User", "admin@school.com", Role.ADMIN, "admin", "admin123");
        userLists[userCount++] = new User("L001", "Lib", "Rarian", "lib@school.com", Role.LIBRARIAN, "lib", "lib123");
        userLists[userCount++] = new User("S001", "Student", "User", "student@school.com", Role.STUDENT, "student", "student123");
        userLists[userCount++] = new User("T001", "Teacher", "User", "teacher@school.com", Role.TEACHER, "teacher", "teacher123");

        // Browse Books with initial stock and additional attributes
        Book b1 = new Book("1512154548453", "Java Basics", new String[]{"Author 1"}, "Publisher 1", 2025, "English", true, 5);
        b1.setFormat("Hardcover");
        b1.setPageCount(450);
        b1.setSubject("Technology");
        bookLists[bookCount++] = b1;

        Book b2 = new Book("1515474648455", "Python Basics", new String[]{"Author 2"}, "Publisher 2", 2025, "English", true, 5);
        b2.setFormat("E-Book");
        b2.setPageCount(550);
        b2.setSubject("Technology");
        bookLists[bookCount++] = b2;

        Book b3 = new Book("1515474648456", "Python Advance", new String[]{"Author 3"}, "Publisher 3", 2026, "English", true, 5); // Fixed duplicate ISBN
        b3.setFormat("E-Book");
        b3.setPageCount(750);
        b3.setSubject("Technology");
        bookLists[bookCount++] = b3;
    }

    // ===================== MAIN MENU =====================
    /**
     * Displays the main menu.
     */
    private static void createMenu() {
        while (true) {
            System.out.println("\n===== Library System =====");
            System.out.println("1. Login");
            System.out.println("2. Create Account");
            System.out.println("3. Browse Books");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");

            int choice = getIntInput();

            switch (choice) {
                case 1 -> login();
                case 2 -> createAccount();
                case 3 -> browseBooks();
                case 4 -> {
                    System.out.println("Thanks For Using This Program. Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    // ===================== LOGIN =====================
    /**
     * Handles user login.
     */
    private static void login() {
        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = findUser(username, password);

        if (user == null) {
            System.out.println("Invalid username or password.");
            return;
        }

        if (!user.isActive()) {
            System.out.println("Account is deactivated.");
            return;
        }

        System.out.println("Welcome " + user.getFullName());
        showRoleMenu(user);
    }

    // ===================== CREATE ACCOUNT =====================
    /**
     * Creates a new user account and allows role selection.
     */
    private static void createAccount() {

        if (userCount >= userLists.length) {
            System.out.println("User storage is full.");
            return;
        }

        System.out.print("User ID: ");
        String id = scanner.nextLine();

        System.out.print("First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        // ===== ROLE SELECTION =====
        System.out.println("\nSelect Role:");
        System.out.println("1. STUDENT");
        System.out.println("2. TEACHER");
        System.out.println("3. LIBRARIAN");
        System.out.println("4. ADMIN");
        System.out.print("Choose role (1-4): ");

        int roleChoice = getIntInput();
        Role role;

        switch (roleChoice) {
            case 2 -> role = Role.TEACHER;
            case 3 -> role = Role.LIBRARIAN;
            case 4 -> role = Role.ADMIN;
            default -> role = Role.STUDENT; // default & safe
        }

        User newUser = new User(id, firstName, lastName, email, role, username, password);
        userLists[userCount++] = newUser;

        System.out.println("Account created successfully as " + role + "!");
    }


    // ===================== FIND USER =====================
    /**
     * Finds a user by username and password.
     */
    private static User findUser(String username, String password) {
        for (int i = 0; i < userCount; i++) {
            if (userLists[i].getUsername().equals(username)
                    && userLists[i].getPassword().equals(password)) {
                return userLists[i];
            }
        }
        return null;
    }

    // ===================== ROLE MENU =====================
    /**
     * Displays menu based on user role.
     */
    private static void showRoleMenu(User user) {
        boolean loggedIn = true;

        while (loggedIn) {
            System.out.println("\n--- " + user.getRole() + " Menu ---");

            if (user.getRole() == Role.ADMIN || user.getRole() == Role.LIBRARIAN) {
                System.out.println("1. Manage Books");
                System.out.println("2. Logout");
            } else {
                System.out.println("1. Browse Books");
                System.out.println("2. Logout");
            }

            System.out.print("Choose option: ");
            int choice = getIntInput();

            switch (choice) {
                case 1 -> {
                    if (user.getRole() == Role.STUDENT || user.getRole() == Role.TEACHER) {
                        browseBooks();
                    } else {
                        manageBooks();
                    }
                }
                case 2 -> loggedIn = false;
                default -> System.out.println("Invalid option!");
            }
        }
    }

    // ===================== BOOK FEATURES =====================
    /**
     * Displays all books with details, including physical stock.
     */
    private static void browseBooks() {
        System.out.println("\n--- Book List ---");
        if (bookCount == 0) {
            System.out.println("No books available.");
            return;
        }


        for (int i = 0; i < bookCount; i++) {
            Book book = bookLists[i];
            System.out.println(book.getDetails() + (bookLists[i].isAvailable() ? " [Available]" : " [Borrowed]"));
            // Demonstrate StockManager: Check if more can be added
            Book.StockManager manager = book.new StockManager();
            System.out.println("Can add more copies? " + manager.canAddMore(LibraryConfig.SHELF_LIMIT));
        }
    }

    /**
     * Book management menu for admins/librarians.
     * Enhanced with options to add book, add stock, browse.
     */
    private static void manageBooks() {
        boolean managing = true;
        while (managing) {
            System.out.println("\n--- Manage Books ---");
            System.out.println("1. Add New Book");
            System.out.println("2. Add Stock to Existing Book");
            System.out.println("3. Browse Books");
            System.out.println("4. Back");
            System.out.print("Choose option: ");
            int choice = getIntInput();
            switch (choice) {
                case 1 -> addBook();
                case 2 -> addStockToBook();
                case 3 -> browseBooks();
                case 4 -> managing = false;
                default -> System.out.println("Invalid option!");
            }
        }
    }

    /**
     * Adds a new book with validation using Book.Validator.
     */
    private static void addBook() {
        if (bookCount >= bookLists.length) {
            System.out.println("Library is full.");
            return;
        }
        String isbn;
        String title;
        do {
            System.out.print("ISBN13: ");
            isbn = scanner.nextLine();
            System.out.print("Title: ");
            title = scanner.nextLine();
            if (!Book.Validator.isValid(isbn, title)) {
                System.out.println("Invalid ISBN or title. ISBN must be 13 digits, title cannot be empty.");
            }
        } while (!Book.Validator.isValid(isbn, title));

        System.out.print("Authors (comma-separated): ");
        String authorsInput = scanner.nextLine();
        String[] authors = authorsInput.isBlank() ? new String[]{"Unknown Author"} : authorsInput.split(",");

        System.out.print("Publisher: ");
        String publisher = scanner.nextLine();

        System.out.print("Publication Year: ");
        int year = getIntInput();

        System.out.print("Language: ");
        String language = scanner.nextLine();

        System.out.print("Initial Stock: ");
        int stock = getIntInput();
        if (!Book.Validator.isValidStock(stock)) {
            System.out.println("Invalid stock. Setting to 0.");
            stock = 0;
        }

        Book book = new Book(isbn, title, authors, publisher, year, language, true, stock);
        bookLists[bookCount++] = book;
        System.out.println("Book added successfully.");
    }

    /**
     * Adds physical stock to an existing book, checking shelf limit with StockManager.
     */
    private static void addStockToBook() {
        System.out.print("Enter book index (0 to " + (bookCount - 1) + "): ");
        int index = getIntInput();
        if (index < 0 || index >= bookCount) {
            System.out.println("Invalid book index.");
            return;
        }
        Book book = bookLists[index];

        Book.StockManager manager = book.new StockManager();
        System.out.println("Current physical copies: " + manager.getAvailableCount());

        if (!manager.canAddMore(LibraryConfig.SHELF_LIMIT)) {
            System.out.println("Cannot add more: Shelf limit reached (" + LibraryConfig.SHELF_LIMIT + ").");
            return;
        }

        System.out.print("How many copies to add? ");
        int addCount = getIntInput();
        if (!Book.Validator.isValidStock(addCount) || addCount <= 0) {
            System.out.println("Invalid count. Must be positive.");
            return;
        }
    }
// ===================== INPUT HELPER =====================
    /**
     * Safely reads integer input.
     */
    private static int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid input. Using 0.");
            return 0;
        }
    }
}
