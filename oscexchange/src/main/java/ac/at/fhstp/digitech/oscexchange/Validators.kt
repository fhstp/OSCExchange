package ac.at.fhstp.digitech.oscexchange

typealias Validator = (OSCArgs) -> Boolean

object Validators {

    @PublicApi
    val noValidation: Validator =
        { true }

    @PublicApi
    val hasNoArgs: Validator =
        hasArgCount(0)

    @PublicApi
    val hasSingleArg: Validator =
        hasArgCount(1)


    @PublicApi
    inline fun <reified T> hasSingleArgOfType(): Validator =
        hasSingleArg.and { args -> args.hasArgOfType<T>(0) }

    @PublicApi
    fun hasArgCount(count: Int): Validator =
        { args -> args.count() == count }


    @PublicApi
    fun Validator.and(other: Validator) =
        { args: OSCArgs -> this(args) && other(args) }

    @PublicApi
    fun Validator.or(other: Validator) =
        { args: OSCArgs -> this(args) || other(args) }

    @PublicApi
    fun Validator.inv(): Validator =
        { args -> !this(args) }

}