package ac.at.fhstp.digitech.oscexchange

import java.util.*
import java.util.regex.Pattern

/**
 * Stores an OSC-address, such as "/my/osc/address"
 *
 * @see OSCAddress.create
 */
class OSCAddress private constructor(@PublicApi val value: String?) {
    companion object {
        /**
         * The pattern used to validate OSCAddresses
         */
        @PublicApi
        val pattern: Pattern = Pattern.compile("^(/[a-zA-Z0-9]+)+$")

        /**
         * Checks if a string is a valid OSCAddress
         *
         * @param value The string to be checked
         * @return True if the string is a valid OSCAddress
         */
        @PublicApi
        fun isValidOSCAddress(value: String): Boolean {
            return pattern.matcher(value).find()
        }

        /**
         * Creates an OSCAddress
         *
         * @param value The string from which to create the address
         * @return An optional address. Has a value if validation succeeded, empty otherwise
         */
        @PublicApi
        fun create(value: String): Optional<OSCAddress> {
            return if (isValidOSCAddress(value)) Optional.of(OSCAddress(value))
            else Optional.empty()
        }
    }
}