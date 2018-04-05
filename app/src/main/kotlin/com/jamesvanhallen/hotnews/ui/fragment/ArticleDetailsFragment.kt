package com.jamesvanhallen.hotnews.ui.fragment

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpFragment
import com.jamesvanhallen.hotnews.R
import com.jamesvanhallen.hotnews.URL
import com.jamesvanhallen.hotnews.inflate
import com.jamesvanhallen.hotnews.ui.listener.OnScreenChangeListener
import kotlinx.android.synthetic.main.fragment_article_details.*

class ArticleDetailsFragment : MvpFragment() {

    private var onScreenChangeListener: OnScreenChangeListener? = null

    companion object {

        fun newInstance(url: String): Fragment {
            val articleFragment = ArticleDetailsFragment()
            val bundle = Bundle()
            bundle.putString(URL, url)
            articleFragment.arguments = bundle
            return articleFragment
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        onScreenChangeListener = activity as? OnScreenChangeListener
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return container!!.inflate(R.layout.fragment_article_details)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        webView.loadUrl(arguments.getString(URL))
    }
}