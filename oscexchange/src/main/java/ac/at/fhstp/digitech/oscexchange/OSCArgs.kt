package ac.at.fhstp.digitech.oscexchange

import java.util.*

/**
 * Stores arguments that can be sent or received over OSC
 */
class OSCArgs private constructor(private val args: Array<out Any>) {

    companion object {

        val empty = multiple()

        /**
         * Creates OSCArgs from a single argument
         *
         * @param arg The argument
         * @return The created OSCArgs
         */
        @PublicApi
        fun single(arg: Any) =
            multiple(arg)

        /**
         * Creates OSCArgs from multiple arguments
         *
         * @param args The arguments
         * @return The created OSCArgs
         */
        @PublicApi
        fun multiple(vararg args: Any) =
            OSCArgs(args)

        /**
         * Creates OSCArgs from a list of arguments
         *
         * @param args The arguments
         * @return The created OSCArgs
         */
        @PublicApi
        fun ofList(args: List<Any>) =
            OSCArgs(args.toTypedArray())

    }

    /**
     * Counts the number of arguments in the given OSCArgs
     *
     * @return The number of arguments
     */
    @PublicApi
    fun count(): Int =
        args.size

    fun getArg(index: Int) =
        if (count() > index) Optional.of(args[index])
        else Optional.empty()

    /**
     * Tries to get an argument of a specific type from a specific index. If no such argument is found, an empty Optional is returned
     *
     * @param index The index of the argument
     * @param <T>   The type of the argument
     * @return The optional result
    </T> */
    @PublicApi
    inline fun <reified T> tryGetArgOfType(index: Int): Optional<T> =
        getArg(index)
            .flatMap {
                if (it is T) Optional.of(it)
                else Optional.empty()
            }

    /**
     * Checks if the OSCArgs has an argument of a specific type from a specific index
     *
     * @param index The index of the argument
     * @param <T>   The type of the argument
     * @return True if the OSCArgs has the searched argument
    </T> */
    @PublicApi
    inline fun <reified T> hasArgOfType(index: Int): Boolean =
        tryGetArgOfType<T>(index).isPresent

    @PublicApi
    fun asList() =
        args.toList()

}