package com.example.trempelapp.presentation_layer.main_screen.main_fragments.favourites

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.trempelapp.BaseViewModel
import com.example.trempelapp.data_layer.models.Favourite
import com.example.trempelapp.domain_layer.DeleteFavouriteFromFavouritesUseCaseImpl
import com.example.trempelapp.domain_layer.FindFavouritesUseCaseImpl
import com.example.trempelapp.domain_layer.InsertFavouriteToFavouriteDBImpl
import com.example.trempelapp.domain_layer.execute
import com.example.trempelapp.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

private const val TAG = "FavouritesPageViewModel"

class FavouritesPageViewModel @Inject constructor(
    private val findFavourites: FindFavouritesUseCaseImpl,
    private val deleteFavourite: DeleteFavouriteFromFavouritesUseCaseImpl,
    private val insertFavourite: InsertFavouriteToFavouriteDBImpl
) : BaseViewModel() {

    val adapter by lazy {
        FavouritesRecyclerAdapter(changeStatusFavouriteLiveData, favouriteToRemove).apply {
            setOnProductListener(object : FavouritesRecyclerAdapter.OnFavouriteListener {
                override fun onFavouriteListener(favourite: Favourite) {
                    Log.d(TAG, "onFavouriteListener: clicked")
                }
            })
        }
    }

    val changeStatusFavouriteLiveData = MutableLiveData<Favourite>()
    val favouriteToRemove = SingleLiveEvent<Favourite>()
    val isAddToCartButtonEnabled: SingleLiveEvent<Boolean>
        get() = _isAddToCartButtonEnabled
    val favouriteListLiveData: SingleLiveEvent<List<Favourite>>
        get() = _favouriteListLiveData
    private val _isAddToCartButtonEnabled = SingleLiveEvent<Boolean>()
        .apply {
            value = false
        }
    private val _favouriteListLiveData = SingleLiveEvent<List<Favourite>>()

    private val favouriteList = mutableListOf<Favourite>()

    fun getAllFavourites() {
        findFavourites
            .execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _favouriteListLiveData.value = it
                favouriteList.clear()
                favouriteList.addAll(it)
                updateCartButton()
            }, {
                handleError(it)
            })
            .run(compositeDisposable::add)
    }

    private fun deleteFavouriteFromDB(favourite: Favourite) {
        deleteFavourite
            .execute(favourite)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .run(compositeDisposable::add)
    }

    fun updateCartButton() {
        _isAddToCartButtonEnabled.value = favouriteList.any { it.isChecked }
    }

    fun insertFavourite(position: Int) {
        favouriteToRemove.value?.let {
            insertFavourite
                .execute(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
                .run(compositeDisposable::add)
            favouriteList.add(it)
        }
        adapter.insertItem(position)
        updateCartButton()
    }

    fun removeFavouriteItem(favourite: Favourite) {
        deleteFavouriteFromDB(favourite)
        favouriteList.remove(favourite)
        updateCartButton()
    }
}
