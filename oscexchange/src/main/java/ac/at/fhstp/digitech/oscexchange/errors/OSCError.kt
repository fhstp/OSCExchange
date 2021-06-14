package ac.at.fhstp.digitech.oscexchange.errors

import ac.at.fhstp.digitech.oscexchange.PublicApi

/**
 * Base-class for all errors that can occur during an OSCExchange
 */
abstract class OSCError protected constructor(
    /**
     * The exception that occurred. Null if no exception occurred
     */
    @PublicApi val exception: Exception?
)