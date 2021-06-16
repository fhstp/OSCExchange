package ac.at.fhstp.digitech.oscexchange

typealias ArgValidator = (OSCArgs) -> Boolean

object Validators {

    @PublicApi
    val noValidation: ArgValidator =
        { true }

    @PublicApi
    val hasNoArgs: ArgValidator =
        hasArgCount(0)

    @PublicApi
    val hasSingleArg: ArgValidator =
        hasArgCount(1)


    @PublicApi
    inline fun <reified T> hasSingleArgOfType(): ArgValidator =
        hasSingleArg.and { args -> args.hasArgOfType<T>(0) }

    @PublicApi
    fun hasArgCount(count: Int): ArgValidator =
        { args -> args.count() == count }


    @PublicApi
    fun ArgValidator.and(other: ArgValidator) =
        { args: OSCArgs -> this(args) && other(args) }

    @PublicApi
    fun ArgValidator.or(other: ArgValidator) =
        { args: OSCArgs -> this(args) || other(args) }

    @PublicApi
    fun ArgValidator.inv(): ArgValidator =
        { args -> !this(args) }

}