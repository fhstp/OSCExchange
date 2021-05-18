package ac.at.fhstp.digitech.oscexchange;

import java.util.ArrayList;

import ac.at.fhstp.digitech.oscexchange.errors.OSCErrorListener;

public class OSCExchange {

    public static Builder buildNew() {
        return new Builder();
    }


    private final OSCRequest[] requests;
    private final OSCErrorListener errorListener;
    private final OSCExchangeCompleteListener completeListener;


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


        public void send(OSCAddress address, OSCArgs args) {
            requests.add(new OSCSendRequest(address, args));
        }

        public void receive(OSCAddress address, OSCRawReceiveListener listener) {
            requests.add(new OSCRawReceiveRequest(address, listener));
        }

        public void receive(OSCAddress address, OSCArgsValidator validator, OSCRawReceiveListener listener) {
            requests.add(new OSCRawReceiveRequest(address, validator, listener));
        }

        public <T> void receive(OSCAddress address, OSCArgsParser<T> parser, OSCParsedReceiveListener<T> listener) {
            requests.add(new OSCParsedReceiveRequest<>(address, parser, listener));
        }

        public <T> void receive(OSCAddress address, OSCArgsValidator validator, OSCArgsParser<T> parser, OSCParsedReceiveListener<T> listener) {
            requests.add(new OSCParsedReceiveRequest<>(address, validator, parser, listener));
        }

        public void onError(OSCErrorListener errorListener) {
            this.errorListener = errorListener;
        }

        public OSCExchange onComplete(OSCExchangeCompleteListener completeListener) {
            return new OSCExchange(requests.toArray(new OSCRequest[0]),
                                   errorListener,
                                   completeListener);
        }

    }

}
