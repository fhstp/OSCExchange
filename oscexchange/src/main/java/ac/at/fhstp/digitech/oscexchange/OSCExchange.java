package ac.at.fhstp.digitech.oscexchange;

import android.os.AsyncTask;

import java.util.ArrayList;

import ac.at.fhstp.digitech.oscexchange.errors.OSCErrorListener;

/**
 * Stores all information about an OSC exchange between two devices
 */
public final class OSCExchange {

    /**
     * Instantiates a new builder with which a new OSCExchange can be constructed
     *
     * @return The new builder
     */
    @PublicApi
    public static Builder buildNew() {
        return new Builder();
    }

    /**
     * Runs the given exchange between the given devices
     *
     * @param exchange   The exchange to run
     * @param devicePair The devices between which to run the exchange
     */
    @PublicApi
    public static void runBetween(OSCExchange exchange, OSCDevicePair devicePair) {
        LiveOSCExchange.start(exchange, devicePair);
    }

    /**
     * Runs the given exchange asynchronously between the given devices
     *
     * @param exchange   The exchange to run
     * @param devicePair The devices between which to run the exchange
     */
    @PublicApi
    public static void runBetweenAsync(OSCExchange exchange, OSCDevicePair devicePair) {
        AsyncTask.execute(() -> runBetween(exchange, devicePair));
    }

    @PublicApi
    static int getRequestCount(OSCExchange exchange) {
        return exchange.requests.length;
    }

    static OSCRequest getRequest(OSCExchange exchange, int index) {
        return exchange.requests[index];
    }


    final OSCRequest[] requests;
    final OSCErrorListener errorListener;
    final OSCExchangeCompleteListener completeListener;


    private OSCExchange(OSCRequest[] requests, OSCErrorListener errorListener, OSCExchangeCompleteListener completeListener) {
        this.requests = requests;
        this.errorListener = errorListener;
        this.completeListener = completeListener;
    }


    /**
     * Used to build instances of OSCExchange
     */
    public static class Builder {

        private final ArrayList<OSCRequest> requests;
        private OSCErrorListener errorListener;


        private Builder() {
            requests = new ArrayList<>();
            errorListener = null;
        }


        /**
         * Adds a send request to the request-chain
         *
         * @param address The address to send the request to
         * @param args    The arguments to send to the address
         * @return The same builder for method chaining
         */
        @PublicApi
        public Builder send(OSCAddress address, OSCArgs args) {
            requests.add(new OSCSendRequest(address, args));
            return this;
        }

        /**
         * Adds a receive request to the request-chain
         *
         * @param address  The address on which the expected message will arrive
         * @param listener A listener to be called when the message arrives. Null if you need no listener
         * @return The same builder for method chaining
         */
        @PublicApi
        public Builder receive(OSCAddress address, OSCRawReceiveListener listener) {
            return receive(address, null, listener);
        }

        /**
         * Adds a receive request to the request-chain
         *
         * @param address The address on which the expected message will arrive
         * @return The same builder for method chaining
         */
        @PublicApi
        public Builder receive(OSCAddress address) {
            return receive(address, (OSCArgsValidator) null, null);
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
        public Builder receive(OSCAddress address, OSCArgsValidator validator, OSCRawReceiveListener listener) {
            requests.add(new OSCRawReceiveRequest(address, validator, listener));
            return this;
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
        public <T> Builder receive(OSCAddress address, OSCArgsParser<T> parser, OSCParsedReceiveListener<T> listener) {
            return receive(address, null, parser, listener);
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
        public <T> Builder receive(OSCAddress address, OSCArgsValidator validator, OSCArgsParser<T> parser, OSCParsedReceiveListener<T> listener) {
            requests.add(new OSCParsedReceiveRequest<>(address, validator, parser, listener));
            return this;
        }

        /**
         * Adds an error listener to the exchange
         *
         * @param errorListener The listener to be called when an error occurs. Null if you need no listener
         * @return The same builder for method chaining
         */
        @PublicApi
        public Builder onError(OSCErrorListener errorListener) {
            this.errorListener = errorListener;
            return this;
        }

        /**
         * Adds a completion listener to the exchange
         *
         * @param completeListener The listener to be called when the exchange completes without error. Null if you need no listener
         * @return The same builder for method chaining
         */
        @PublicApi
        public OSCExchange onComplete(OSCExchangeCompleteListener completeListener) {
            return new OSCExchange(requests.toArray(new OSCRequest[0]),
                                   errorListener,
                                   completeListener);
        }

    }

}
