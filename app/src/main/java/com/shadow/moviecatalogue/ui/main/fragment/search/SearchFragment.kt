package com.shadow.moviecatalogue.ui.main.fragment.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadow.moviecatalogue.R
import com.shadow.moviecatalogue.config.Injector
import com.shadow.moviecatalogue.data.model.response.ItemMovie
import com.shadow.moviecatalogue.data.model.response.ItemTv
import com.shadow.moviecatalogue.data.model.response.MovieResponse
import com.shadow.moviecatalogue.data.model.response.TvResponse
import com.shadow.moviecatalogue.presenter.catalogue.CataloguePresenter
import com.shadow.moviecatalogue.ui.BaseActivity
import com.shadow.moviecatalogue.ui.detail.MovieDetailActivity
import com.shadow.moviecatalogue.ui.detail.TvDetailActivity
import com.shadow.moviecatalogue.ui.main.MainContract
import com.shadow.moviecatalogue.util.Utilities
import com.shadow.moviecatalogue.widget.adapter.MovieAdapter
import com.shadow.moviecatalogue.widget.adapter.TvAdapter
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

@Suppress("DEPRECATION")
class SearchFragment : Fragment(), MainContract, MovieAdapter.OnResultClickListener,
    TvAdapter.OnResultClickListener {

    @Inject
    internal lateinit var presenter: CataloguePresenter

    private var page by Delegates.notNull<Int>()
    private lateinit var title: String
    private lateinit var root: View
    private lateinit var tvAdapter: TvAdapter
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_search, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Injector.obtain(context!!).inject(this)

        presenter.setView(this)

        initComponent(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(MOVIE_RESULT, savedMovie)
        outState.putParcelable(TV_RESULT, savedTv)
        super.onSaveInstanceState(outState)
    }

    override fun initComponent(savedInstanceState: Bundle?) {
        with(root) {
            page = 1
            val list = ArrayList<String>()
            list.add(0, "Movie")
            list.add(1, "Tv")

            val adapter = ArrayAdapter<String>(
                context!!,
                R.layout.spinner_item, list
            )
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)

            movieAdapter = MovieAdapter()
            tvAdapter = TvAdapter()
            linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            btnRetry.setOnClickListener {
                setMessageView(View.GONE, View.VISIBLE, View.GONE)
                getListCatalogue()
            }

            with(root) {
                spinner.adapter = adapter
                RvSearch.layoutManager = linearLayoutManager
                RvSearch.setItemViewCacheSize(20)
                RvSearch.isDrawingCacheEnabled = true

                searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        if (query != null) {
                            title = query
                            clearList()
                            setMessageView(View.GONE, View.VISIBLE, View.GONE)
                            getListCatalogue()
                            hideKeyboard()
                        } else {
                            onShowToast("Input is Empty")
                        }
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        //Do Nothing
                        return false
                    }

                })
            }
        }

        if (savedInstanceState?.getParcelable<MovieResponse>(MOVIE_RESULT) != null) {
            setMessageView(View.GONE, View.GONE, View.GONE)

            val resultMovie = savedInstanceState.getParcelable<MovieResponse>(MOVIE_RESULT) as MovieResponse
            setListCatalogueMovie(resultMovie)

        }
        if (savedInstanceState?.getParcelable<TvResponse>(TV_RESULT) != null){
            setMessageView(View.GONE, View.GONE, View.GONE)

            val resultTv = savedInstanceState.getParcelable<TvResponse>(TV_RESULT) as TvResponse
            setListCatalogueTv(resultTv)
        }
    }

    override fun getListCatalogue() {
        if (Utilities.isNetAvailable(context!!)) {
            setMessageView(View.GONE, View.VISIBLE, View.GONE)

            if (root.spinner.selectedItem.equals("Movie")) {
                presenter.getSearchMovieList(title, page)
            } else if (root.spinner.selectedItem.equals("Tv")) {
                presenter.getSearchTvList(title, page)
            }

        } else {
            setMessageView(View.GONE, View.GONE, View.VISIBLE)
        }

    }

    override fun setListCatalogueMovie(response: MovieResponse) {
        val listMovie = response.results
        savedMovie = response

        if (listMovie.isNotEmpty() && page == 1) {
            movieAdapter.setItem(listMovie)
            movieAdapter.setClickListener(this)
            root.RvSearch.adapter = movieAdapter
            root.RvSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(
                    recyclerView: RecyclerView,
                    dx: Int,
                    dy: Int
                ) {
                    super.onScrolled(recyclerView, dx, dy)
                    val countItem = linearLayoutManager.itemCount
                    val lastVisiblePosition =
                        linearLayoutManager.findLastCompletelyVisibleItemPosition()
                    val isLastPosition = countItem.minus(1) == lastVisiblePosition

                    if (isLastPosition && 0 < movieAdapter.itemCount) {
                        page++
                        setMessageView(View.GONE, View.VISIBLE, View.GONE)
                        getListCatalogue()
                    }
                }
            })
        } else if (page > 1) {
            movieAdapter.addItem(listMovie)
        }
    }

    override fun setListCatalogueTv(response: TvResponse) {
        val listTv = response.results
        savedTv = response

        if (listTv.isNotEmpty() && page == 1) {

            tvAdapter.setItem(listTv)
            tvAdapter.setClickListener(this)

            root.RvSearch.adapter = tvAdapter
            root.RvSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(
                    recyclerView: RecyclerView,
                    dx: Int,
                    dy: Int
                ) {
                    super.onScrolled(recyclerView, dx, dy)
                    val countItem = linearLayoutManager.itemCount
                    val lastVisiblePosition =
                        linearLayoutManager.findLastCompletelyVisibleItemPosition()
                    val isLastPosition = countItem.minus(1) == lastVisiblePosition

                    if (isLastPosition && 0 < tvAdapter.itemCount) {
                        page++
                        setMessageView(View.GONE, View.VISIBLE, View.GONE)
                        getListCatalogue()
                    }
                }
            })
        } else if (page > 1) {
            tvAdapter.addItem(listTv)
        }
    }

    override fun setMessageView(EmptyMessage: Int, ProgressBar: Int, ErrorNetwork: Int) {
        emptyMessage.visibility = EmptyMessage
        progressBar.visibility = ProgressBar
        errorNetwork.visibility = ErrorNetwork

    }

    override fun onClick(item: ItemMovie) {
        val intent = Intent(context, MovieDetailActivity::class.java)
        intent.putExtra("MovieDetail", item)
        startActivity(intent)
    }

    override fun onClick(item: ItemTv) {
        val intent = Intent(context, TvDetailActivity::class.java)
        intent.putExtra("TvDetail", item)
        startActivity(intent)
    }

    fun clearList() {
        movieAdapter.clearItem()
        tvAdapter.clearItem()
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onShowToast(message: String) {
        (activity as BaseActivity).showToast(message)
    }

    companion object {
        val MOVIE_RESULT = "movie_result"
        val TV_RESULT = "tv_result"
        var savedMovie: MovieResponse? = null
        var savedTv: TvResponse? = null
    }

}