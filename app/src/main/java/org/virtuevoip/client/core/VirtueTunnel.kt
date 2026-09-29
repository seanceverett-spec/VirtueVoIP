package org.virtuevoip.client.core

import android.content.Context
import com.wireguard.android.backend.GoBackend
import com.wireguard.config.Config
import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets

class VirtueTunnel(private val context: Context) {
    private val backend = GoBackend(context)
    private val tunnelName = "virtue0"

    fun startMeshTunnel(
        devicePrivateKeyHex: String,
        assignedVirtualIp: String,
        partnerPublicKeyHex: String,
        partnerEndpoint: String
    ) {
        val configFile = """
            [Interface]
            PrivateKey = $devicePrivateKeyHex
            Address = $assignedVirtualIp
            ListenPort = 51820

            [Peer]
            PublicKey = $partnerPublicKeyHex
            Endpoint = $partnerEndpoint
            AllowedIPs = 100.64.0.0/24
            PersistentKeepalive = 25
        """.trimIndent()

        val config = Config.parse(ByteArrayInputStream(configFile.toByteArray(StandardCharsets.UTF_8)))
        val tunnel = com.wireguard.android.backend.Tunnel { tunnelName }
        backend.setState(tunnel, com.wireguard.android.backend.Tunnel.State.UP, config)
    }

    fun stopMeshTunnel() {
        val tunnel = com.wireguard.android.backend.Tunnel { tunnelName }
        backend.setState(tunnel, com.wireguard.android.backend.Tunnel.State.DOWN, null)
    }
}
