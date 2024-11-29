package com.example.optivote

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import com.example.optivote.ViewModel.SignInViewModel
import com.example.optivote.ViewModel.UserViewModel
import com.example.optivote.databinding.ActivityChangePassActivtyBinding
import com.example.optivote.model.UserInInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePassActivty : AppCompatActivity() {
    private lateinit var binding: ActivityChangePassActivtyBinding
    private val signInViewModel : SignInViewModel by viewModels()
    private val userViewModel : UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityChangePassActivtyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val newPasswordTf = binding.newPassword
        val newPasswordConfirmedTf = binding.newPasswordConfirmed
        val updateBtn = binding.buttonChangePassword
        updateBtn.setOnClickListener {
            val newPassword = newPasswordTf.text.toString()
            val newPasswordConfirmed = newPasswordConfirmedTf.text.toString()

            if (newPassword == newPasswordConfirmed) {
                signInViewModel.updatePassword(newPassword)
                showSuccessDialog()
                userViewModel.updateAlreadySignedInState(UserInInfo.id,UserInInfo.email)
                Log.d("update success", "matched passwords")
            } else {
                Log.d("unmatched", "unmatched passwords")
            }
        }


    }
    private fun redirectToNextActivity() {
        val intent = Intent(this@ChangePassActivty,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    private  fun showSuccessDialog(){
        val inflater = LayoutInflater.from(this)
        val dialogView = inflater.inflate(R.layout.success_dialog_change_pass, null)


        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)


        val alertDialog = builder.create()

        val widthInDp = 500
        val heightInDp = 220

        val widthInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, widthInDp.toFloat(), resources.displayMetrics).toInt()
        val heightInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, heightInDp.toFloat(), resources.displayMetrics).toInt()

        alertDialog.window?.setLayout(widthInPx, heightInPx)


        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialog.window?.decorView?.postDelayed({
            alertDialog.dismiss()

                redirectToNextActivity()




        }, 2000)
        alertDialog.show()


    }
}