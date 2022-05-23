package com.adefruandta.fungame.module.game

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.adefruandta.fungame.R
import com.adefruandta.fungame.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    companion object {

        private const val PLAYED = "PLAYED"

        private const val POSITION = "POSITION"

        fun create(position: Int): Fragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putInt(POSITION, position)
                }
            }
        }
    }

    private lateinit var viewBinding: FragmentGameBinding

    private val position: Int by lazy { arguments?.getInt(POSITION, 0) ?: 0 }

    private val gameViewModel: GameViewModel by lazy {
        ViewModelProvider(
            activity ?: throw RuntimeException("Activity null")
        )[GameViewModel::class.java]
    }

    private var played = false

    private val correctSound by lazy { MediaPlayer.create(context, R.raw.correct) }

    private val wrongSound by lazy { MediaPlayer.create(context, R.raw.wrong) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentGameBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        played = savedInstanceState?.getBoolean(PLAYED, false) ?: false

        if (!played) {
            viewBinding.containerInstruction.visibility = View.VISIBLE
        } else {
            viewBinding.containerInstruction.visibility = View.GONE
        }

        gameViewModel.getGame(position).observe(viewLifecycleOwner) { game ->
            val questionAdapter = QuestionAdapter().apply {
                questions = game.generateQuestions()
            }
            viewBinding.textViewInstruction.text = game.instruction
            viewBinding.buttonPlay.setOnClickListener {
                viewBinding.containerInstruction.visibility = View.GONE
                played = true
            }
            viewBinding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            viewBinding.viewPager.adapter = questionAdapter
            viewBinding.viewPager.isUserInputEnabled = false
            viewBinding.recyclerView.layoutManager =
                GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            viewBinding.recyclerView.adapter = OptionAdapter().apply {
                options = game.options.toList()
                onItemClickListener = { option ->
                    val question = questionAdapter.questions[viewBinding.viewPager.currentItem]
                    if (game.isCorrect(option, question)) {
                        viewBinding.viewPager.currentItem = viewBinding.viewPager.currentItem + 1
                        correctSound.seekTo(0)
                        correctSound.start()
                        correctSound.setOnCompletionListener {
                            if (viewBinding.viewPager.currentItem == questionAdapter.itemCount - 1) {
                                gameViewModel.riseGameDone(game)
                            }
                        }
                    } else {
                        wrongSound.start()
                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(PLAYED, played)
    }

    override fun onDestroy() {
        super.onDestroy()
        correctSound.release()
        wrongSound.release()
    }
}