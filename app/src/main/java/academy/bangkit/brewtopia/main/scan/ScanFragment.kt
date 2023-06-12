package academy.bangkit.brewtopia.main.scan

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import academy.bangkit.brewtopia.R
import academy.bangkit.brewtopia.data.remote.config.ApiConfigScan
import academy.bangkit.brewtopia.data.remote.response.ScanResponse
import academy.bangkit.brewtopia.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ScanFragment : Fragment() {
    private var getFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(), REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_scan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.btn_camera).setOnClickListener {
            startCameraX()
        }
        view.findViewById<Button>(R.id.btn_gallery).setOnClickListener {
            startGallery()
        }
        view.findViewById<Button>(R.id.btn_identify).setOnClickListener {
            upload(getFile)
        }
    }

    private fun upload(
        img: File?
    ) {
        if (img != null) {
            val requestImage = img.asRequestBody("application/binary".toMediaType())
            ApiConfigScan.getApiService().uploadImg(requestImage)
                .enqueue(object : Callback<ScanResponse> {
                    @SuppressLint("SetTextI18n")
                    override fun onResponse(
                        call: Call<ScanResponse>, response: Response<ScanResponse>
                    ) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody != null && !responseBody.error) {
                                val tvResult = view?.findViewById<TextView>(R.id.tv_result_coffee)
                                tvResult?.text = responseBody.message
                                showLoading(false)
                            }
                        } else {
                            Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show()
                            showLoading(false)
                        }
                    }

                    override fun onFailure(call: Call<ScanResponse>, t: Throwable) {
                        Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                        showLoading(false)
                    }

                })
            showLoading(true)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        view?.findViewById<ProgressBar>(R.id.pb_upload)?.visibility =
            if (isLoading) View.VISIBLE else View.GONE
    }

    private fun startCameraX() {
        val intent = Intent(requireContext(), CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, R.string.choose_pic.toString())
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.data?.getSerializableExtra("picture", File::class.java)
            } else {
                @Suppress("DEPRECATION") it.data?.getSerializableExtra(PICTURE)
            } as? File
            it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            myFile?.let { file ->
                getFile = myFile
                view?.findViewById<ImageView>(R.id.iv_camera)
                    ?.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }

        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImage = it.data?.data as Uri
            selectedImage.let { uri ->
                val myFile = uriToFile(uri, requireContext())
                getFile = myFile
                view?.findViewById<ImageView>(R.id.iv_camera)?.setImageURI(uri)
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    requireContext(), R.string.no_permission, Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA
        )
        private const val REQUEST_CODE_PERMISSIONS = 10
        const val PICTURE = "picture"
    }
}