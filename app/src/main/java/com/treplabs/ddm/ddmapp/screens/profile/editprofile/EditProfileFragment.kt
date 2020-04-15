package com.treplabs.ddm.ddmapp.screens.profile.editprofile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import coil.api.load
import coil.transform.CircleCropTransformation
import com.treplabs.ddm.Constants
import com.treplabs.ddm.R
import com.treplabs.ddm.base.BaseViewModel
import com.treplabs.ddm.base.BaseViewModelFragment
import com.treplabs.ddm.databinding.FragmentEditProfileBinding
import com.treplabs.ddm.databinding.FragmentProfileBinding
import com.treplabs.ddm.ddmapp.models.request.SignUpRequest
import com.treplabs.ddm.ddmapp.screens.diagnosisresult.DiagnoseResultFragmentArgs
import com.treplabs.ddm.ddmapp.validateTextLayouts
import com.treplabs.ddm.extensions.*
import com.treplabs.ddm.utils.EventObserver
import java.io.File
import java.io.IOException

import javax.inject.Inject

class EditProfileFragment : BaseViewModelFragment() {

    companion object {
        private const val CAMERA_AND_STORAGE_PERMISSION_REQUEST_CODE = 100
        private const val REQUEST_IMAGE_CAPTURE_CODE = 101
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: EditProfileViewModel

    lateinit var binding: FragmentEditProfileBinding

    var isSignUpFlow = false

    private val cameraAndStoragePermissions =
        arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    private lateinit var photoFile: File

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        daggerAppComponent.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(EditProfileViewModel::class.java)
        binding.viewModel = viewModel
        isSignUpFlow = EditProfileFragmentArgs.fromBundle(arguments!!).isSignUpFlow

        if (isSignUpFlow) {
            binding.skipProcessLink.underline()
            binding.skipProcessLink.show()
        }

        binding.avatarImageView.setOnClickListener { if (checkPermissions()) startImageCaptureProcess() }
        observeImageLoadingStatus()

        viewModel.userInfoUpdated.observe(this, EventObserver {
            if (it) {
                showDialogWithAction("Profile successfully updated",
                    "Profile updated, please click okay to continue",
                    positiveAction = { if (isSignUpFlow) navigateToSignInPage() else findNavController().popBackStack() }
                )
            }
        })

        binding.skipProcessLink.setOnClickListener {
            navigateToSignInPage()
        }

        binding.updateProfileButton.setOnClickListener {

            if (viewModel.imageUploadStatus.value == ImageUploadStatus.UPLOADING) {
                showMessageDialog(getString(R.string.image_upload_in_progress))
                return@setOnClickListener
            }

            if (viewModel.imageUploadStatus.value != ImageUploadStatus.SUCCESS) {
                showSnackBar(getString(R.string.upload_picture_to_proceed))
                return@setOnClickListener
            }

            if (!(validateTextLayouts(binding.firstNameEditText, binding.lastNameEditText, binding.phoneEditText))
            ) {
                showSnackBar("Enter all form fields to proceed")
                return@setOnClickListener
            }
            // We are clean
            viewModel.updateUserInfo(
                binding.firstNameEditText.stringContent(),
                binding.lastNameEditText.stringContent(),
                binding.phoneEditText.stringContent()
            )
        }

    }

    fun navigateToSignInPage() {
        findNavController().navigate(
            EditProfileFragmentDirections.actionEditProfileFragmentToSignInFragment()
        )
    }

    private fun setUpToolbar() = mainActivity.run {
        val title = if (isSignUpFlow) "Create Profile" else "Update Profile"
        setUpToolBar(title, false)
        invalidateToolbarElevation(0)
    }

    override fun getViewModel(): BaseViewModel = viewModel


    // Image capture and upload boilerplate

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE_CODE && resultCode == Activity.RESULT_OK) {
            binding.avatarImageView.load(photoFile) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            viewModel.uploadProfilePicture(photoFile)
        }
    }

    private fun startImageCaptureProcess() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(mainActivity.packageManager) != null) {
            photoFile = try {
                mainActivity.createImageFile()
            } catch (e: IOException) {
                showSnackBar("Something went wrong")
                return
            }
            val photoUri = FileProvider.getUriForFile(mainActivity, Constants.Misc.FILE_PROVIDER_AUTHORITY, photoFile)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_CODE)
        }
    }

    private fun checkPermissions(): Boolean {
        return if (mainActivity.hasPermissions(cameraAndStoragePermissions)) true
        else {
            requestPermissions(cameraAndStoragePermissions, CAMERA_AND_STORAGE_PERMISSION_REQUEST_CODE)
            false
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == CAMERA_AND_STORAGE_PERMISSION_REQUEST_CODE &&
            confirmPermissionResults(grantResults)
        ) startImageCaptureProcess()
    }

    private fun confirmPermissionResults(results: IntArray): Boolean {
        results.forEach {
            if (it != PackageManager.PERMISSION_GRANTED) return false
        }
        return true
    }

    private fun observeImageLoadingStatus() {
        viewModel.imageUploadStatus.observe(this, Observer {
            it?.let {
                when (it) {
                    ImageUploadStatus.NOT_UPLOADED -> {
                        binding.indeterminateImageUploadProgressBar.hide()
                        binding.imageUploadStatusView.hide()
                    }
                    ImageUploadStatus.UPLOADING -> {
                        binding.imageUploadStatusView.hide()
                        binding.indeterminateImageUploadProgressBar.show()
                    }
                    ImageUploadStatus.SUCCESS -> {
                        binding.imageUploadStatusView.load(R.drawable.image_upload_done_status) {
                            transformations(CircleCropTransformation())
                        }
                        binding.imageUploadStatusView.show()
                        binding.indeterminateImageUploadProgressBar.hide()
                    }
                    ImageUploadStatus.FAILED -> {
                        binding.imageUploadStatusView.load(R.drawable.image_upload_refresh) {
                            transformations(CircleCropTransformation())
                        }
                        binding.imageUploadStatusView.show()
                        binding.indeterminateImageUploadProgressBar.hide()
                    }
                }
            }
        })
    }
}
