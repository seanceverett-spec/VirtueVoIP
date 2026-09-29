package org.virtuevoip.client.core

import android.content.Context
import org.linphone.core.Core
import org.linphone.core.Factory
import org.linphone.core.MediaEncryption

class VirtueEngine(private val context: Context) {
    private var core: Core? = null

    fun initialize() {
        val factory = Factory.instance()
        core = factory.createCore(null, null, context).apply {
            audioPayloadTypes.firstOrNull { it.mimeType.equals("opus", ignoreCase = true) }?.enable(true)
            isNetworkReachable = true
            start()
        }
    }

    fun makeCall(targetMeshIp: String) {
        val targetUri = "sip:call@$targetMeshIp"
        val currentCore = core ?: return
        val address = currentCore.createAddress(targetUri) ?: return
        val params = currentCore.createCallParams(null) ?: return
        params.mediaEncryption = MediaEncryption.ZRTP
        currentCore.inviteAddressWithParams(address, params)
    }

    fun stop() {
        core?.stop()
        core = null
    }
}
