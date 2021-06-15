package ac.at.fhstp.digitech.oscexchange.requests

import ac.at.fhstp.digitech.oscexchange.errors.OSCException

interface Request {

    companion object {

        val noErrorHandling = { _: OSCException -> }

    }

}