package com.a24i.jobinterview.activity

import android.app.Fragment
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View

import com.a24i.jobinterview.R
import com.a24i.jobinterview.databinding.ActivityDetailBinding
import com.a24i.jobinterview.entity.Movie
import com.a24i.jobinterview.fragment.DetailFragment
import com.a24i.jobinterview.viewmodel.DetailActivityViewModel

class DetailActivity : BaseActivity() {

    private lateinit var mViewModel: DetailActivityViewModel
    private lateinit var mBinding: ActivityDetailBinding  // So far only for future extensibility.

    companion object {
        private const val KEY_MOVIE = "movie_key"
        const val TAG_DETAIL_FRAGMENT = "detail_fragment_tag"

        fun newIntent(context: Context, movie: Movie): Intent {
            return  Intent(context, DetailActivity::class.java).apply {
                putExtra(DetailActivity.KEY_MOVIE, movie)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(DetailActivityViewModel::class.java)
        mViewModel.movie = intent.getParcelableExtra(KEY_MOVIE)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        mBinding.viewModel = mViewModel
        setSupportActionBar(mBinding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, DetailFragment.newInstance(mViewModel.movie), TAG_DETAIL_FRAGMENT).commit()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var isHandled = super.onOptionsItemSelected(item)
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                isHandled = true
            }
        }
        return isHandled
    }
}