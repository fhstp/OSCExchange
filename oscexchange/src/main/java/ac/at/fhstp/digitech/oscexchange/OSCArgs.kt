package ac.at.fhstp.digitech.oscexchange

import java.util.*

/**
 * Stores arguments that can be sent or received over OSC
 */
class OSCArgs private constructor(private val args: Array<Any>) {
    companion object {

        val empty = list(listOf())

        /**
         * Creates OSCArgs from a single argument
         *
         * @param arg The argument
         * @return The created OSCArgs
         */
        @PublicApi
        fun single(arg: Any): OSCArgs {
            return multiple(arg)
        }

        /**
         * Creates OSCArgs from multiple arguments
         *
         * @param args The arguments
         * @return The created OSCArgs
         */
        @PublicApi
        fun multiple(vararg args: Any): OSCArgs {
            return OSCArgs(arrayOf(args))
        }

        /**
         * Creates OSCArgs from a list of arguments
         *
         * @param args The arguments
         * @return The created OSCArgs
         */
        @PublicApi
        fun list(args: List<Any>): OSCArgs {
            return OSCArgs(args.toTypedArray())
        }

        /**
         * Counts the number of arguments in the given OSCArgs
         *
         * @param args The args to be counted
         * @return The number of arguments
         */
        @PublicApi
        fun argCount(args: OSCArgs): Int {
            return args.args.size
        }

        /**
         * Tries to get an argument of a specific type from a specific index. If no such argument is found, an empty Optional is returned
         *
         * @param args  The OSCArgs from which to get the argument
         * @param index The index of the argument
         * @param type  The class of the argument
         * @param <T>   The type of the argument
         * @return The optional result
        </T> */
        @PublicApi
        fun <T> getArg(args: OSCArgs, index: Int, type: Class<T>): Optional<T> {
            return if (argCount(args) > index && args.args[index].javaClass == type)
                Optional.of(args.args[index] as T)
            else Optional.empty()
        }

        /**
         * Checks if the OSCArgs has an argument of a specific type from a specific index
         *
         * @param args  The OSCArgs from which to get the argument
         * @param index The index of the argument
         * @param type  The class of the argument
         * @param <T>   The type of the argument
         * @return True if the OSCArgs has the searched argument
        </T> */
        @PublicApi
        fun <T> hasArg(args: OSCArgs, index: Int, type: Class<T>): Boolean {
            return getArg(args, index, type).isPresent
        }

        fun asList(args: OSCArgs): List<Any?> {
            return listOf(*args.args)
        }
    }
}