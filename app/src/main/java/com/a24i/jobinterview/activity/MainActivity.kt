package com.a24i.jobinterview.activity

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View

import com.a24i.jobinterview.R
import com.a24i.jobinterview.databinding.ActivityMainBinding
import com.a24i.jobinterview.entity.ChangedMovie
import com.a24i.jobinterview.entity.Movie
import com.a24i.jobinterview.fragment.MainFragment
import com.a24i.jobinterview.repository.TheMovieDbRepository
import com.a24i.jobinterview.viewmodel.MainActivityViewModel

class MainActivity : BaseActivity(), MainFragment.OnFragmentInteractionListener {

    private lateinit var mViewModel: MainActivityViewModel
    private lateinit var mBinding: ActivityMainBinding  // So far only for future extensibility.

    companion object {
        const val TAG_MAIN_FRAGMENT = "main_fragment_tag"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.viewModel = mViewModel

        setSupportActionBar(mBinding.toolbar)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, MainFragment.newInstance(), TAG_MAIN_FRAGMENT).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        return if (id == R.id.action_about) {
            Log.d("clicked", "clicked")
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onMovieSelected(movie: Movie) {
        startActivity(DetailActivity.newIntent(this, movie))
    }

    fun onTextViewClick(view: View) {
        Log.d("clicked", "clickedwoow")
    }

}
