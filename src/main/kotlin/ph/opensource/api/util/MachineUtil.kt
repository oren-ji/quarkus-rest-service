package ph.opensource.api.util

import java.net.InetAddress
import java.net.NetworkInterface
import java.util.*

/**
 * Machine util
 *
 * Purpose: Retrieves machine details for audit use.
 *
 * @constructor Create empty Machine util
 */
class MachineUtil {
    companion object {
        private const val IPV6_LINK_LOCAL_ADDRESS = "fe80"
        private const val IPV6_LOOPBACK_ADDRESS = "0:0:0:0:0:0:0:1"
        private const val IPV4_LOOPBACK_ADDRESS = "127.0.0.1"

        fun getMachineIpAddress(): String {
            val ipAddresses = arrayListOf<String>()

            val e: Enumeration<*> = NetworkInterface.getNetworkInterfaces()

            while (e.hasMoreElements()) {
                val n = e.nextElement() as NetworkInterface
                val ee: Enumeration<*> = n.inetAddresses

                while (ee.hasMoreElements()) {
                    val i = ee.nextElement() as InetAddress
                    val hostAddress = i.hostAddress

                    if (!hostAddress.startsWith(IPV6_LINK_LOCAL_ADDRESS)
                        && !hostAddress.equals(IPV6_LOOPBACK_ADDRESS)
                        && !hostAddress.equals(IPV4_LOOPBACK_ADDRESS)) {
                        ipAddresses.add(i.hostAddress)
                    }
                }
            }

            return ipAddresses.joinToString(",")
        }
    }
}