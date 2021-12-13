package ru.netology.nmedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.databinding.ActivityAppBinding
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import ru.netology.nmedia.auth.AppAuth
import ru.netology.nmedia.viewmodel.AuthViewModel

class AppActivity : AppCompatActivity(R.layout.activity_app) {
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//        val binding = ActivityAppBinding.inflate(layoutInflater)
//        setContentView(binding.root)

//        intent?.let {
//            if (it.action != Intent.ACTION_SEND) return@let
//
//            val text = it.getStringExtra(Intent.EXTRA_TEXT)
//            if (text.isNullOrBlank()) Snackbar.make(
//                binding.root, R.string.error_empty_content,
//                LENGTH_INDEFINITE
//            ).setAction(android.R.string.ok) {
//                finish()
//            }.show()
//        }
                intent?.let {
                    val text = it.getStringExtra(Intent.EXTRA_TEXT)
                    if (text?.isNotBlank() != true) {
                        return@let
                    }
                }

            viewModel.data.observe(this) {
                invalidateOptionsMenu()
            }
        }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        menu.let {
            it.setGroupVisible(R.id.unauthenticated, !viewModel.authenticated)
            it.setGroupVisible(R.id.authenticated, viewModel.authenticated)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) : Boolean {
        return when (item.itemId) {
            R.id.sign_in -> {
               findNavController(R.id.app_activity_fragment_container).navigate(R.id.action_feedFragment_to_authFragment)
                true
            }
            R.id.sign_up -> {
                // TODO: just hardcode it, implementation must be in homework
                AppAuth.getInstance().setAuth(5, "x-token")
                true
            }
            R.id.sign_out -> {
                // TODO: just hardcode it, implementation must be in homework
                AppAuth.getInstance().removeAuth()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}