package com.example.trempelapp.presentation_layer.main_screen.main_fragments.profile

import android.graphics.Bitmap
import android.net.Uri
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.domain_layer.coroutine_use_cases.GetGpsStatusUseCaseImpl
import com.example.domain_layer.coroutine_use_cases.LogOutUseCaseImpl
import com.example.domain_layer.coroutine_use_cases.SaveUserImageUseCaseImpl
import com.example.domain_layer.coroutine_use_cases.execute
import com.example.domain_layer.models.ProfileState
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
    private val saveUserImage: SaveUserImageUseCaseImpl,
    private val gpsStatus: GetGpsStatusUseCaseImpl,
    private val fusedLocationProviderManager: FusedLocationProviderManager
) : BaseViewModel() {

    val onLogOutButtonClicked: SingleLiveEvent<Void>
        get() = _onLogOutButtonClicked
    private val _onLogOutButtonClicked = SingleLiveEvent<Void>()

    val gpsStatusLiveData: LiveData<Boolean>
        get() = _gpsStatusLiveData
    private val _gpsStatusLiveData = SingleLiveEvent<Boolean>()

    val profileState: LiveData<ProfileState>
        get() = _profileState
    private val _profileState = SingleLiveEvent<ProfileState>()
        .apply {
            value = ProfileState()
        }

    fun onLogOutClick() {
        viewModelScope.launch {
            logOut.execute()
        }
        _onLogOutButtonClicked.call()
    }

    fun getGpsStatus() {
        viewModelScope.launch {
            _gpsStatusLiveData.value = gpsStatus.execute()
        }
    }

    fun initFusedLocationProviderManager(activity: FragmentActivity) {
        fusedLocationProviderManager.create(activity)
    }

    fun getUserLocation() {
        fusedLocationProviderManager.getLocation(_profileState)
    }

    fun getUserInfo() {
        _profileState.value = _profileState.value?.copy(isLoading = true)
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
                _profileState.value = _profileState.value?.copy(user = it, isLoading = false)
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
            .subscribe()
            .run(compositeDisposable::add)
    }

    fun saveUserImage(image: Bitmap) {

        writeUserImageUri(
            viewModelScope.async launch@{

                return@launch saveUserImage.execute(image)
            }.getCompleted()
        )
    }
}
