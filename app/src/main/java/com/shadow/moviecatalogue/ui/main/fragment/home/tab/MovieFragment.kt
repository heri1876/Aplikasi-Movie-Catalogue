package com.shadow.moviecatalogue.ui.main.fragment.home.tab

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadow.moviecatalogue.R
import com.shadow.moviecatalogue.config.Injector
import com.shadow.moviecatalogue.data.model.response.MovieResponse
import com.shadow.moviecatalogue.data.model.response.ItemMovie
import com.shadow.moviecatalogue.data.model.response.TvResponse
import com.shadow.moviecatalogue.presenter.catalogue.CataloguePresenter
import com.shadow.moviecatalogue.ui.BaseActivity
import com.shadow.moviecatalogue.ui.detail.MovieDetailActivity
import com.shadow.moviecatalogue.ui.main.MainContract
import com.shadow.moviecatalogue.util.Utilities
import com.shadow.moviecatalogue.widget.adapter.MovieAdapter
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.fragment_movie.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * A simple [Fragment] subclass.
 */
@Suppress("DEPRECATION")
class MovieFragment : Fragment(), MainContract, MovieAdapter.OnResultClickListener {

    @Inject
    internal lateinit var presenter: CataloguePresenter

    private var page by Delegates.notNull<Int>()
    private lateinit var root: View
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_movie, container, false)

        return root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(MOVIE_RESULT, savedMovie)
        super.onSaveInstanceState(outState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Injector.obtain(context!!).inject(this)

        presenter.setView(this)

        initComponent(savedInstanceState)
    }

    override fun initComponent(savedInstanceState: Bundle?) {
        with(root) {
            page = 1

            movieAdapter = MovieAdapter()
            linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            RvMovie.layoutManager = linearLayoutManager
            RvMovie.setItemViewCacheSize(20)
            RvMovie.isDrawingCacheEnabled = true
            buttonRetry.setOnClickListener {
                getListCatalogue()
            }
        }
        if (savedInstanceState != null) {
            val result = savedInstanceState.getParcelableArrayList<MovieResponse>(MOVIE_RESULT) as ArrayList<MovieResponse>
            setListCatalogueMovie(result[0])
        } else {
            getListCatalogue()
        }
    }

    override fun getListCatalogue() {
        if (Utilities.isNetAvailable(context!!)) {
            setMessageView(View.GONE, View.VISIBLE, View.GONE)
            presenter.getCatalogueMovieList(page)
        } else {
            setMessageView(View.GONE, View.GONE, View.VISIBLE)
        }
    }

    override fun setListCatalogueMovie(response: MovieResponse) {

        val listMovie = response.results
        savedMovie.add(response)

        if (listMovie.isNotEmpty() && page == 1) {

            movieAdapter.setItem(listMovie)
            movieAdapter.setClickListener(this)

            root.RvMovie.adapter = movieAdapter
            root.RvMovie.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
        //Do Nothing
    }

    override fun onClick(item: ItemMovie) {
        val intent = Intent(context, MovieDetailActivity::class.java)
        intent.putExtra("MovieDetail", item)
        startActivity(intent)
    }

    override fun setMessageView(EmptyMessage: Int, ProgressBar: Int, ErrorNetwork: Int){
        root.emptyMessage.visibility = EmptyMessage
        root.progressBar.visibility = ProgressBar
        root.errorNetwork.visibility = ErrorNetwork
    }

    override fun onShowToast(message: String) {
        (activity as BaseActivity).showToast(message)
    }

    companion object {
        val MOVIE_RESULT = "movie_result"
        val savedMovie = ArrayList<MovieResponse>()
    }
}
