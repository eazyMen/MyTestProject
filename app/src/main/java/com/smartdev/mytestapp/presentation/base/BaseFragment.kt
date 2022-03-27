package com.smartdev.mytestapp.presentation.base

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<B : ViewBinding>: Fragment() {

    var binding: B? = null



}