package com.treplabs.ddm

import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.snackbar.Snackbar
import com.treplabs.ddm.base.BaseFragment
import com.treplabs.ddm.base.LoadingCallback
import com.treplabs.ddm.extensions.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.loading_indicator.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class MainActivity : AppCompatActivity(), LoadingCallback {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var currentFragment: BaseFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpNavigation()
    }

    private fun setUpNavigation() {
        toolbar.overflowIcon = getDrawable(R.drawable.ic_more_vert_24dp)
        setSupportActionBar(toolbar)
        navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.loginFragment -> bottomNavigationView.hide()
            }
        }
    }

    fun setUpToolBar(toolbarTitle: String, isRootPage: Boolean = false) {
        supportActionBar!!.run {
            setDisplayHomeAsUpEnabled(!isRootPage)
            setHomeAsUpIndicator(if (!isRootPage) R.drawable.ic_arrow_back_24dp else 0)
            toolbarTitleTextView.text = toolbarTitle
            val leftRightPaddingRes =
                if (isRootPage) R.dimen.toolbar_left_right_padding_root else R.dimen.toolbar_left_right_padding
            toolbarTitleTextView.setViewPadding(
                R.dimen.toolbar_top_bottom_padding,
                leftRightPaddingRes
            )
        }
    }

    fun setToolbarIcon(@DrawableRes resId: Int) {
        supportActionBar!!.setHomeAsUpIndicator(resId)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    override fun showLoading() {
        showLoading(R.string.default_loading_message)
    }

    override fun showLoading(resId: Int) {
        showLoading(getString(resId))
    }

    override fun isLoading(): Boolean {
        return loading_layout_container.isVisible
    }

    override fun showLoading(message: String) {
        progressMessage.text = message
        if (isLoading()) return
        hideKeyBoard()
        loading_layout_container.showViewWithChildren()
        disableTouch()
    }

    override fun dismissLoading() {
        loading_layout_container.hide()
        enableTouch()
    }

    override fun showError(resId: Int) {
        showError(getString(resId))
    }

    fun invalidateToolbarElevation(scrollY: Int) {
        if (scrollY > (toolbar.measuredHeight / 2)) {
            appBarLayout.elevation = resources.getDimension(R.dimen.raised_toolbar_elevation)
        } else {
            appBarLayout.elevation = 0f
        }
    }

    override fun showError(message: String) {
        hideKeyBoard()
        dismissLoading()
        MaterialDialog(this).show {
            message(text = message)
            positiveButton(R.string.ok)
            cancelOnTouchOutside(false)
        }
    }

    fun showDialogWithAction(
        title: String?,
        body: String?,
        positiveRes: Int,
        positiveAction: (() -> Unit)?,
        negativeRes: Int?,
        negativeAction: (() -> Unit)?
    ) {
        MaterialDialog(this).show {
            if (title != null) title(text = title)
            if (body != null) message(text = body)
            if (negativeRes != null) negativeButton(negativeRes) { negativeAction?.invoke() }
            positiveButton(positiveRes) { positiveAction?.invoke() }
            cancelOnTouchOutside(false)
        }
    }

    fun showMessageDialog(message: String) {
        MaterialDialog(this).show {
            message(text = message)
            positiveButton(R.string.ok)
            cancelOnTouchOutside(false)
        }
    }

    fun showSnackBar(message: String) {
        hideKeyBoard()
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
    }

    fun setCurrentFragment(baseFragment: BaseFragment) {
        currentFragment = baseFragment
    }

    override fun onBackPressed() {
        // If the current fragment doesn't consume the back pressed action, then call super onBackPressed
        if (!currentFragment.onBackPressed()) super.onBackPressed()
    }
}