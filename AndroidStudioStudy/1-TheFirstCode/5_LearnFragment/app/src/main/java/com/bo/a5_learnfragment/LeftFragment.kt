package com.bo.a5_learnfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/*这里要从androidx.fragment.app.Fragment继承,因为它可以让Fragment的特性在所有Android版本中保持一致*/
class LeftFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.left_fragment,container,false)
    }
}