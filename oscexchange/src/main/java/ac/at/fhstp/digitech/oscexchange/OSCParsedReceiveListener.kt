package ac.at.fhstp.digitech.oscexchange

/**
 * Listener for when a parsed object was received
 *
 * @param <T> The type of the object
</T> */
fun interface OSCParsedReceiveListener<T> {
    /**
     * Handles the received object
     *
     * @param parsed The received object
     */
    @PublicApi
    fun handle(parsed: T)
}