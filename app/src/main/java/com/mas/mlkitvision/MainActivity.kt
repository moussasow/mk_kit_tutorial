package com.mas.mlkitvision

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
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
        ScannerActivity.startScanner(this) {
            // handle result
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