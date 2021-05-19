package ac.at.fhstp.digitech.oscexchange;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Stores arguments that can be sent or received over OSC
 */
public final class OSCArgs {

    /**
     * Creates OSCArgs from a single argument
     *
     * @param arg The argument
     * @return The created OSCArgs
     */
    public static OSCArgs single(Object arg) {
        return multiple(arg);
    }

    /**
     * Creates OSCArgs from multiple arguments
     *
     * @param args The arguments
     * @return The created OSCArgs
     */
    public static OSCArgs multiple(Object... args) {
        return new OSCArgs(args);
    }

    /**
     * Creates OSCArgs from a list of arguments
     *
     * @param args The arguments
     * @return The created OSCArgs
     */
    public static OSCArgs list(List<Object> args) {
        return new OSCArgs(args.toArray());
    }

    /**
     * Counts the number of arguments in the given OSCArgs
     *
     * @param args The args to be counted
     * @return The number of arguments
     */
    public static int argCount(OSCArgs args) {
        return args.args.length;
    }

    /**
     * Tries to get an argument of a specific type from a specific index. If no such argument is found, an empty Optional is returned
     *
     * @param args  The OSCArgs from which to get the argument
     * @param index The index of the argument
     * @param type  The class of the argument
     * @param <T>   The type of the argument
     * @return The optional result
     */
    public static <T> Optional<T> getArg(OSCArgs args, int index, Class<T> type) {
        if (argCount(args) > index && args.args[index].getClass() == type)
            //noinspection unchecked
            return Optional.of((T) args.args[index]);
        else
            return Optional.empty();
    }

    /**
     * Checks if the OSCArgs has an argument of a specific type from a specific index
     *
     * @param args  The OSCArgs from which to get the argument
     * @param index The index of the argument
     * @param type  The class of the argument
     * @param <T>   The type of the argument
     * @return True if the OSCArgs has the searched argument
     */
    public static <T> boolean hasArg(OSCArgs args, int index, Class<T> type) {
        return getArg(args, index, type).isPresent();
    }

    static List<Object> asList(OSCArgs args) {
        return Arrays.asList(args.args);
    }


    private final Object[] args;


    private OSCArgs(Object[] args) {
        this.args = args;
    }

}
