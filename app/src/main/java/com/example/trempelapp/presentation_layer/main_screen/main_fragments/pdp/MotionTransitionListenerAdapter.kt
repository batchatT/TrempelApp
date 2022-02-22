package com.example.trempelapp.presentation_layer.main_screen.main_fragments.pdp

import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.trempelapp.R

class MotionTransitionListenerAdapter(
    private val onSwipe: (Boolean) -> Unit,
) : MotionLayout.TransitionListener {

    override fun onTransitionStarted(
        motionLayout: MotionLayout?,
        startId: Int,
        endId: Int,
    ) {}

    override fun onTransitionChange(
        motionLayout: MotionLayout?,
        startId: Int,
        endId: Int,
        progress: Float,
    ) {}

    override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
        onSwipe(currentId == R.id.start)
    }

    override fun onTransitionTrigger(
        motionLayout: MotionLayout?,
        triggerId: Int,
        positive: Boolean,
        progress: Float,
    ) {}
}
