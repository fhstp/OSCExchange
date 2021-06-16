package ac.at.fhstp.digitech.oscexchange

/**
 * Function that checks if OSCArgs match some criteria
 */
typealias ArgValidator = (OSCArgs) -> Boolean

/**
 * Contains some universal validators
 */
object ArgValidators {

    /**
     * Matches all args
     */
    @PublicApi
    val noValidation: ArgValidator =
        { true }

    /**
     * Only matches empty args
     */
    @PublicApi
    val hasNoArgs: ArgValidator =
        hasArgCount(0)

    /**
     * Only matches args with a single argument
     */
    @PublicApi
    val hasSingleArg: ArgValidator =
        hasArgCount(1)


    /**
     * Creates a validator that matches a single argument of a specific type
     *
     * @param T the type of argument that should be matched
     * @return The created validator
     */
    @PublicApi
    inline fun <reified T> hasSingleArgOfType(): ArgValidator =
        hasSingleArg.and { args -> args.hasArgOfType<T>(0) }

    /**
     * Creates a validator that matches args with the given number of arguments
     *
     * @param count The number of arguments to match
     * @return The created validator
     */
    @PublicApi
    fun hasArgCount(count: Int): ArgValidator =
        { args -> args.count() == count }


    /**
     * Combines this validator with another one,
     * only matching the given args if they match both validators
     * @param other The other validator
     * @return The new combined validator
     */
    @PublicApi
    fun ArgValidator.and(other: ArgValidator) =
        { args: OSCArgs -> this(args) && other(args) }

    /**
     * Combines this validator with another one,
     * only matching the given args if they match either of the validators
     *
     * @param other The other validator
     * @return The new combined validator
     */
    @PublicApi
    fun ArgValidator.or(other: ArgValidator) =
        { args: OSCArgs -> this(args) || other(args) }

    /**
     * Inverts the validator,
     * so it will match if the args don't match the original
     *
     * @return The inverted validator
     */
    @PublicApi
    fun ArgValidator.inv(): ArgValidator =
        { args -> !this(args) }

}