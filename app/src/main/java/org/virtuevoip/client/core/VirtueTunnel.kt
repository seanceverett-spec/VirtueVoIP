package org.virtuevoip.client.core

import android.content.Context
import com.wireguard.android.backend.Backend
import com.wireguard.android.backend.GoBackend
import com.wireguard.android.backend.Tunnel
import com.wireguard.config.Config
import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets

class VirtueTunnel(private val context: Context) {
    private val backend: Backend = GoBackend(context)
    private val tunnelName = "virtue0"

    private class SimpleTunnel(private val name: String) : Tunnel {
        override fun getName(): String = name
        override fun onStateChange(newState: Tunnel.State) {}
    }

    fun startMeshTunnel(
        privateKeyHex: String,
        meshIp: String,
        partnerPublicKeyHex: String,
        partnerEndpoint: String
    ) {
        val configFile = """
            [Interface]
            PrivateKey = $privateKeyHex
            Address = $meshIp/24

            [Peer]
            PublicKey = $partnerPublicKeyHex
            Endpoint = $partnerEndpoint
            AllowedIPs = 100.64.0.0/24
            PersistentKeepalive = 25
        """.trimIndent()

        val config = Config.parse(ByteArrayInputStream(configFile.toByteArray(StandardCharsets.UTF_8)))
        val tunnel = SimpleTunnel(tunnelName)
        backend.setState(tunnel, Tunnel.State.UP, config)
    }

    fun stopMeshTunnel() {
        val tunnel = SimpleTunnel(tunnelName)
        backend.setState(tunnel, Tunnel.State.DOWN, null)
    }
}
