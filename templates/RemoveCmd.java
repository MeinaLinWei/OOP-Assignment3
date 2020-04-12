import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Class responsible for removing book/s according to title or author.
 */
public class RemoveCmd extends LibraryCommand{

    /**
     * Create a  RemoveCmd instance.
     * @param argInput : hold the title/s or author/s of book/s to be removed
     */
    public RemoveCmd(String argInput) {
        super(CommandType.REMOVE, argInput);
    }

    /** */
    private String titleOrAuthor;
    private String restDetails;

    /**
     * Check if user entered either "TITLE" or "AUTHOR".
     * @param argumentInput : argument input for this command
     * @return true if first argument is TITLE/AUTHOR and second argument is not empty
     * @return false otherwise.
     * @throws NullPointerException : if given input is null.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        Objects.requireNonNull(argumentInput, "Given input must not be null.");

        // Split input at the first whitespace only, and store the details into an array.
        String[] splitDetails = argumentInput.split(" ", 2);

        // Store details into respective variable.
        titleOrAuthor = splitDetails[0];

        try {
            restDetails = splitDetails[1];
        } catch (Exception IO) {
            return false;
        }

        if ((titleOrAuthor.equals("TITLE") || titleOrAuthor.equals("AUTHOR")) && !(restDetails.isEmpty())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check if user entered "TITLE"/"AUTHOR" and remove books accordingly.
     *
     * @param data : book data to be considered for command execution.
     * @throws NullPointerException : if given data is null.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, "Given data must not be null.");
        List<BookEntry> books = data.getBookData();
        Iterator<BookEntry> iteration = books.iterator();

        if ("TITLE".equalsIgnoreCase(titleOrAuthor)) {
            removeTitle(iteration);
        } else {
            removeAuthor(iteration);
        }
    }

    /**
     * Remove all books that have book authors as specified by the user.
     *
     * @param iteration : BookEntry iterator object.
     * @throws NullPointerException : If given data is null.
     */
    private void removeAuthor(Iterator<BookEntry> iteration) {
        Objects.requireNonNull(iteration, "Given books must not be null.");

        int counter = 0;

        do {
            String[] authors = iteration.next().getAuthors();
            for (String author : authors) {
                if (author.equals(restDetails)) {
                    iteration.remove();
                    counter++;
                    break;
                }
            }
        }
        while (iteration.hasNext());

        System.out.println(counter + " books removed for author: " + restDetails);
    }

    /**
     * This will remove the single book with the exact title
     * entered by the user
     *
     * @param iteration BookEntry iterator object
     */
    private void removeTitle(Iterator<BookEntry> iteration) {
        Objects.requireNonNull(iteration, "Given books must not be null.");

        int counter = 0;

        do {
            String title = iteration.next().getTitle();
            if (title.equals(restDetails)) {
                iteration.remove();
                counter++;
            }
        }
        while (iteration.hasNext());

        if (counter > 0) {
            System.out.println(restDetails + ": removed successfully.");
        } else {
            System.out.println(restDetails + ": not found.");
        }
    }
}