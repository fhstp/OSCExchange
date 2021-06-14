package ac.at.fhstp.digitech.oscexchange

/**
 * Checks if OSCArgs match given criteria
 */
fun interface OSCArgsValidator {
    /**
     * Checks if the given args match this validators criteria
     *
     * @param args The args to check
     * @return True if the args match the validators criteria
     */
    @PublicApi
    fun isValid(args: OSCArgs): Boolean

    /**
     * Combines this validator with a second one so that the result is only valid if it passes both of them
     *
     * @param other The other validator
     * @return The combined validator
     */
    @PublicApi
    fun and(other: OSCArgsValidator): OSCArgsValidator {
        return OSCArgsValidator { data: OSCArgs ->
            isValid(data) && other.isValid(
                data
            )
        }
    }

    /**
     * Combines this validator with a second one so that the result is valid if it passes either of them
     *
     * @param other The other validator
     * @return The combined validator
     */
    @PublicApi
    fun or(other: OSCArgsValidator): OSCArgsValidator {
        return OSCArgsValidator { data: OSCArgs ->
            isValid(data) || other.isValid(
                data
            )
        }
    }

    /**
     * Combines this validator with a second one so that the result is only valid if it passes the first but not the second
     *
     * @param other The other validator
     * @return The combined validator
     */
    @PublicApi
    fun butNot(other: OSCArgsValidator): OSCArgsValidator {
        return OSCArgsValidator { data: OSCArgs ->
            isValid(data) && !other.isValid(
                data
            )
        }
    }

    companion object {
        /**
         * Creates a validator that checks if the given OSCArgs have the specified number of arguments
         *
         * @param count The number that should be checked for
         * @return The created validator
         */
        @PublicApi
        fun hasCount(count: Int): OSCArgsValidator {
            return OSCArgsValidator { args: OSCArgs ->
                OSCArgs.argCount(args) == count
            }
        }

        /**
         * Creates a validator that checks if the given OSCArgs has a single argument
         *
         * @return The created validator
         */
        @PublicApi
        val isSingle: OSCArgsValidator
            get() = hasCount(1)

        /**
         * Creates a validator that checks if the given OSCArgs has an argument of the given type at a specific index
         *
         * @param index The index to check for the argument
         * @param type  The type of the argument
         * @return The created validator
         */
        @PublicApi
        fun hasArgOfType(index: Int, type: Class<*>): OSCArgsValidator {
            return OSCArgsValidator { args: OSCArgs ->
                OSCArgs.hasArg(args, index, type)
            }
        }

        /**
         * Creates a validator that checks if the given OSCArgs has a single argument of the given type
         *
         * @param type The type of the argument
         * @return The created validator
         */
        @PublicApi
        fun isSingleOfType(type: Class<*>): OSCArgsValidator {
            return isSingle.and(hasArgOfType(0, type))
        }
    }
}