package ac.at.fhstp.digitech.oscexchange;

import java.util.Optional;
import java.util.regex.Pattern;

public final class OSCAddress {

    public static Pattern pattern = Pattern.compile("^(/[a-zA-Z]+)+$");


    public static boolean isValidOSCAddress(String value) {
        return value != null && pattern.matcher(value).find();
    }

    public static Optional<OSCAddress> create(String value) {
        if (isValidOSCAddress(value)) return Optional.of(new OSCAddress(value));
        else return Optional.empty();
    }


    public final String value;


    private OSCAddress(String address) {
        this.value = address;
    }

}
