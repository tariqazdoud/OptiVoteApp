package com.example.optivote


import android.icu.text.CaseMap.Title
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.optivote.ViewModel.VoteRecordViewModel
import com.example.optivote.adapters.RecentVotesAdapter
import com.example.optivote.databinding.FragmentHomePageBinding
import com.example.optivote.model.UserInInfo
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomePageFragment : Fragment() {
    private var _binding: FragmentHomePageBinding? = null

    private val voteRecordViewModel: VoteRecordViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomePageBinding.inflate(inflater, container, false)
        val view = binding.root
        var recentVotesRecyclerView: RecyclerView = binding.recentVotesRecyclerView
        var adapter: RecentVotesAdapter = RecentVotesAdapter(findNavController())
        recentVotesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        recentVotesRecyclerView.adapter = adapter


        val joinBtn = binding.button
        joinBtn.setOnClickListener {
            proceedToVote()
        }

        voteRecordViewModel.recentVotesLiveData.observe(viewLifecycleOwner) { recentVotesData ->
            adapter.submitList(recentVotesData)
            Log.d("recentVotes", "$recentVotesData")
        }
        voteRecordViewModel.getRecentVote()

        return view
    }

    fun proceedToVote() {
        val codeEnteredByUser = binding.editText.text.toString().toIntOrNull()
        voteRecordViewModel.clearVoteResult()
        voteRecordViewModel.voteResultLiveData.removeObservers(viewLifecycleOwner)
        if (codeEnteredByUser != null) {
            voteRecordViewModel.voteResultLiveData.observe(viewLifecycleOwner) { voteResult ->
                when (voteResult) {
                    is VoteRecordViewModel.VoteResult.Success -> {
                        val fragmentId = R.id.voteFragment
                        view?.findNavController()?.navigate(fragmentId, Bundle().apply {
                            putSerializable("voteData", voteResult.voteDto)
                        })
                    }

                    VoteRecordViewModel.VoteResult.AlreadyVoted -> {
                        showFailureDialog2()
                        Log.d("Vote Check", "Already Voted $codeEnteredByUser")
                    }

                    VoteRecordViewModel.VoteResult.CodeMismatch -> {
                        Log.d("Vote Status ", "Code miss match  $codeEnteredByUser")
                        showFailureDialog1()

                    }

                    null -> {
                        Log.d("error","error")
                    }
                }
            }
            voteRecordViewModel.proceedToVote(codeEnteredByUser, UserInInfo.id)
        } else {
            displayInvalidCodeDialog()
        }
    }


    private fun displayCodeMismatchDialog() {
        Log.d("error", "code mismatch")
    }

    private fun displayInvalidCodeDialog() {
        Log.d("error", "invalid code dialogue")
    }

    private fun showFailureDialog1() {
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.home_page_vote_dialog, null)

        val rootLayout = dialogView.findViewById<LinearLayout>(R.id.successConstraintLayout)
        rootLayout.gravity = Gravity.CENTER

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(dialogView)


        val alertDialog = builder.create()

        val widthInDp = 500
        val heightInDp = 220

        val widthInPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            widthInDp.toFloat(),
            resources.displayMetrics
        ).toInt()
        val heightInPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            heightInDp.toFloat(),
            resources.displayMetrics
        ).toInt()

        alertDialog.window?.setLayout(widthInPx, heightInPx)

        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)


        val closeBtn = dialogView.findViewById<Button>(R.id.dialogCloseBtn)


        closeBtn.setOnClickListener {
            alertDialog.dismiss()
        }



        alertDialog.show()


    }
    private fun showFailureDialog2() {
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.home_page_vote_dialog_2, null)

        val rootLayout = dialogView.findViewById<LinearLayout>(R.id.successConstraintLayout)
        rootLayout.gravity = Gravity.CENTER

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(dialogView)


        val alertDialog = builder.create()

        val widthInDp = 500
        val heightInDp = 220

        val widthInPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            widthInDp.toFloat(),
            resources.displayMetrics
        ).toInt()
        val heightInPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            heightInDp.toFloat(),
            resources.displayMetrics
        ).toInt()

        alertDialog.window?.setLayout(widthInPx, heightInPx)

        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)


        val closeBtn = dialogView.findViewById<Button>(R.id.dialogCloseBtn)


        closeBtn.setOnClickListener {
            alertDialog.dismiss()
        }



        alertDialog.show()


    }


}
