package com.shadow.moviecatalogue.presenter.catalogue

import android.util.Log
import android.view.View
import com.shadow.moviecatalogue.repository.catalogue.CatalogueRepository
import com.shadow.moviecatalogue.ui.main.MainContract
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@Suppress("DEPRECATION")
class CataloguePresenter @Inject
constructor(private val repository: CatalogueRepository) : CataloguePresenterContract {

    private lateinit var view: MainContract
    private var disposable: Disposable? = null

    override fun setView(view: MainContract) {
        this.view = view
    }

    override fun getCatalogueMovieList(page: Int) {
        if (disposable != null) {
            Log.i(TAG, "Still doing job, cancelling...")
            return
        }

        disposable = repository.getPopularMovie(page)
            .subscribe({ response ->
                dispose()
                view.setListCatalogueMovie(response)
                view.setMessageView(View.GONE, View.GONE, View.GONE)
            }, { throwable ->
                dispose()
                view.setMessageView(View.GONE, View.GONE, View.VISIBLE)
            })
    }

    override fun getCatalogueTvList(page: Int) {
        if (disposable != null) {
            Log.i(TAG, "Still doing job, cancelling...")
            return
        }

        disposable = repository.getPopularTv(page)
            .subscribe({ response ->
                dispose()
                view.setListCatalogueTv(response)
                view.setMessageView(View.GONE, View.GONE, View.GONE)
            }, {
                dispose()
                view.setMessageView(View.GONE, View.GONE, View.VISIBLE)
            })
    }

    override fun getSearchMovieList(title: String, page: Int) {
        if (disposable != null) {
            Log.i(TAG, "Still doing job, cancelling...")
            return
        }

        disposable = repository.getSearchMovie(title, page)
            .subscribe({ response ->
                dispose()
                view.setListCatalogueMovie(response)
            }, {
                dispose()
                view.setMessageView(View.GONE, View.GONE, View.VISIBLE)
            })
    }

    override fun getSearchTvList(title: String, page: Int) {
        if (disposable != null) {
            Log.i(TAG, "Still doing job, cancelling...")
            return
        }

        disposable = repository.getSearchTv(title, page)
            .subscribe({ response ->
                dispose()
                view.setListCatalogueTv(response)
            }, {
                dispose()
                view.setMessageView(View.GONE, View.GONE, View.VISIBLE)
            })
    }

    private fun dispose() {
        disposable?.let {
            if (!disposable!!.isDisposed) {
                disposable!!.dispose()
            }

            disposable = null
        }
    }

    companion object {
        private const val TAG = "CataloguePresenter"
    }

}