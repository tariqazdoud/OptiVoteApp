package com.example.optivote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.optivote.ViewModel.SignInViewModel
import com.example.optivote.ViewModel.UserViewModel
import com.example.optivote.model.UserInInfo
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var loginBtn: Button
    private lateinit var EmailEt : EditText
    private lateinit var PasswordEt:EditText
    private val viewModel:SignInViewModel by viewModels()
    private val userViewModel : UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginBtn = findViewById(R.id.btnLogin)

        EmailEt = findViewById(R.id.loginEt)
        PasswordEt = findViewById(R.id.passwordEt)


        loginBtn.setOnClickListener{

                val myEmail = EmailEt.text.toString()
                val myPassword = PasswordEt.text.toString()
                viewModel.onSignIn(myEmail,myPassword)
        }
        observeSignInState()



    }
    private fun observeSignInState(){
        lifecycleScope.launchWhenStarted {
            viewModel.signInState.collect{state->
                when(state){
                    is SignInViewModel.SignInState.Success->{
                        if (state.success){
                            Log.d("LoginActivity", "Sign-In success")

                            viewModel.test.observe(this@LoginActivity, Observer { email->
                                userViewModel.getUserInfo(email)
                                Log.d("userInfo","current user email $email")
                                userViewModel.userInfoLiveDate.observe(this@LoginActivity){userIn->
                                    UserInInfo.id = userIn.id!!
                                    UserInInfo.email = userIn.email.toString()
                                    UserInInfo.phone = userIn.phone.toString()
                                    UserInInfo.name = userIn.name.toString()
                                    UserInInfo.password = userIn.password.toString()
                                    UserInInfo.signInId = userIn.signInId.toString()
                                    UserInInfo.image = userIn.image.toString()
                                    Log.d("userInName","${UserInInfo.name}")
                                    showSuccessDialog(userIn.alreadySignedIn)

                                }
                            })


                            viewModel.getCurrentUserEmail()



                        }else{
                            showFailureDialog()
                        }
                    }
                    is SignInViewModel.SignInState.Error->{
                        Log.d("LoginActivity", "Sign-in error: ${state.message}")
                    }
                    SignInViewModel.SignInState.Loading->{

                    }
                    SignInViewModel.SignInState.Initial->{

                    }
                }
            }
        }
    }
    private  fun showSuccessDialog(alreadySignedIn : Boolean?){
        val inflater = LayoutInflater.from(this)
        val dialogView = inflater.inflate(R.layout.success_dialog, null)


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
            if(alreadySignedIn == true){
                redirectToNextActivity()
            }else{
                redirectToNextActivityChangePass()
            }

        }, 2000)
        alertDialog.show()


    }
    private  fun showFailureDialog(){
        val inflater = LayoutInflater.from(this)
        val dialogView = inflater.inflate(R.layout.failed_dialog, null)


        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)


        val alertDialog = builder.create()

        val widthInDp = 500
        val heightInDp = 220

        val widthInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, widthInDp.toFloat(), resources.displayMetrics).toInt()
        val heightInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, heightInDp.toFloat(), resources.displayMetrics).toInt()

        alertDialog.window?.setLayout(widthInPx, heightInPx)

        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val closeBtn = dialogView.findViewById<Button>(R.id.dialogCloseBtn)
        closeBtn.setOnClickListener {
            alertDialog.dismiss()
        }



        alertDialog.show()




    }
    private fun redirectToNextActivity() {
        val intent = Intent(this@LoginActivity,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun redirectToNextActivityChangePass() {
        val intent = Intent(this@LoginActivity,ChangePassActivty::class.java)
        startActivity(intent)
        finish()
    }

}
