package ac.at.fhstp.digitech.oscexchange

import com.illposed.osc.transport.udp.OSCPortIn
import com.illposed.osc.transport.udp.OSCPortOut

data class RunnableOSCExchange(
    val exchange: OSCExchange,
    val inPort: OSCPortIn,
    val outPort: OSCPortOut
)