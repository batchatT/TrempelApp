package com.example.trempelapp.presentation_layer.main_screen.main_fragments.profile

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain_layer.coroutine_use_cases.LogOutUseCaseImpl
import com.example.domain_layer.coroutine_use_cases.SaveUserImageUseCaseImpl
import com.example.domain_layer.coroutine_use_cases.execute
import com.example.domain_layer.models.MainUserInfo
import com.example.domain_layer.rxjava_use_cases.GetUserDataUseCaseImpl
import com.example.domain_layer.rxjava_use_cases.WriteUserImageUseCaseImpl
import com.example.domain_layer.rxjava_use_cases.execute
import com.example.trempelapp.BaseViewModel
import com.example.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfilePageViewModel @Inject constructor(
    private val logOut: LogOutUseCaseImpl,
    private val getUserData: GetUserDataUseCaseImpl,
    private val writeUserImageUri: WriteUserImageUseCaseImpl,
    private val saveUserImage: SaveUserImageUseCaseImpl
) : BaseViewModel() {

    val onLogOutButtonClicked: SingleLiveEvent<Void>
        get() = _onLogOutButtonClicked
    private val _onLogOutButtonClicked = SingleLiveEvent<Void>()

    val onImageSaved: SingleLiveEvent<Void>
        get() = _onImageSaved
    private val _onImageSaved = SingleLiveEvent<Void>()

    fun onLogOutClick() {
        viewModelScope.launch {
            logOut.execute()
        }
        _onLogOutButtonClicked.call()
    }

    val userData = MutableLiveData<MainUserInfo>()

    val loading = mutableStateOf(false)

    fun getUserInfo() {
        loading.value = true
        getUserData
            .execute()
            .doOnSubscribe {
                isLoadingLiveData.postValue(true)
            }
            .doFinally {
                isLoadingLiveData.postValue(false)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                userData.value = it
                loading.value = false
            }, {
                handleError(it)
            })
            .run(compositeDisposable::add)
    }

    private fun writeUserImageUri(uri: Uri) {
        writeUserImageUri
            .execute(uri)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _onImageSaved.call()
            }, {
                handleError(it)
            })
            .run(compositeDisposable::add)
    }

    fun saveUserImage(image: Bitmap) {
        val waitFor = viewModelScope.async {
            return@async saveUserImage.execute(image)
        }
        writeUserImageUri(waitFor.getCompleted())
    }
}
