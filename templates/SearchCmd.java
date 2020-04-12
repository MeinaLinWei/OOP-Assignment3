import java.util.List;
import java.util.Objects;

/**
 * Class responsible for searching book/s based on one word only.
 */
public class SearchCmd extends LibraryCommand {

    /**
     * Create a SearchCmd instance.
     * @param argumentInput : hold the keyword needed for searching
     * the desired book/s.
     */
    public SearchCmd(String argumentInput) {
        super(CommandType.SEARCH, argumentInput);
    }

    /**
     * searchValue method: retain the input of the user.
     */
    private String searchValue;

    /**
     * Check that 'Search Value' is not blank and is one-word only.
     *
     * @param argumentInput : argument input for this command.
     * @return true if input is of correct format.
     * @return false otherwise.
     * @throws NullPointerException : if given input is null.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        Objects.requireNonNull(argumentInput, "Given input must not be empty.");

        // If search value contains whitespace(s), then it is not one word.
        if ((argumentInput.contains(" ")) || (argumentInput.isBlank())) {
            return false;
        } else {
            searchValue = argumentInput;
            return true;
        }
    }

    /**
     * Compare titles from library of books and the title the user entered.
     * This is done by first converting all letters for both into lower case.
     * If they are both the same, this means the book is present in the library and
     * th variable "absent" is set to false.
     *
     * @param data : book data to be considered for command execution.
     */
    @Override
    public void execute(LibraryData data) {
        List<BookEntry> book = data.getBookData();

        boolean absent = true;

        // Print all books whose title contain the 'Search Value'.
        for (BookEntry books : book) {
            if (books.getTitle().toLowerCase().contains(searchValue.toLowerCase())) {
                System.out.println(books.getTitle());
                absent = false;
            }
        }

        // No book's title matches 'Search Value'.
        if (absent) {
            System.out.println("No hits found for search term: " + searchValue);
        }
    }
}
