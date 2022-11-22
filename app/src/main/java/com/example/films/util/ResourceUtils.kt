package com.example.films.util

import android.content.res.Resources
import androidx.annotation.IntegerRes

internal infix fun Resources.getLong(@IntegerRes integerRes : Int) : Long =
    getInteger(integerRes).toLong()