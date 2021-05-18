package ac.at.fhstp.digitech.oscexchange;

final class OSCParsedReceiveRequest<T> extends OSCReceiveRequest {

    public final OSCArgsParser<T> parser;
    public final OSCParsedReceiveListener<T> listener;


    protected OSCParsedReceiveRequest(OSCAddress address, OSCArgsValidator validator, OSCArgsParser<T> parser, OSCParsedReceiveListener<T> listener, OSCRequest next) {
        super(address, validator, next);
        this.parser = parser;
        this.listener = listener;
    }

    protected OSCParsedReceiveRequest(OSCAddress address, OSCArgsValidator validator, OSCArgsParser<T> parser, OSCParsedReceiveListener<T> listener) {
        super(address, validator);
        this.parser = parser;
        this.listener = listener;
    }

}
