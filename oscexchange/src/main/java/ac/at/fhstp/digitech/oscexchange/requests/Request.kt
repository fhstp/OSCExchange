package ac.at.fhstp.digitech.oscexchange.requests

import ac.at.fhstp.digitech.oscexchange.OSCArgs
import ac.at.fhstp.digitech.oscexchange.errors.OSCError

internal interface Request {

    companion object {


        val emptyArgsGenerator = { _: Unit -> OSCArgs.empty }

        val noErrorHandling = { _: OSCError -> }

        val noSuccessHandling = { _: Unit -> }

    }

}