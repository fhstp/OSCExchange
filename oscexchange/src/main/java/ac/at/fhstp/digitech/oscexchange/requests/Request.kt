package ac.at.fhstp.digitech.oscexchange.requests

import ac.at.fhstp.digitech.oscexchange.errors.OSCError

interface Request {

    companion object {

        val noErrorHandling = { _: OSCError -> }

    }

}