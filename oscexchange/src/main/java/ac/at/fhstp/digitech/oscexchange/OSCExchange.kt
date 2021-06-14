package ac.at.fhstp.digitech.oscexchange

import ac.at.fhstp.digitech.oscexchange.errors.OSCErrorListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Stores all information about an OSC exchange between two devices
 */
class OSCExchange private constructor(
    val requests: Array<OSCRequest>,
    val errorListener: OSCErrorListener?,
    val completeListener: OSCExchangeCompleteListener?
) {

    companion object {
        /**
         * Instantiates a new builder with which a new OSCExchange can be constructed
         *
         * @return The new builder
         */
        @PublicApi
        fun buildNew(): Builder {
            return Builder()
        }

        /**
         * Runs the given exchange between the given devices
         *
         * @param exchange   The exchange to run
         * @param devicePair The devices between which to run the exchange
         */
        @PublicApi
        fun runBetween(exchange: OSCExchange, devicePair: OSCDevicePair) {
            LiveOSCExchange.start(exchange, devicePair)
        }

        /**
         * Runs the given exchange asynchronously between the given devices
         *
         * @param exchange   The exchange to run
         * @param devicePair The devices between which to run the exchange
         */
        @PublicApi
        suspend fun runBetweenAsync(
            exchange: OSCExchange,
            devicePair: OSCDevicePair
        ) = withContext(Dispatchers.IO) {
            runBetween(exchange, devicePair)
        }

        @PublicApi
        fun getRequestCount(exchange: OSCExchange): Int {
            return exchange.requests.size
        }

        fun getRequest(exchange: OSCExchange, index: Int): OSCRequest {
            return exchange.requests[index]
        }
    }

    /**
     * Used to build instances of OSCExchange
     */
    class Builder {
        private val requests: ArrayList<OSCRequest> = ArrayList()
        private var errorListener: OSCErrorListener? = null

        /**
         * Adds a send request to the request-chain
         *
         * @param address The address to send the request to
         * @param args    The arguments to send to the address
         * @return The same builder for method chaining
         */
        @PublicApi
        fun send(address: OSCAddress, args: OSCArgs): Builder {
            requests.add(OSCSendRequest(address, args))
            return this
        }

        /**
         * Adds a receive request to the request-chain
         *
         * @param address  The address on which the expected message will arrive
         * @param listener A listener to be called when the message arrives. Null if you need no listener
         * @return The same builder for method chaining
         */
        @PublicApi
        fun receive(
            address: OSCAddress,
            listener: OSCRawReceiveListener?
        ): Builder {
            return receive(address, null, listener)
        }

        /**
         * Adds a receive request to the request-chain
         *
         * @param address The address on which the expected message will arrive
         * @return The same builder for method chaining
         */
        @PublicApi
        fun receive(address: OSCAddress): Builder {
            return receive(address, null as OSCArgsValidator?, null)
        }

        /**
         * Adds a receive request to the request-chain
         *
         * @param address   The address on which the expected message will arrive
         * @param validator A validator to be used on the received data. Null if you need no validation
         * @param listener  A listener to be called when the message arrives. Null if you need no listener
         * @return The same builder for method chaining
         */
        @PublicApi
        fun receive(
            address: OSCAddress,
            validator: OSCArgsValidator?,
            listener: OSCRawReceiveListener?
        ): Builder {
            requests.add(OSCRawReceiveRequest(address, validator, listener))
            return this
        }

        /**
         * Adds a receive request to the request-chain
         *
         * @param address  The address on which the expected message will arrive
         * @param parser   A parser to be used on the received data
         * @param listener A listener to be called when the message arrives. Null if you need no listener
         * @return The same builder for method chaining
         */
        @PublicApi
        fun <T> receive(
            address: OSCAddress,
            parser: OSCArgsParser<T>,
            listener: OSCParsedReceiveListener<T>?
        ): Builder {
            return receive(address, null, parser, listener)
        }

        /**
         * Adds a receive request to the request-chain
         *
         * @param address   The address on which the expected message will arrive
         * @param validator A validator to be used on the received data. Null if you need no validation
         * @param parser    A parser to be used on the received data
         * @param listener  A listener to be called when the message arrives. Null if you need no listener
         * @return The same builder for method chaining
         */
        @PublicApi
        fun <T> receive(
            address: OSCAddress,
            validator: OSCArgsValidator?,
            parser: OSCArgsParser<T>,
            listener: OSCParsedReceiveListener<T>?
        ): Builder {
            requests.add(
                OSCParsedReceiveRequest(
                    address,
                    validator,
                    parser,
                    listener
                )
            )
            return this
        }

        /**
         * Adds an error listener to the exchange
         *
         * @param errorListener The listener to be called when an error occurs. Null if you need no listener
         * @return The same builder for method chaining
         */
        @PublicApi
        fun onError(errorListener: OSCErrorListener?): Builder {
            this.errorListener = errorListener
            return this
        }

        /**
         * Adds a completion listener to the exchange
         *
         * @param completeListener The listener to be called when the exchange completes without error. Null if you need no listener
         * @return The same builder for method chaining
         */
        @PublicApi
        fun onComplete(completeListener: OSCExchangeCompleteListener): OSCExchange {
            return OSCExchange(
                requests.toTypedArray(),
                errorListener,
                completeListener
            )
        }

    }

}