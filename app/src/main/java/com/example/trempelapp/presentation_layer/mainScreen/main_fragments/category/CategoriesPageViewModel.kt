package com.example.trempelapp.presentation_layer.mainScreen.main_fragments.category

import android.util.Log
import com.example.trempelapp.BaseViewModel
import com.example.trempelapp.domain_layer.Category
import com.example.trempelapp.domain_layer.FindCategoriesUseCaseImpl
import com.example.trempelapp.domain_layer.execute
import com.example.trempelapp.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CategoriesPageViewModel @Inject constructor(
    private val findCategories: FindCategoriesUseCaseImpl
) : BaseViewModel() {

    val categoryListLiveData: SingleLiveEvent<List<Category>>
        get() = _categoryListLiveData
    private val _categoryListLiveData = SingleLiveEvent<List<Category>>()

    val onCategoryClickedLiveData: SingleLiveEvent<Boolean>
        get() = _onCategoryClickedLiveData
    private val _onCategoryClickedLiveData = SingleLiveEvent<Boolean>()

    val categoryTitleLiveData: SingleLiveEvent<String>
        get() = _categoryTitleLiveData
    private val _categoryTitleLiveData = SingleLiveEvent<String>()

    init {
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

    val adapter by lazy {
        CategoriesRecyclerAdapter().apply {
            setOnCategoryListener(object : CategoriesRecyclerAdapter.OnCategoryListener {
                override fun onCategoryListener(category: Category) {
                    _categoryTitleLiveData.value = category.title
                    _onCategoryClickedLiveData.value = true
                }
            })
        }
    }
}
