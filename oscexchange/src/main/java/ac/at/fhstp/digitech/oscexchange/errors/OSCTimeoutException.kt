package ac.at.fhstp.digitech.oscexchange.errors

import ac.at.fhstp.digitech.oscexchange.PublicApi

@PublicApi
class OSCTimeoutException(
    @PublicApi
    val timeout: Long
) : OSCException("The request timed out after $timeout millis", null)