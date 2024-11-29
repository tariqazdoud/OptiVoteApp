package com.example.optivote


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.example.optivote.ViewModel.SignInViewModel
import com.example.optivote.ViewModel.UserViewModel
import com.example.optivote.databinding.FragmentPersonalAccountBinding
import com.example.optivote.model.UserInInfo
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonalAccountFragment : Fragment() {
    private var _binding: FragmentPersonalAccountBinding? = null
    private val binding get() = _binding!!
    private val signInViewModel : SignInViewModel by viewModels()
    private val userViewModel : UserViewModel by viewModels()
    private var currentUserEmail : String? = null
    private var alertDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPersonalAccountBinding.inflate(inflater, container, false)
        val view = binding.root





            binding.nameTv.text = UserInInfo.name
            binding.emailTv.text = UserInInfo.email
            binding.phoneTv.text = UserInInfo.phone


            val imgUrl = UserInInfo.image?.let { UserInInfo.buildImageUrl(it) }
            Picasso.get().load(imgUrl).into(binding.profileImageView)

            binding.logoutBtn.setOnClickListener {
                showConfirmationDialog()
             }

        return view

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        alertDialog?.dismiss()
    }

    private fun showConfirmationDialog() {
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.logout_dialog, null)

        val builder = androidx.appcompat.app.AlertDialog.Builder(requireActivity())
        builder.setView(dialogView)

        alertDialog = builder.create()

        val widthInDp = 500
        val heightInDp = 220

        val widthInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, widthInDp.toFloat(), resources.displayMetrics).toInt()
        val heightInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, heightInDp.toFloat(), resources.displayMetrics).toInt()

        alertDialog?.window?.setLayout(widthInPx, heightInPx)
        alertDialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val closeBtn = dialogView.findViewById<Button>(R.id.cancelBtn)
        val confirmBtn = dialogView.findViewById<Button>(R.id.confirmBtn)

        confirmBtn.setOnClickListener {
            signInViewModel.logout()
            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            activity?.finish()
            alertDialog?.dismiss()
        }
        closeBtn.setOnClickListener {
            alertDialog?.dismiss()
        }

        alertDialog?.show()
    }


}