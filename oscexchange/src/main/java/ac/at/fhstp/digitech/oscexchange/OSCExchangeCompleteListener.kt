package ac.at.fhstp.digitech.oscexchange

/**
 * A listener for when an an exchange is completed
 */
fun interface OSCExchangeCompleteListener {
    /**
     * Handles the completed exchange
     */
    @PublicApi
    fun handle()
}