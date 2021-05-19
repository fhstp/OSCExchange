package ac.at.fhstp.digitech.oscexchange;

import android.os.AsyncTask;

import java.util.ArrayList;

import ac.at.fhstp.digitech.oscexchange.errors.OSCErrorListener;

public final class OSCExchange {

    public static Builder buildNew() {
        return new Builder();
    }

    public static void runBetween(OSCExchange exchange, OSCDevicePair devicePair) {
        LiveOSCExchange.start(exchange, devicePair);
    }

    public static void runBetweenAsync(OSCExchange exchange, OSCDevicePair devicePair) {
        AsyncTask.execute(() -> runBetween(exchange, devicePair));
    }

    static int getRequestCount(OSCExchange exchange) {
        return exchange.requests.length;
    }

    static OSCRequest getRequest(OSCExchange exchange, int index) {
        return exchange.requests[index];
    }


    public final OSCRequest[] requests;
    public final OSCErrorListener errorListener;
    public final OSCExchangeCompleteListener completeListener;


    private OSCExchange(OSCRequest[] requests, OSCErrorListener errorListener, OSCExchangeCompleteListener completeListener) {
        this.requests = requests;
        this.errorListener = errorListener;
        this.completeListener = completeListener;
    }


    public static class Builder {

        private final ArrayList<OSCRequest> requests;
        private OSCErrorListener errorListener;


        private Builder() {
            requests = new ArrayList<>();
            errorListener = null;
        }


        public Builder send(OSCAddress address, OSCArgs args) {
            requests.add(new OSCSendRequest(address, args));
            return this;
        }

        public Builder receive(OSCAddress address, OSCRawReceiveListener listener) {
            return receive(address, null, listener);
        }

        public Builder receive(OSCAddress address) {
            return receive(address, (OSCArgsValidator) null, null);
        }

        public Builder receive(OSCAddress address, OSCArgsValidator validator, OSCRawReceiveListener listener) {
            requests.add(new OSCRawReceiveRequest(address, validator, listener));
            return this;
        }

        public <T> Builder receive(OSCAddress address, OSCArgsParser<T> parser, OSCParsedReceiveListener<T> listener) {
            return receive(address, null, parser, listener);
        }

        public <T> Builder receive(OSCAddress address, OSCArgsValidator validator, OSCArgsParser<T> parser, OSCParsedReceiveListener<T> listener) {
            requests.add(new OSCParsedReceiveRequest<>(address, validator, parser, listener));
            return this;
        }

        public Builder onError(OSCErrorListener errorListener) {
            this.errorListener = errorListener;
            return this;
        }

        public OSCExchange onComplete(OSCExchangeCompleteListener completeListener) {
            return new OSCExchange(requests.toArray(new OSCRequest[0]),
                                   errorListener,
                                   completeListener);
        }

    }

}
