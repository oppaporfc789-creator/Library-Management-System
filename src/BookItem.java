import java.util.Date;
import java.util.Calendar;

/**
 * Represents a physical copy of a book in the Library Management System.
 * Each BookItem is a unique instance (e.g., different barcode) of a Book (identified by ISBN-13).
 * Manages availability, borrowing, due dates, reference-only status, and shelf location.
 */
public class BookItem {

    // ================= Fields =================
    private int bookItemID;
    private String isbn13;
    private String barcode;
    private boolean isReferenceOnly;
    private Date borrowedDate;
    private Date dueDate;
    private double price;
    private Status status;
    private String locationShelf;

    // ================= Constructor =================
    /**
     * Default constructor.
     */
    public BookItem(int bookItemID, String isbn13, String barcode, double price, String locationShelf) {
        this.bookItemID = bookItemID;
        this.isbn13 = isbn13;
        this.barcode = barcode;
        this.price = price;
        this.locationShelf = locationShelf;
        this.status = Status.AVAILABLE;
        this.isReferenceOnly = false;
    }

    // ================= Getters & Setters =================
    /**
     * Gets the unique ID of this book item.
     */
    public int getBookItemID() {
        return bookItemID;
    }

    /**
     * Gets ISBN-13 of the related Book.
     */
    public String getIsbn13() {
        return isbn13;
    }

    /**
     * Gets the barcode of this physical copy.
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * Returns whether the book is reference-only.
     */
    public boolean isReferenceOnly() {
        return isReferenceOnly;
    }

    /**
     * Sets whether this book is reference-only.
     */
    public void setReferenceOnly(boolean referenceOnly) {
        isReferenceOnly = referenceOnly;
    }

    /**
     * Gets the date when the book was borrowed.
     */
    public Date getBorrowedDate() {
        return borrowedDate;
    }

    /**
     * Gets the due date for returning the book.
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * Gets the replacement price of the book.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the replacement price.
     * @param price must be positive
     */
    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price must be positive.");
        }
        this.price = price;
    }

    /**
     * Gets the current status of the book item.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Updates the current status of the book item.
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Gets the shelf location of the book.
     */
    public String getLocationShelf() {
        return locationShelf;
    }

    /**
     * Sets the shelf location.
     */
    public void setLocationShelf(String locationShelf) {
        this.locationShelf = locationShelf;
    }

    // ================= Business Logic =================

    public boolean checkout(int userID) {
        if (isReferenceOnly) {
            System.out.println("This book is reference-only.");
            return false;
        }

        if (status != Status.AVAILABLE) {
            System.out.println("Book is not available.");
            return false;
        }

        this.borrowedDate = new Date();

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 14); // 2-week loan
        this.dueDate = cal.getTime();

        this.status = Status.LOANED;
        return true;
    }

    public void returnItem() {
        this.borrowedDate = null;
        this.dueDate = null;
        this.status = Status.AVAILABLE;
    }

    public boolean renew() {
        if (status != Status.LOANED) {
            return false;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(this.dueDate);
        cal.add(Calendar.DAY_OF_MONTH, 7); // extend 1 week
        this.dueDate = cal.getTime();

        return true;
    }

    public void updateStatus(Status newStatus) {
        this.status = newStatus;
    }

    /**
     * Returns a formatted string with all book item details.
     */
    public String getDetails() {
        return "------------------------------------------------\n" +
                "Book Item ID: " + bookItemID + "\n" +
                "Barcode: " + barcode + "\n" +
                "ISBN-13: " + isbn13 + "\n" +
                "Shelf Location: " + locationShelf + "\n" +
                "Price: $" + String.format("%.2f", price) + "\n" +
                "Status: " + status + "\n" +
                "Borrowed Date: " + (borrowedDate != null ? borrowedDate : "N/A") + "\n" +
                "Due Date: " + (dueDate != null ? dueDate : "N/A") + "\n" +
                "------------------------------------------------";
    }

}
