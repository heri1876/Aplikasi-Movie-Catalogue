package com.shadow.favoritecataloguemovie.ui.main.tab

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.shadow.favoritecataloguemovie.R
import com.shadow.favoritecataloguemovie.config.Injector
import com.shadow.favoritecataloguemovie.data.model.response.ItemMovie
import com.shadow.favoritecataloguemovie.data.model.response.ItemTv
import com.shadow.favoritecataloguemovie.presenter.favorite.FavoritePresenter
import com.shadow.favoritecataloguemovie.ui.BaseActivity
import com.shadow.favoritecataloguemovie.ui.detail.MovieDetailActivity
import com.shadow.favoritecataloguemovie.util.adapter.MovieAdapter
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.fragment_movie.view.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
@Suppress("DEPRECATION")
class MovieFavorite : Fragment(), FavoriteContract, MovieAdapter.OnResultClickListener {

    @Inject
    internal lateinit var presenter: FavoritePresenter

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Injector.obtain(context!!).inject(this)

        presenter.setView(this)

        initComponent(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        clearList()
        initComponent(savedInstanceState = null)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(MOVIE_RESULT, savedMovie)
        super.onSaveInstanceState(outState)
    }

    override fun initComponent(savedInstanceState: Bundle?) {
        with(root) {
            movieAdapter = MovieAdapter()
            linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            RvMovie.layoutManager = linearLayoutManager
            RvMovie.setItemViewCacheSize(20)
            RvMovie.isDrawingCacheEnabled = true
        }
        if (savedInstanceState != null){
            val result = savedInstanceState.getParcelableArrayList<ItemMovie>(MOVIE_RESULT) as ArrayList<ItemMovie>
            setListCatalogueMovie(result)
        }else {
            getListCatalogue()
        }
    }

    override fun getListCatalogue() {
        presenter.getFavoriteMovieList(root.context)
    }

    override fun setListCatalogueMovie(movie: ArrayList<ItemMovie>) {
        savedMovie = movie

        if (movie.isNotEmpty()) {
            movieAdapter.setItem(movie)
            movieAdapter.setClickListener(this)
            emptyMessage.visibility = View.GONE
            root.RvMovie.adapter = movieAdapter
        } else {
            emptyMessage.visibility = View.VISIBLE
        }
    }

    override fun setListCatalogueTv(tv: ArrayList<ItemTv>) {
        //Do Nothing
    }

    override fun clearList() {
        movieAdapter.clearItem()
    }

    override fun onClick(item: ItemMovie) {
        val intent = Intent(context, MovieDetailActivity::class.java)
        intent.putExtra("MovieDetail", item)
        startActivity(intent)
    }

    override fun onShowToast(message: String) {
        (activity as BaseActivity).showToast(message)
    }
    companion object{
        val MOVIE_RESULT = "movie_result"
        lateinit var savedMovie: ArrayList<ItemMovie>
    }
}
