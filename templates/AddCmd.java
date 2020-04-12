import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Class responsible for adding books into library
 * from given user input and executing them.
 */
public class AddCmd extends LibraryCommand {

    /**
     * Create a AddCmd instance.
     *
     * @param argumentInput : book/s to be added into library.
     */
    public AddCmd(String argumentInput) {
        super(CommandType.ADD, argumentInput);
    }

    /** Store path of the file. */
    private Path filePath;

    /**
     * Override LibraryCommand’s parseArguments() method.
     * Check if file name is of correct format.
     *
     * @param argumentInput : contains ADD keyword followed by
     * name of book/s or file path for book/s to be added into library
     * @return true if file name ends with .csv. Otherwise, return false.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        // Check if argumentInput is not null.
        Objects.requireNonNull(argumentInput, "Input cannot be null.");

        /*
        Check if length of file is more than 4.
        If less than 4, file name does not contain .csv and returns false immediately.
        If equal to 4, file name may contain .csv only or not at all, and returns false.
        If greater than 4, file name may contain .csv or not, and if contain .csv, then file name is correct
        */
        if (argumentInput.length() > 4){
            if (argumentInput.substring(argumentInput.length()-4).equals(".csv") && !argumentInput.isBlank()) {
                filePath = Paths.get(argumentInput);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Execute ADD command and add all entries into database.
     *
     * @param data book data to be considered for command execution.
     * @throws NullPointerException : if given data is null.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, "Given data must not be null.");

        data.loadData(filePath);
    }

}
