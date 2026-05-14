import java.util.Arrays;
import java.time.Year;

/**
 * Represents a book in the Library Management System.
 * Stores book details such as ISBN, title, authors, publisher,
 * publication year, language, and availability.
 */
public class Book {

    // ===================== FIELDS =====================
    private String isbn13;
    private String title;
    private String[] authors;
    private String publisher;
    private int publicationYear;
    private String language;
    private boolean available;

    // ===== New attributes Week 2=====
    private String format;
    private int pageCount;
    private String description;
    private String[] editors;
    private String subject;

    // ===== New Week 3 Attribute =====
    // Array to hold physical copies of the book (Required for StockManager)
    private BookItem[] items;
    private int stock; // To store the stock number

    // ===================== CONSTRUCTORS =====================
    /**
     * Default constructor with safe default values.
     */
    public Book() {
        this.isbn13 = "0000000000000";
        this.title = "Unknown Title";
        this.authors = new String[]{"Unknown Author"};
        this.publisher = "Unknown Publisher";
        this.publicationYear = 9999;
        this.language = "English";
        this.available = true;
        this.stock = 0;
    }

    /**
     * Constructor without availability (defaults to available).
     */
    public Book(String isbn13, String title, String[] authors,
                String publisher, int publicationYear, String language, int stock) {
        this(isbn13, title, authors, publisher, publicationYear, language, true, 0);
    }

    /**
     * Constructor used by Main class (with availability).
     */
    public Book(String isbn13, String title, String[] authors,
                String publisher, int publicationYear,
                String language, boolean available, int stock) {

        // Use the new Validator for ISBN-13 validation (13 digits) and Title
        this.isbn13 = Validator.isIsbnValid(isbn13)
                ? isbn13 : "0000000000000";

        this.title = Validator.isTitleValid(title)
                ? "Unknown Title" : title;

        this.authors = (authors != null && authors.length > 0)
                ? authors : new String[]{"Unknown Author"};

        this.publisher = (publisher == null || publisher.isBlank())
                ? "Unknown Publisher" : publisher;

        int currentYear = Year.now().getValue();
        this.publicationYear =
                (publicationYear >= 1500 && publicationYear <= currentYear)
                        ? publicationYear : 9999;

        this.language = (language == null || language.isBlank())
                ? "English" : language;

        this.available = available;

        // Validate and set physical stock
        if (!Validator.isValidStock(stock)){
            stock = 0;
        }
        this.items = new BookItem[stock]; // array size = number of physical copies
        //elements may be null

        // Defaults for additional attributes
        this.format = null;
        this.pageCount = 0;
        this.description = "";
        this.editors = null;
        this.subject = null;
    }

    // ===== week 02 (Getters & Setters) =====

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        if (format == null || format.isEmpty()) {
            throw new IllegalArgumentException("Format cannot be empty.");
        }
        this.format = format;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        if (pageCount <= 0) {
            throw new IllegalArgumentException("Page count must be positive.");
        }
        this.pageCount = pageCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = (description == null) ? "" : description;
    }

    public String[] getEditors() {
        return editors;
    }

    public void setEditors(String[] editors) {
        this.editors = editors;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        if (subject == null || subject.isEmpty()) {
            throw new IllegalArgumentException("Subject cannot be empty.");
        }
        this.subject = subject;
    }

    // ===================== week 01 (GETTERS & SETTERS) =====================

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        if (Validator.isIsbnValid(isbn13)) {
            this.isbn13 = isbn13;
        }
        // If invalid, ignore
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (Validator.isTitleValid(title)) {
            this.title = title;
        }
        // If invalid, ignore
    }

    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        if (authors != null && authors.length > 0) {
            this.authors = authors;
        }
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        if (publisher != null && !publisher.isBlank()) {
            this.publisher = publisher;
        }
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        int currentYear = Year.now().getValue();
        if (publicationYear >= 1500 && publicationYear <= currentYear) {
            this.publicationYear = publicationYear;
        }
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        if (language != null && !language.isBlank()) {
            this.language = language;
        }
    }

    public boolean isAvailable() {
        return available;
    }

    // Prefer these instead of setAvailable()
    public void markAvailable() {
        this.available = true;
    }

    public void markUnavailable() {
        this.available = false;
    }

    // ===================== UTILITY METHODS =====================

    /**
     * Returns the primary author of the book.
     */
    // ===== Additional Methods =====

    public String getPrimaryEditor() {
        if (editors != null && editors.length > 0) {
            return editors[0];
        }
        return "N/A";
    }

    public String getDetails() {
        String authorText = (authors != null)
                ? String.join(", ", authors)
                : "Unknown Author";
        // Added physical copies count to details (optional enhancement)
        StockManager stockManager = new StockManager();
        String stockText = "Physical copies: " + stockManager.getAvailableCount() + "\n";

        return "------------------------------------------------\n" +
                "Title: " + title + "\n" +
                "ISBN: " + isbn13 + "\n" +
                "Authors: " + authorText + "\n" +
                "Publisher: " + publisher + "\n" +
                "Year: " + publicationYear + "\n" +
                "Language: " + language + "\n" +
                "Format: " + (format != null ? format : "N/A") + "\n" +
                "Pages: " + pageCount + "\n" +
                "Subject: " + (subject != null ? subject : "N/A") + "\n" +
                stockText +
                "Available: " + available + "\n";
    }

    /**
     * Returns a formatted citation string.
     */
    public String getCitation() {
        return title + " (" + publisher + ", " + publicationYear + ")";
    }

    // ======= 1. Static Nested Class : Validator ======
    /**
     * Static nested utility class for validating book attributes.
     */
    public static class Validator {

        /**
         * Checks if both the ISBN and title are valid.
         * @param isbn  the ISBN string to validate
         * @param title the book title to validate
         * @return true only if both are valid
         */
        public static boolean isValid(String isbn, String title) {
            return isIsbnValid(isbn) && isTitleValid(title);
        }

        /**
         * Checks if the ISBN consists of exactly 13 digits.
         * @param isbn the ISBN string to validate
         * @return true if it is exactly 13 digits, false otherwise (including null)
         */
        public static boolean isIsbnValid(String isbn) {
            return isbn != null && isbn.matches("\\d{13}");
        }

        /**
         * Checks if the title is not empty or whitespace-only.
         * @param title the book title to validate
         * @return true if the title is valid
         */
        public static boolean isTitleValid(String title) {
            return title != null && !title.isBlank();
        }

        /**
         * Checks if the stock value is non-negative.
         * @param stock the stock value to validate
         * @return true if stock >= 0
         */
        public static boolean isValidStock(int stock) {
            return stock >= 0;
        }
    }
    // ========2. Inner Class : Stock Manager =====================
    /**
     * Inner class responsible for managing and querying the physical stock
     * of a specific Book instance.
     */
    public class StockManager {

        /**
         * Returns the number of physical book copies (array length).
         * @return the count of physical books
         */
        public int getAvailableCount() {
            return (Book.this.items == null) ? 0 : Book.this.items.length;
        }

        /**
         * Checks whether another physical copy can be added without exceeding
         * the shelf limit.
         * @param shelfLimit the maximum allowed copies
         * @return true if current count < shelfLimit
         */
        public boolean canAddMore(int shelfLimit) {
            return getAvailableCount() < shelfLimit;
        }
    }
}
