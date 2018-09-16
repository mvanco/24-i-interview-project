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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.viewModel = mViewModel

        setSupportActionBar(mBinding.toolbar)



        TheMovieDbRepository.getChangedMovies("2018-09-10", "2018-09-16", 1).subscribe {
            for (item: ChangedMovie in it) {
                Log.d("super", item.id.toString())
            }
        }

//        for (i in 4992L..6000L) {
            TheMovieDbRepository.getMovie(4992L).subscribe {
                Log.d("super2", it.poster_path)
//            }
        }
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
