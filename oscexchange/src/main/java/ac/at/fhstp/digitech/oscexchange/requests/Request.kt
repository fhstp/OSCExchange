package ac.at.fhstp.digitech.oscexchange.requests

import ac.at.fhstp.digitech.oscexchange.errors.OSCError

internal interface Request {

    companion object {

        val noErrorHandling = { _: OSCError -> }

    }

}