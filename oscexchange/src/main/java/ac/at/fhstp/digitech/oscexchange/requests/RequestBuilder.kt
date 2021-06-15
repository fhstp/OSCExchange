package ac.at.fhstp.digitech.oscexchange.requests

interface RequestBuilder<TRequest : Request> {

    fun build(): TRequest

}