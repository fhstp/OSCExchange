package ac.at.fhstp.digitech.oscexchange;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Stores an OSC-address, such as "/my/osc/address"
 *
 * @see OSCAddress#create(String)
 */
public final class OSCAddress {

    /**
     * The pattern used to validate OSCAddresses
     */
    public static final Pattern pattern = Pattern.compile("^(/[a-zA-Z0-9]+)+$");


    /**
     * Checks if a string is a valid OSCAddress
     *
     * @param value The string to be checked
     * @return True if the string is a valid OSCAddress
     */
    public static boolean isValidOSCAddress(String value) {
        return value != null && pattern.matcher(value).find();
    }

    /**
     * Creates an OSCAddress
     *
     * @param value The string from which to create the address
     * @return An optional address. Has a value if validation succeeded, empty otherwise
     */
    public static Optional<OSCAddress> create(String value) {
        if (isValidOSCAddress(value)) return Optional.of(new OSCAddress(value));
        else return Optional.empty();
    }


    public final String value;


    private OSCAddress(String address) {
        this.value = address;
    }

}
