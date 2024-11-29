package com.example.optivote


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.optivote.ViewModel.UserViewModel
import com.example.optivote.ViewModel.VoteRecordViewModel
import com.example.optivote.databinding.FragmentVoteBinding
import com.example.optivote.model.UserDto
import com.example.optivote.model.UserInInfo
import com.example.optivote.model.VoteDto
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VoteFragment : Fragment() {
    private var _binding: FragmentVoteBinding? = null
    private val binding get() = _binding!!
    private val voteRecordViewModel: VoteRecordViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private var alertDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVoteBinding.inflate(inflater, container, false)
        val view = binding.root

        val titleTv = binding.voteTitle
        val contentTv = binding.voteContent
        val radioGroup = binding.radioGroup
        val submitBtn = binding.submitBtn


        val voteData = arguments?.getSerializable("voteData") as? VoteDto
        if (voteData != null) {
            Log.d("idVote","${voteData.idVote}")
            titleTv.text = voteData.title
            contentTv.text = voteData.content
        }

        val user = UserDto(
            id = UserInInfo.id,
            name = UserInInfo.name,
            email = UserInInfo.email,
            phone = UserInInfo.phone,
            password = UserInInfo.password,
            image = UserInInfo.image,
            signInId = UserInInfo.signInId
        )
        submitBtn.setOnClickListener {
            showConfirmationDialog(view,radioGroup,voteData,user)
        }



        return view
    }
    private fun handleVoteSubmission(view: View, radioGroup: RadioGroup, voteData: VoteDto?, user: UserDto) {
        val selectedRadioButtonId = radioGroup.checkedRadioButtonId
        val selectedRadioButton = view.findViewById<RadioButton>(selectedRadioButtonId)
        val decision = selectedRadioButton?.text?.toString()
        if (decision != null && voteData != null) {
            voteData.idVote?.let { it1 ->
                user.id?.let { it2 ->
                    voteRecordViewModel.submitVote(decision, it2, it1)
                }
            }
        }
        Log.d("tag", "decision $decision")
    }

    private fun showConfirmationDialog(view: View, radioGroup: RadioGroup, voteData: VoteDto?, user: UserDto) {
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.vote_confirmation_dialog, null)

        val builder = AlertDialog.Builder(requireActivity())
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
            findNavController().navigate(R.id.action_voteFragment_to_votesHistoryFragment)
            handleVoteSubmission(view,radioGroup,voteData,user)
            alertDialog?.dismiss()
        }
        closeBtn.setOnClickListener {
            alertDialog?.dismiss()
        }

        alertDialog?.show()
    }

}
