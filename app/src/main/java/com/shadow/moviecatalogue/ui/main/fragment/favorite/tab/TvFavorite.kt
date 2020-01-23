package com.shadow.moviecatalogue.ui.main.fragment.favorite.tab


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.shadow.moviecatalogue.R
import com.shadow.moviecatalogue.config.Injector
import com.shadow.moviecatalogue.data.model.response.ItemMovie
import com.shadow.moviecatalogue.data.model.response.ItemTv
import com.shadow.moviecatalogue.presenter.favorite.FavoritePresenter
import com.shadow.moviecatalogue.ui.BaseActivity
import com.shadow.moviecatalogue.ui.detail.TvDetailActivity
import com.shadow.moviecatalogue.ui.main.fragment.favorite.FavoriteContract
import com.shadow.moviecatalogue.widget.adapter.TvAdapter
import kotlinx.android.synthetic.main.fragment_tv.*
import kotlinx.android.synthetic.main.fragment_tv.view.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
@Suppress("DEPRECATION")
class TvFavorite : Fragment(), FavoriteContract, TvAdapter.OnResultClickListener {

    @Inject
    internal lateinit var presenter: FavoritePresenter

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

    override fun onResume() {
        super.onResume()
        clearList()
        initComponent(savedInstanceState = null)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(TV_RESULT, savedTv)
        super.onSaveInstanceState(outState)
    }

    override fun initComponent(savedInstanceState: Bundle?) {
        with(root) {
            tvAdapter = TvAdapter()
            linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            RvTv.layoutManager = linearLayoutManager
            RvTv.setItemViewCacheSize(20)
            RvTv.isDrawingCacheEnabled = true
        }

        if (savedInstanceState != null){
            val result = savedInstanceState.getParcelableArrayList<ItemTv>(TV_RESULT) as ArrayList<ItemTv>
            setListCatalogueTv(result)
        } else {
            getListCatalogue()
        }
    }

    override fun getListCatalogue() {
        presenter.getFavoriteTvList(root.context)
    }

    override fun setListCatalogueMovie(movie: ArrayList<ItemMovie>) {
        //Do Nothing
    }

    override fun setListCatalogueTv(tv: ArrayList<ItemTv>) {
        savedTv = tv
        if (tv.isNotEmpty()) {
            tvAdapter.setItem(tv)
            tvAdapter.setClickListener(this)
            setMessageView(View.GONE, View.GONE, View.GONE)

            root.RvTv.adapter = tvAdapter
        } else {
            setMessageView(View.VISIBLE, View.GONE, View.GONE)
        }
    }

    override fun clearList() {
        tvAdapter.clearItem()
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
        lateinit var savedTv: ArrayList<ItemTv>
    }
}