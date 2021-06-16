package ac.at.fhstp.digitech.oscexchange

typealias ArgGenerator = () -> OSCArgs

object ArgGenerators {

    val empty: ArgGenerator =
        { OSCArgs.empty }


    fun constant(args: OSCArgs): ArgGenerator =
        { args }

}