package com.example.trempelapp.domain_layer

import com.example.trempelapp.data_layer.ProductRepository
import io.reactivex.Single
import javax.inject.Inject

class FindCategoriesUseCaseImpl @Inject constructor(
    private val repository: ProductRepository,
) : UseCase<Nothing?, Single<List<Category>>> {

    override fun execute(params: Nothing?): Single<List<Category>> {
        return repository.fetchAllCategories()
            .map { categories ->
                categories.mapNotNull { it.toCategory() }
                    .plus(Category.ALL)
                    .sorted()
            }
    }

    private fun String.toCategory(): Category? {
        return when (this) {
            Category.ALL.title -> Category.ALL
            Category.MENS_CLOTHING.title -> Category.MENS_CLOTHING
            Category.WOMENS_CLOTHING.title -> Category.WOMENS_CLOTHING
            Category.ELECTRONICS.title -> Category.ELECTRONICS
            Category.JEWELERY.title -> Category.JEWELERY
            else -> null
        }
    }
}
