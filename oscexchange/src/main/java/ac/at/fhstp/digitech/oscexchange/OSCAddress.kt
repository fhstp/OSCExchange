package ac.at.fhstp.digitech.oscexchange

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*
import java.util.regex.Pattern

/**
 * Stores an OSC-address, such as "/my/osc/address"
 *
 * @see OSCAddress.tryCreate
 */
@Parcelize
class OSCAddress private constructor(@PublicApi val value: String?) :
    Parcelable {
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
         * Ties to create an OSCAddress
         *
         * @param value The string from which to create the address
         * @return An optional address. Has a value if validation succeeded, empty otherwise
         */
        @PublicApi
        fun tryCreate(value: String): Optional<OSCAddress> {
            return if (isValidOSCAddress(value)) Optional.of(OSCAddress(value))
            else Optional.empty()
        }

        /**
         * Tries to create an OSCAddress. Throws an exception if not successful
         *
         * @param value The string from which to create the address
         * @return The created address
         */
        @PublicApi
        fun create(value: String) =
            tryCreate(value).get()
    }
}