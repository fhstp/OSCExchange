package ac.at.fhstp.digitech.oscexchange

/**
 * Listener for when OSCArgs were received
 */
fun interface OSCRawReceiveListener {
    /**
     * Handles the received OSCArgs
     *
     * @param args The received args
     */
    @PublicApi
    fun handle(args: OSCArgs)
}