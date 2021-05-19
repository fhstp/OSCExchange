package ac.at.fhstp.digitech.oscexchange;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public final class OSCArgs {

    public static OSCArgs single(Object arg) {
        return multiple(arg);
    }

    public static OSCArgs multiple(Object... args) {
        return new OSCArgs(args);
    }

    public static OSCArgs list(List<Object> args) {
        return new OSCArgs(args.toArray());
    }


    public static int argCount(OSCArgs args) {
        return args.args.length;
    }

    public static <T> Optional<T> getArg(OSCArgs args, int index, Class<T> type) {
        if (argCount(args) > index && args.args[index].getClass() == type)
            //noinspection unchecked
            return Optional.of((T) args.args[index]);
        else
            return Optional.empty();
    }

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
