import java.util.List;
import java.util.Objects;

/**
 * Class responsible for listing only title/s of book/s or
 * all details of book/s
 */
public class ListCmd extends LibraryCommand {

    /**
     * Create a ListCmd instance.
     * @param argumentInput :
     */
    public ListCmd(String argumentInput) {
        super(CommandType.LIST, argumentInput);
    }

    /**
     * answerFormat method: store the format "long"/"short" that the user entered
     * and later used in the execute method.
     */
    private String answerFormat;

    /**
     * Parse given user input and compare if user input is correct or not.
     *
     * @param argumentInput : user input which is either "long"/"short" or blank.
     * @return true if correct input.
     * @return false otherwise.
     * @throws NullPointerException : if the given data is null.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        Objects.requireNonNull(argumentInput, "Given input must not be null.");

        if ("long".equalsIgnoreCase(argumentInput)) {
            answerFormat = "long";
            return true;
        } else if (("short".equalsIgnoreCase(argumentInput)) || argumentInput.isBlank()) {
            answerFormat = "short";
            return true;
        } else {
            return false;
        }
    }

    /**
     * Execute the command given.
     *
     * @param data : book data to be considered for command execution.
     * @throws NullPointerException : if given data is null.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, "Given data must not be null.");

        List<BookEntry> books = data.getBookData();

        // Check if library contain books.
        if (books.size() == 0) {
            System.out.println("The library has no book entries.");
        } else {
            System.out.println(books.size() + " books in library:");

            if (answerFormat.equals("long")) {
                // Print all details of the book by calling Book Entry instance's toString method.
                for (BookEntry book : books) {
                    System.out.println(book.toString() + "\n");
                }
            } else {
                // Either "short" or blank.
                // Print title of book only by calling Book Entry instance's getTitle method.
                for (BookEntry book : books) {
                    System.out.println(book.getTitle());
                }
            }
        }
    }
}
