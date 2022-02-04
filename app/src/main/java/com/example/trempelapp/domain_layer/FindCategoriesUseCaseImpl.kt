package com.example.trempelapp.domain_layer

import com.example.trempelapp.data_layer.repositories.ProductRepository
import com.example.trempelapp.utils.CATEGORY_ALL
import com.example.trempelapp.utils.CATEGORY_ELECTRONICS
import com.example.trempelapp.utils.CATEGORY_JEWELERY
import com.example.trempelapp.utils.CATEGORY_MENS
import com.example.trempelapp.utils.CATEGORY_WOMENS
import io.reactivex.Single
import javax.inject.Inject

class FindCategoriesUseCaseImpl @Inject constructor(
    private val repository: ProductRepository,
) : UseCase<Nothing?, Single<List<Category>>> {

    override fun execute(params: Nothing?): Single<List<Category>> {
        return repository.fetchAllCategories()
            .map { categories ->
                categories
                    .mapNotNull { it.toCategory() }
                    .plus(Category.ALL)
                    .sorted()
            }
    }

    private fun String.toCategory(): Category? {
        return when (this) {
            CATEGORY_ALL -> Category.ALL
            CATEGORY_MENS -> Category.MENS_CLOTHING
            CATEGORY_WOMENS -> Category.WOMENS_CLOTHING
            CATEGORY_ELECTRONICS -> Category.ELECTRONICS
            CATEGORY_JEWELERY -> Category.JEWELERY
            else -> null
        }
    }
}
