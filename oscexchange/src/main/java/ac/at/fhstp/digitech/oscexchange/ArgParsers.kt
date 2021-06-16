package ac.at.fhstp.digitech.oscexchange

/**
 * Function that converts OSCArgs to some other type.
 * The result might be null if no parsing was possible
 */
typealias ArgParser<TParsed> = (OSCArgs) -> TParsed?