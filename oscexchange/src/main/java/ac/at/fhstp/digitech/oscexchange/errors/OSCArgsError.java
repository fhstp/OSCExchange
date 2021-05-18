package ac.at.fhstp.digitech.oscexchange.errors;

import ac.at.fhstp.digitech.oscexchange.OSCArgs;

public class OSCArgsError extends OSCError {

    public final OSCArgs args;


    public OSCArgsError(OSCArgs args) {
        this.args = args;
    }

}
