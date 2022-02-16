package com.example.trempelapp.presentation_layer.main_screen.main_fragments.category

import android.util.Log
import com.example.trempelapp.BaseViewModel
import com.example.trempelapp.domain_layer.rxjava.Category
import com.example.trempelapp.domain_layer.rxjava.FindCategoriesUseCaseImpl
import com.example.trempelapp.domain_layer.rxjava.execute
import com.example.trempelapp.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CategoriesPageViewModel @Inject constructor(
    private val findCategories: FindCategoriesUseCaseImpl,
) : BaseViewModel() {

    val adapter by lazy {
        CategoriesRecyclerAdapter().apply {
            setOnCategoryListener { category ->
                _categoryTitleLiveData.value = category.stringId
                _onCategoryClickedLiveData.call()
            }
        }
    }

    val categoryListLiveData: SingleLiveEvent<List<Category>>
        get() = _categoryListLiveData
    private val _categoryListLiveData = SingleLiveEvent<List<Category>>()

    val onCategoryClickedLiveData: SingleLiveEvent<Void>
        get() = _onCategoryClickedLiveData
    private val _onCategoryClickedLiveData = SingleLiveEvent<Void>()

    val categoryTitleLiveData: SingleLiveEvent<Int>
        get() = _categoryTitleLiveData
    private val _categoryTitleLiveData = SingleLiveEvent<Int>()

    fun getAllCategories() {
        findCategories.execute()
            .doOnSubscribe {
                isLoadingLiveData.postValue(true)
            }
            .doFinally {
                isLoadingLiveData.postValue(false)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _categoryListLiveData.value = it
                Log.d(javaClass.name, "$it")
            }, {
                handleError(it)
                Log.d(javaClass.name, "$it")
            }).run(compositeDisposable::add)
    }
}
