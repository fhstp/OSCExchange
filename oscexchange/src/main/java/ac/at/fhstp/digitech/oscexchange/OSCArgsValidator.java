package ac.at.fhstp.digitech.oscexchange;

/**
 * Checks if OSCArgs match given criteria
 */
@FunctionalInterface
public interface OSCArgsValidator {

    /**
     * Creates a validator that checks if the given OSCArgs have the specified number of arguments
     *
     * @param count The number that should be checked for
     * @return The created validator
     */
    @PublicApi
    static OSCArgsValidator hasCount(int count) {
        return args -> OSCArgs.argCount(args) == count;
    }

    /**
     * Creates a validator that checks if the given OSCArgs has a single argument
     *
     * @return The created validator
     */
    @PublicApi
    static OSCArgsValidator isSingle() {
        return hasCount(1);
    }

    /**
     * Creates a validator that checks if the given OSCArgs has an argument of the given type at a specific index
     *
     * @param index The index to check for the argument
     * @param type  The type of the argument
     * @return The created validator
     */
    @PublicApi
    static OSCArgsValidator hasArgOfType(int index, Class<?> type) {
        return args -> OSCArgs.hasArg(args, index, type);
    }

    /**
     * Creates a validator that checks if the given OSCArgs has a single argument of the given type
     *
     * @param type The type of the argument
     * @return The created validator
     */
    @PublicApi
    static OSCArgsValidator isSingleOfType(Class<?> type) {
        return isSingle().and(hasArgOfType(0, type));
    }


    /**
     * Checks if the given args match this validators criteria
     *
     * @param args The args to check
     * @return True if the args match the validators criteria
     */
    @PublicApi
    boolean isValid(OSCArgs args);


    /**
     * Combines this validator with a second one so that the result is only valid if it passes both of them
     *
     * @param other The other validator
     * @return The combined validator
     */
    @PublicApi
    default OSCArgsValidator and(OSCArgsValidator other) {
        return data -> this.isValid(data) && other.isValid(data);
    }

    /**
     * Combines this validator with a second one so that the result is valid if it passes either of them
     *
     * @param other The other validator
     * @return The combined validator
     */
    @PublicApi
    default OSCArgsValidator or(OSCArgsValidator other) {
        return data -> this.isValid(data) || other.isValid(data);
    }

    /**
     * Combines this validator with a second one so that the result is only valid if it passes the first but not the second
     *
     * @param other The other validator
     * @return The combined validator
     */
    @PublicApi
    default OSCArgsValidator butNot(OSCArgsValidator other) {
        return data -> this.isValid(data) && !other.isValid(data);
    }

}
