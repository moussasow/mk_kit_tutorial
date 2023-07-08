package com.mas.mlkitvision

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.google.mlkit.vision.barcode.common.Barcode
import com.mas.mlkitvision.databinding.ActivityMainBinding
import com.mas.mlkitvision.ui.ScannerActivity
import com.mas.mlkitvision.util.cameraPermissionRequest
import com.mas.mlkitvision.util.isPermissionGranted
import com.mas.mlkitvision.util.openCameraPermissionSettings

class MainActivity : AppCompatActivity() {

    private val cameraPermission = android.Manifest.permission.CAMERA
    private lateinit var binding: ActivityMainBinding

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            startScanner()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCamera.setOnClickListener {
            requestCameraAndStartScan()
        }
    }

    private fun requestCameraAndStartScan() {
        if (isPermissionGranted(cameraPermission)) {
            startScanner()
        } else {
            requestCameraPermission()
        }
    }

    private fun startScanner() {
        // Start scan
        ScannerActivity.startScanner(this) { barcodes ->
            // handle result
            barcodes.forEach { barcode ->
                binding.textCode.text = barcode.displayValue.toString()

                when(barcode.valueType) {
                    Barcode.TYPE_URL -> {
                        Log.d("startScanner",  barcode.displayValue.toString())
                    }
                    Barcode.TYPE_TEXT -> {
                        Log.d("startScanner",  barcode.displayValue.toString())
                    }
                    Barcode.TYPE_EMAIL -> {
                        Log.d("startScanner",  barcode.displayValue.toString())
                    }
                    else -> {
                        Log.d("startScanner",  barcode.displayValue.toString())
                    }
                }

            }
        }
    }
    private fun requestCameraPermission() {
        when {
            shouldShowRequestPermissionRationale(cameraPermission) -> {
                cameraPermissionRequest {
                    openCameraPermissionSettings()
                }
            }
            else -> {
                requestPermissionLauncher.launch(cameraPermission)
            }
        }
    }

}