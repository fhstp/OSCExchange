package ac.at.fhstp.digitech.oscexchange

/**
 * A function that generates OSCArgs
 */
typealias ArgGenerator = () -> OSCArgs

/**
 * Contains some universal arg-generators
 */
object ArgGenerators {

    /**
     * Generates empty OSCArgs
     */
    val empty: ArgGenerator =
        { OSCArgs.empty }


    /**
     * Always generates the given args
     *
     * @param args The args which this generator should generate
     * @return The generator
     */
    fun constant(args: OSCArgs): ArgGenerator =
        { args }

}