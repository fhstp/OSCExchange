package ac.at.fhstp.digitech.oscexchange;

@FunctionalInterface
public interface OSCArgsValidator {

    static OSCArgsValidator hasCount(int count) {
        return args -> OSCArgs.argCount(args) == count;
    }

    static OSCArgsValidator isSingle() {
        return hasCount(1);
    }

    static OSCArgsValidator hasArgOfType(int index, Class<?> type) {
        return args -> OSCArgs.hasArg(args, index, type);
    }

    static OSCArgsValidator isSingleOfType(Class<?> type) {
        return isSingle().and(hasArgOfType(0, type));
    }


    boolean isValid(OSCArgs args);


    default OSCArgsValidator and(OSCArgsValidator other) {
        return data -> this.isValid(data) && other.isValid(data);
    }

    default OSCArgsValidator or(OSCArgsValidator other) {
        return data -> this.isValid(data) || other.isValid(data);
    }

    default OSCArgsValidator butNot(OSCArgsValidator other) {
        return data -> this.isValid(data) && !other.isValid(data);
    }

}
