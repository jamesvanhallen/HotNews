package com.jamesvanhallen.hotnews.data.model.annotation

import android.support.annotation.StringDef
import com.jamesvanhallen.hotnews.LATEST
import com.jamesvanhallen.hotnews.POPULAR
import com.jamesvanhallen.hotnews.TOP

@Retention(AnnotationRetention.SOURCE)
@StringDef(TOP, LATEST, POPULAR)
annotation class Sort
