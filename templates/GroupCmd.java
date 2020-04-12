import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Group command used to group all book's titles by title or author
 */
public class GroupCmd extends LibraryCommand{

    /** Chosen type from the user*/
    private String titleOrAuthor;


    /**
     * Create a GroupCmd instance.
     *
     * @param argumentInput : user input which is either "TITLE" or "AUTHOR"
     */
    public GroupCmd(String argumentInput) {
        super(CommandType.GROUP, argumentInput);
    }

    /**
     * This will check the user input to see if it is TITLE or AUTHOR and
     * then parse the information to be used later.
     *
     * @param argumentInput argument input for this command.
     * @return true if it's TITLE or AUTHOR else false.
     * @throws NullPointerException if given input is null.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        Objects.requireNonNull(argumentInput, "Given argument must not be null.");

        if (argumentInput.equals("TITLE")) {
            titleOrAuthor = "TITLE";
            return true;
        } else if (argumentInput.equals("AUTHOR")) {
            titleOrAuthor = "AUTHOR";
            return true;
        } else {
            return false;
        }
    }

    /**
     * This will check the selected display type and then print the corresponding
     * output
     *
     * @param data : book data to be considered for command execution.
     * @throws NullPointerException : if given data is null.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, "Given data must not be null.");
        List<BookEntry> books = data.getBookData();

        if (books.isEmpty()) {
            System.out.println("The library has no book entries.");
        } else {
            System.out.println("Grouped data by " + titleOrAuthor);

            if (titleOrAuthor.equals("TITLE")) {
                groupByTitleOnly(books);
            } else {
                groupByAuthorOnly(books);
            }
        }
    }

    /**
     * Map all titles with their corresponding first letter using HashMap.
     * For example, title "On Duties" will have value 'O'.
     *
     * @param books contains all the book entries in a List.
     */
    private void groupByTitleOnly(List<BookEntry> books) {

        HashMap<Character, List<String>> bookMap = new HashMap<>();

        // Put letters into HashMap.
        for (int i = 0; i < 26; i++) {
            ArrayList<String> values = new ArrayList<>();
            bookMap.put((char)('A' + i), values);
        }

        List<String> titles = new ArrayList<>();
        bookMap.put('0', titles);

        for (BookEntry book : books) {

            // Store the first character of the title.
            char firstChar = book.getTitle().toUpperCase().charAt(0);

            if (Character.isLetter(firstChar)) {
                // Sort titles alphabetically by creating a new list.
                List<String> sortTitle = new ArrayList<>();
                sortTitle = bookMap.get(firstChar);
                sortTitle.add(book.getTitle());
                bookMap.replace(firstChar, sortTitle);
            } else {
                // Sort titles in ascending order of numbers by creating a new list.
                List<String> sortTitle = new ArrayList<>();
                sortTitle = bookMap.get('0');
                sortTitle.add(book.getTitle());
                bookMap.replace('0', sortTitle);
            }
        }

        // Check if library contains books with titles that start with a number.
        // If not, remove '0' so that it is not printed.
        if (bookMap.get('0').isEmpty()) {
            bookMap.remove('0');
        }

        // Print the list of titles in alphabetical order.
        printTitleOnly(bookMap);

    }

    /**
     * Print titles in alphabetical order.
     * Print titles containing letters before titles containing numbers.
     *
     * @param bookMap contains the list of titles to be printed.
     */
    private void printTitleOnly(HashMap<Character, List<String>> bookMap) {

        for (char key : bookMap.keySet()) {

            List<String> titles = bookMap.get(key);

            // Print titles that start with numbers.
            if (key == '0') {
                // Print header.
                System.out.println("## [0-9]");
                for (String title : titles) {
                    System.out.println(title);
                }
            // Print titles that start with letters.
            } else if (!titles.isEmpty() && key != '0') {
                // Print header.
                System.out.println("## " + key);
                for (String title : titles) {
                    System.out.println(title);
                }
            }

        }
    }

    /**
     * Group all books by their authors.
     *
     * @param books contains all the book entries in a List
     */
    private void groupByAuthorOnly(List<BookEntry> books) {

        HashMap<String, List<String>> bookMap = new HashMap<>();

        List<String> authors = new ArrayList<>();

        // Take all book authors from BookEntry.
        for (BookEntry book : books) {
            authors.addAll(Arrays.asList(book.getAuthors()));
        }

        // Sort Authors and group them in terms of same first alphabet.
        List<String> sortAuthors =
                authors.stream().distinct().sorted(String.CASE_INSENSITIVE_ORDER).collect(Collectors.toList());

        // Put authors into HashMap.
        for (String author : sortAuthors) {
            List<String> values = new ArrayList<>();
            bookMap.put(author, values);
        }


        for (BookEntry book : books) {
            for (String author : book.getAuthors()) {
                List<String> values = bookMap.get(author);
                values.add(book.getTitle());
                bookMap.replace(author, values);
            }
        }

        printAuthorOnly(bookMap);
    }

    /**
     * Print the book's titles grouped by authors.
     *
     * @param authorMap contains books' titles corresponding to their authors.
     */
    private void printAuthorOnly(HashMap<String, List<String>> authorMap) {
        List<String> authors = new ArrayList<>(authorMap.keySet());

        // Sort the authors in alphabetical order.
        authors.sort(String.CASE_INSENSITIVE_ORDER);

        for (String author : authors) {
            List<String> values = authorMap.get(author);
            // Print header and the author's name
            System.out.println("## " + author);
            // Print the name/s of the book/s written by the author.
            for (String value : values) {
                System.out.println(value);
            }

        }
    }

}