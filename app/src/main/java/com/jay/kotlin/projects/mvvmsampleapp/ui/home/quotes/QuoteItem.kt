package com.jay.kotlin.projects.mvvmsampleapp.ui.home.quotes

import com.jay.kotlin.projects.mvvmsampleapp.R
import com.jay.kotlin.projects.mvvmsampleapp.data.db.entities.Quote
import com.jay.kotlin.projects.mvvmsampleapp.databinding.ItemQuoteBinding
import com.xwray.groupie.databinding.BindableItem

class QuoteItem(
    private val quote: Quote
) : BindableItem<ItemQuoteBinding>() {
    override fun bind(viewBinding: ItemQuoteBinding, position: Int) {
        viewBinding.setQuote(quote)
    }

    override fun getLayout() = R.layout.item_quote
}