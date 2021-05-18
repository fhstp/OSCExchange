package ac.at.fhstp.digitech.oscexchange;

final class OSCParsedReceiveRequest<T> extends OSCReceiveRequest {

    public final OSCArgsParser<T> parser;
    public final OSCParsedReceiveListener<T> listener;


    OSCParsedReceiveRequest(OSCAddress address, OSCArgsValidator validator, OSCArgsParser<T> parser, OSCParsedReceiveListener<T> listener) {
        super(address, validator);
        this.parser = parser;
        this.listener = listener;
    }

    OSCParsedReceiveRequest(OSCAddress address, OSCArgsParser<T> parser, OSCParsedReceiveListener<T> listener) {
        super(address);
        this.parser = parser;
        this.listener = listener;
    }

}
