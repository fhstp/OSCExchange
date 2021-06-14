package ac.at.fhstp.digitech.oscexchange.requests

internal interface RequestBuilder<TRequest : Request> {

    fun build(): TRequest

}