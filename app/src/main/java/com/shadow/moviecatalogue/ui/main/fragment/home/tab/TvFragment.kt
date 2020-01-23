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
import com.shadow.moviecatalogue.data.model.response.ItemTv
import com.shadow.moviecatalogue.data.model.response.TvResponse
import com.shadow.moviecatalogue.presenter.catalogue.CataloguePresenter
import com.shadow.moviecatalogue.ui.BaseActivity
import com.shadow.moviecatalogue.ui.detail.TvDetailActivity
import com.shadow.moviecatalogue.ui.main.MainContract
import com.shadow.moviecatalogue.util.Utilities
import com.shadow.moviecatalogue.widget.adapter.TvAdapter
import kotlinx.android.synthetic.main.fragment_tv.*
import kotlinx.android.synthetic.main.fragment_tv.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * A simple [Fragment] subclass.
 */
@Suppress("DEPRECATION")
class TvFragment : Fragment(), MainContract, TvAdapter.OnResultClickListener {

    @Inject
    internal lateinit var presenter: CataloguePresenter

    private var page by Delegates.notNull<Int>()
    private lateinit var root: View
    private lateinit var tvAdapter: TvAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_tv, container, false)
        return root
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

            tvAdapter = TvAdapter()
            linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            RvTv.layoutManager = linearLayoutManager
            RvTv.setItemViewCacheSize(20)
            RvTv.isDrawingCacheEnabled = true

            buttonRetry.setOnClickListener {
                getListCatalogue()
            }
        }
        if (savedInstanceState != null) {
            val result = savedInstanceState.getParcelableArrayList<TvResponse>(TV_RESULT) as ArrayList<TvResponse>
            setListCatalogueTv(result[0])
        } else {
            getListCatalogue()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(TV_RESULT, savedTv)
        super.onSaveInstanceState(outState)
    }

    override fun getListCatalogue() {
        if (Utilities.isNetAvailable(context!!)) {
            setMessageView(View.GONE, View.VISIBLE, View.GONE)
            presenter.getCatalogueTvList(page)
        } else {
            setMessageView(View.GONE, View.GONE, View.VISIBLE)
        }
    }

    override fun setListCatalogueMovie(response: MovieResponse) {
        //Do Nothing
    }

    override fun setListCatalogueTv(response: TvResponse) {

        val listTv = response.results
        savedTv.add(response)

        if (listTv.isNotEmpty() && page == 1) {

            tvAdapter.setItem(listTv)
            tvAdapter.setClickListener(this)

            root.RvTv.adapter = tvAdapter

            root.RvTv.addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
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


    override fun setMessageView(EmptyMessage: Int, ProgressBar: Int, ErrorNetwork: Int){
        root.emptyMessage.visibility = EmptyMessage
        root.progressBar.visibility = ProgressBar
        root.errorNetwork.visibility = ErrorNetwork
    }

    override fun onClick(item: ItemTv) {
        val intent = Intent(context, TvDetailActivity::class.java)
        intent.putExtra("TvDetail", item)
        startActivity(intent)
    }

    override fun onShowToast(message: String) {
        (activity as BaseActivity).showToast(message)
    }

    companion object {
        val TV_RESULT = "tv_result"
        val savedTv = ArrayList<TvResponse>()
    }
}