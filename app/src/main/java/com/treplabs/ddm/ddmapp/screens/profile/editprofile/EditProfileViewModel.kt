package com.treplabs.ddm.ddmapp.screens.profile.editprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.storage.FirebaseStorage
import com.treplabs.ddm.Constants
import com.treplabs.ddm.base.BaseViewModel
import com.treplabs.ddm.ddmapp.PrefsValueHelper
import com.treplabs.ddm.ddmapp.datasources.repositories.FirebaseAuthRepository
import com.treplabs.ddm.ddmapp.models.request.User
import com.treplabs.ddm.ddmapp.screens.profile.editprofile.ImageUploadStatus.*
import com.treplabs.ddm.extensions.resize
import com.treplabs.ddm.networkutils.GENERIC_ERROR_MESSAGE
import com.treplabs.ddm.networkutils.LoadingStatus
import com.treplabs.ddm.networkutils.Result
import com.treplabs.ddm.networkutils.disposeBy
import com.treplabs.ddm.utils.Event
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.io.File
import javax.inject.Inject

enum class ImageUploadStatus { NOT_UPLOADED, UPLOADING, SUCCESS, FAILED }

class EditProfileViewModel @Inject constructor(
   private  val prefsValueHelper: PrefsValueHelper,
    private val authRepository: FirebaseAuthRepository
) : BaseViewModel() {

    private val _imageUploadStatus = MutableLiveData<ImageUploadStatus>(NOT_UPLOADED)
    val imageUploadStatus: LiveData<ImageUploadStatus>
        get() = _imageUploadStatus

    private val _user = MutableLiveData<User>(authRepository.getUser())

    val user: LiveData<User>
        get() = _user

    private val _userInfoUpdated = MutableLiveData<Event<Boolean>>()
    val userInfoUpdated: LiveData<Event<Boolean>>
        get() = _userInfoUpdated

    private val imageStorageRef by lazy {
        FirebaseStorage.getInstance().reference.child(Constants.CloudStoragePaths.IMAGES)
            .child(user.value!!.firebaseUid!!)
    }

    fun updateUserInfo(firstName: String, lastName: String, phoneNumber: String) {
        _loadingStatus.value = LoadingStatus.Loading("Please wait . . .")
        val user = _user.value!!
        val updatedUser = User(user.firebaseUid, firstName, lastName, phoneNumber, user.email, user.profileImageUrl)
        authRepository.saveUserInfo(updatedUser).subscribeBy(onComplete = {
             prefsValueHelper.saveUser(user)
            _userInfoUpdated.value = Event(true)
            _loadingStatus.value = LoadingStatus.Success
        }, onError = {
            _loadingStatus.value = LoadingStatus.Error("-1", it.message ?: GENERIC_ERROR_MESSAGE)
        }).disposeBy(disposeBag)
    }

    fun uploadProfilePicture(file: File) {
        _imageUploadStatus.value = UPLOADING
        getScaledFile(file)
            .flatMap { resizedFile ->
                authRepository.uploadFile(resizedFile, imageStorageRef)
            }
            .subscribeBy {
                when (it) {
                    is Result.Success -> {
                        _user.value!!.profileImageUrl = it.data.toString()
                        _imageUploadStatus.value = SUCCESS
                    }
                    is Result.Error -> {
                        _imageUploadStatus.value = FAILED
                        _loadingStatus.value = LoadingStatus.Error(it.errorCode, it.errorMessage)
                    }
                }
            }.disposeBy(disposeBag)
    }

    private fun getScaledFile(file: File): Single<File> {
        return Single.fromCallable {
            file.resize(400, 600)
            file
        }.subscribeOn(Schedulers.io())
    }

    override fun addAllLiveDataToObservablesList() {
    }
}
