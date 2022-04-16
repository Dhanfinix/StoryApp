package com.dhandev.storyapp.view.add

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dhandev.storyapp.R
import com.dhandev.storyapp.api.ApiConfig
import com.dhandev.storyapp.databinding.ActivityAddStoryBinding
import com.dhandev.storyapp.model.FileUploadResponse
import com.dhandev.storyapp.rotateBitmap
import com.dhandev.storyapp.uriToFile
import com.dhandev.storyapp.view.login.LoginActivity
import com.dhandev.storyapp.view.main.MainActivity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AddStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddStoryBinding
    private var getFile: File? = null

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        binding.apply {
            btnKamera.setOnClickListener {
                val intent = Intent(this@AddStoryActivity, CameraActivity::class.java)
                launcherIntentCameraX.launch(intent)
            }
            btnGaleri.setOnClickListener {
                val intent = Intent()
                intent.action = Intent.ACTION_GET_CONTENT
                intent.type = "image/*"
                val chooser = Intent.createChooser(intent, "Choose a Picture")
                launcherIntentGallery.launch(chooser)
            }
            btnUpload.setOnClickListener {
                if (getFile != null && binding.etDesc.text.toString() != "") {
                    val file = getFile as File
                    val edDescription = etDesc.text.toString()
                    val description = edDescription.toRequestBody("text/plain".toMediaType())
                    val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                    val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                        "photo",
                        file.name,
                        requestImageFile
                    )

                    val service = ApiConfig.getApiService().uploadImage(imageMultipart, description)
                    service.enqueue(object: Callback<FileUploadResponse> {
                        override fun onResponse(
                            call: Call<FileUploadResponse>,
                            response: Response<FileUploadResponse>
                        ) {
                            if (response.isSuccessful) {
                                val responseBody = response.body()
                                if (responseBody != null && !responseBody.error) {
                                    Toast.makeText(this@AddStoryActivity, responseBody.message, Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this@AddStoryActivity, MainActivity::class.java))
                                    finish()
                                }
                            } else {
                                Toast.makeText(this@AddStoryActivity, response.message(), Toast.LENGTH_SHORT).show()
                            }                        }

                        override fun onFailure(call: Call<FileUploadResponse>, t: Throwable) {
                            Toast.makeText(this@AddStoryActivity, "Gagal instance Retrofit", Toast.LENGTH_SHORT).show()
                        }

                    })

                } else {
                    Toast.makeText(this@AddStoryActivity, "Gambar dan deskripsi tidak boleh kosong.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            getFile = myFile
            val result = rotateBitmap(
                BitmapFactory.decodeFile(myFile.path),
                isBackCamera
            )

            binding.previewImage.setImageBitmap(result)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this)
            getFile = myFile
            binding.previewImage.setImageURI(selectedImg)
        }
    }


    companion object {
        const val CAMERA_X_RESULT = 200

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}