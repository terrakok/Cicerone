package com.github.terrakok.fondazione

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Router

abstract class AppFragment : Fragment {
    constructor() : super()
    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    protected lateinit var runId: String
        private set

    enum class CreateMode { NEW, RESTORED_AFTER_ROTATION, RESTORED_AFTER_DEATH }

    protected lateinit var createMode: CreateMode
        private set

    private var instanceStateSaved: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            runId = savedInstanceState.getString(FIRST_RUN_ID)!!

            val appId = requireContext().applicationContext.hashCode().toString()
            val savedAppId = savedInstanceState.getString(APP_RUN_ID)

            createMode = if (appId != savedAppId) {
                CreateMode.RESTORED_AFTER_DEATH
            } else {
                CreateMode.RESTORED_AFTER_ROTATION
            }
        } else {
            runId = "${javaClass.simpleName}[${hashCode()}]"
            createMode = CreateMode.NEW
        }
    }

    override fun onStart() {
        super.onStart()
        instanceStateSaved = false
    }

    override fun onResume() {
        super.onResume()
        instanceStateSaved = false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(APP_RUN_ID, requireContext().applicationContext.hashCode().toString())
        outState.putString(FIRST_RUN_ID, runId)
        instanceStateSaved = true
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isRealDestroy()) onFinalDestroy()
    }

    protected open fun onFinalDestroy() {}

    // This is android, baby!
    private fun isRealRemoving(): Boolean =
        (isRemoving && !instanceStateSaved) || // Because isRemoving == true for fragment in backstack on screen rotation
            ((parentFragment as? AppFragment)?.isRealRemoving() ?: false)

    // It will be valid only for 'onDestroy()' method
    private fun isRealDestroy(): Boolean =
        when {
            activity?.isChangingConfigurations == true -> false
            activity?.isFinishing == true -> true
            else -> isRealRemoving()
        }

    open fun onBackPressed(): Boolean = false

    companion object {
        private const val APP_RUN_ID = "state_app_run_id"
        private const val FIRST_RUN_ID = "state_first_run_id"
    }
}

val AppFragment.router: Router get() =
    if (this is NavigationFragment) router else findParentRouter()!!
fun AppFragment.findParentRouter(): Router? {
    val parent = this.parentFragment as? AppFragment
        ?: return (this.activity as? AppActivity)?.router

    return if (parent is NavigationFragment)
        parent.router
    else
        parent.findParentRouter()
}