package com.jay.kotlin.projects.mvvmsampleapp.ui.home.quotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.jay.kotlin.projects.mvvmsampleapp.R
import com.jay.kotlin.projects.mvvmsampleapp.data.db.entities.Quote
import com.jay.kotlin.projects.mvvmsampleapp.util.Coroutines
import com.jay.kotlin.projects.mvvmsampleapp.util.hide
import com.jay.kotlin.projects.mvvmsampleapp.util.show
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.quotes_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class QuotesFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    private val factory: QuotesViewModelFactory by instance()

    private lateinit var viewModel: QuotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.quotes_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(QuotesViewModel::class.java)

        bindUi()

        /*Coroutines.main {
            val quotes = viewModel.quotes.await()
            quotes.observe(viewLifecycleOwner, {  //this replaced with viewLifecycleOwner
                context?.toast(it.size.toString())
            })
        }*/

    }

    private fun bindUi() = Coroutines.main {
        progress_bar.show()
        viewModel.quotes.await().observe(viewLifecycleOwner, {
            progress_bar.hide()
            initRecyclerView(it.toQuoteItem())
        })
    }

    private fun initRecyclerView(quoteItem: List<QuoteItem>) {
        val mAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(quoteItem)
        }
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mAdapter
        }
    }

    private fun List<Quote>.toQuoteItem(): List<QuoteItem>{
        return this.map {
            QuoteItem(it)
        }
    }
}