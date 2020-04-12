import java.util.Arrays;
import java.util.Objects;

/**
 * Immutable class encapsulating data for a single book entry.
 **/

public class BookEntry {

    /** Store title of book. */
    private final String title;

    /** Store names of all authors. */
    private final String[] authors;

    /** Store rating of book. */
    private final float rating;

    /** Store ISBN of book. */
    private final String ISBN;

    /** Store number of pages. */
    private final int pages;

    /**
     * @param title : Title of book.
     * @param authors : List of author(s) of book.
     * @param rating : Rating of the book (rating is between 0 and 5 and is formatted to two decimal place.)
     * @param ISBN : ISBN of book.
     * @param pages : Number of pages the book has.
     * @throws NullPointerException : if given title/authors/rating/ISBN/pages is null.
     * @throws IllegalArgumentException : if given rating is out of range.
     * @throws IllegalArgumentException : if given pages is negative.
     **/

    public BookEntry(String title, String[] authors, float rating, String ISBN, int pages){

        // Check if each parameter is not empty.
        Objects.requireNonNull(title, "Given 'Title' cannot be empty");
        Objects.requireNonNull(authors, "Given 'Authors' cannot be empty");
        Objects.requireNonNull(ISBN, "Given 'ISBN' cannot be empty");

        // Initialise corresponding fields.
        this.title = title;
        this.authors = authors;
        this.rating = rating;
        this.ISBN = ISBN;
        this.pages = pages;

        // Check if rating is within correct range.
        if (rating < 0 || rating > 5){
            throw new IllegalArgumentException("Rating must be between 0 and 5.");
        }

        // Check if number of pages is positive.
        if (pages < 0){
            throw new IllegalArgumentException("Number of pages cannot be negative.");
        }

    }

    /**
     *
     * @return title of book.
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @return an array of author names.
     */
    public String[] getAuthors() {
        return authors;
    }

    /**
     *
     * @return rating of book.
     */
    public float getRating() {
        return rating;
    }

    /**
     *
     * @return ISBN of book.
     */
    public String getISBN() {
        return ISBN;
    }

    /**
     *
     * @return number of pages of the book.
     */
    public int getPages() {
        return pages;
    }

    /**
     * Override toString() method.
     * @return desired format.
     */
    @Override
    public String toString() {
        return  title + '\n' +
                "by " + Arrays.toString(authors).replace("[", "").replace("]", "") + '\n' +
                "Rating: " + String.format("%.2f",rating) + '\n' +
                "ISBN: " + ISBN + '\n' +
                pages + " pages";
    }

    /**
     * Override equals() method.
     * @param o : Casting object into BookEntry.
     * @return true if two books are equal, otherwise return false.
    **/
    @Override
    public boolean equals(Object o) {
        // Check if the two instances are the same.
        if (this == o) {
            return true;
        }

        // Check if parameter is null.
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BookEntry bookEntry = (BookEntry) o;

        return Float.compare(bookEntry.rating, rating) == 0 &&
                pages == bookEntry.pages &&
                title.equals(bookEntry.title) &&
                Arrays.equals(authors, bookEntry.authors) &&
                ISBN.equals(bookEntry.ISBN);
    }

    /**
     * Overrides hashCode() method.
     *
     * @return result of hashCode calculation.
     */

    @Override
    public int hashCode() {
        int result = Objects.hash(title, rating, ISBN, pages);
        result = 31 * result + Arrays.hashCode(authors);
        return result;
    }
}
