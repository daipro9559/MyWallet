package org.walleth.activities.qrscan

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.dai_pc.android_test.R
import kotlinx.android.synthetic.main.activity_scan.*


const val SCAN_REQUEST_CODE = 0x00006983
const val SCAN_RESULT = "scan_result"

fun startScanActivityForResult(activity: AppCompatActivity, requestCode: Int = SCAN_REQUEST_CODE) {
    val intent = Intent(activity, QRScanActivity::class.java)
    activity.startActivityForResult(intent, requestCode)
}

class QRScanActivity : AppCompatActivity() {
    private val videographer = Videographer(this).also {
        it.onSuccessfulScan = this::finishWithResult
    }

    private val cameraPermission = CameraPermission(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)
    }

    override fun onResume() {
        super.onResume()

        if (!cameraPermission.isGranted()) {
            cameraPermission.request()
        } else {
            videographer.open(viewfinderView)
        }
    }

    override fun onPause() {
        super.onPause()

        if (videographer.isOpen) {
            videographer.close()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        cameraPermission.handleRequestResult(requestCode, grantResults)
    }

    private fun finishWithResult(value: String) {
        val result = Intent()
        result.putExtra(SCAN_RESULT, value)
        setResult(Activity.RESULT_OK, result)
        finish()
    }
}

