package ac.at.fhstp.digitech.oscexchange.errors

/**
 * A listener for when an error occurs
 */
fun interface OSCErrorListener {
    /**
     * Handles the occurred error
     *
     * @param error The error that occurred
     */
    fun handle(error: OSCError)
}