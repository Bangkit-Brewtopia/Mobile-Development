package academy.bangkit.brewtopia.main.scan

import academy.bangkit.brewtopia.R
import academy.bangkit.brewtopia.databinding.ActivityCameraBinding
import academy.bangkit.brewtopia.utils.createFile
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraControl
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.google.android.material.switchmaterial.SwitchMaterial

class CameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraBinding
    private var imageCapture: ImageCapture? = null
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startCamera()
        supportActionBar?.hide()

        binding.switchCamera.setOnClickListener {
            cameraSelector =
                if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) CameraSelector.DEFAULT_FRONT_CAMERA
                else CameraSelector.DEFAULT_BACK_CAMERA
            startCamera()
        }
        binding.captureImage.setOnClickListener {
            takePhoto()
        }
        binding.switchFlash.setOnCheckedChangeListener { _, isChecked ->
            startCamera()
        }
    }

    private fun startCamera() {

        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                val camera = cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )
                val flash = findViewById<SwitchMaterial>(R.id.switchFlash)
                if (camera.cameraInfo.hasFlashUnit() && flash.isChecked) {
                    camera.cameraControl.enableTorch(true)
                    flash.setThumbResource(R.drawable.ic_flashon_fix)
                } else if (!flash.isChecked){
                    flash.setThumbResource(R.drawable.ic_flashoff_fix)
                }
                if (camera.cameraInfo.hasFlashUnit()) {
                    flash.isEnabled = true
                } else if (!camera.cameraInfo.hasFlashUnit()) {
                    flash.isEnabled = false
                }
            } catch (exc: Exception) {
                Toast.makeText(
                    this@CameraActivity, "Gagal memunculkan kamera.", Toast.LENGTH_SHORT
                ).show()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val photoFile = createFile(application)

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Toast.makeText(
                        this@CameraActivity, "Gagal mengambil gambar.", Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val intent = Intent()
                    intent.putExtra("picture", photoFile)
                    intent.putExtra(
                        "isBackCamera", cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA
                    )
                    setResult(ScanFragment.CAMERA_X_RESULT, intent)
                    finish()
                }
            })
    }
}