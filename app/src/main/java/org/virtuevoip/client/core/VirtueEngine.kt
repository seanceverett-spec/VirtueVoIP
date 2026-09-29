package org.virtuevoip.client.core

import android.content.Context
import org.linphone.core.*

class VirtueEngine(private val context: Context) {
    private var core: Core? = null

    private val coreListener = object : CoreListenerStub() {
        override fun onCallStateChanged(
            core: Core,
            call: Call,
            state: Call.State,
            message: String
        ) {
            when (state) {
                Call.State.IncomingReceived -> {
                    // Mesh call received
                }
                Call.State.Connected -> {
                    // Session established
                }
                Call.State.End, Call.State.Released -> {
                    // Tear down session
                }
                else -> {}
            }
        }

        override fun onCallEncryptionChanged(
            core: Core,
            call: Call,
            on: Boolean,
            authenticationToken: String?
        ) {
            if (on && !authenticationToken.isNullOrEmpty()) {
                println("VirtueVoIP SAS Token: $authenticationToken")
            }
        }
    }

    fun init() {
        val factory = Factory.instance()
        factory.setDebugMode(false, "VirtueVoIP")

        core = factory.createCore(null, null, context).apply {
            addListener(coreListener)
            mediaEncryption = MediaEncryption.ZRTP
            isAdaptiveRateControlEnabled = true
            
            val opusPayload = payloadTypes.firstOrNull { it.mimeType.equals("opus", ignoreCase = true) }
            opusPayload?.enable(true)

            isNetworkReachable = true
            start()
        }
    }

    fun makeCall(targetMeshIp: String) {
        val targetUri = "sip:call@$targetMeshIp"
        val address = core?.createAddress(targetUri) ?: return
        val params = core?.createCallParams(null)?.apply {
            mediaEncryption = MediaEncryption.ZRTP
        }
        core?.inviteAddressWithParams(address, params)
    }

    fun stop() {
        core?.stop()
        core = null
    }
}
