package com.mishamoovex.permissions

import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultRegistry
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class TestFragment (private val registry: ActivityResultRegistry) : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}