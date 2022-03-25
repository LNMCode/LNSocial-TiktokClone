package com.longnp.lnsocial.presentation.main.seeds.list

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible

class SeedBtnPauseVideo
@JvmOverloads
constructor(
    @NonNull context: Context,
    @Nullable attributeSet: AttributeSet,
    defStyleAttr: Int = 0,
) : AppCompatImageView(context, attributeSet, defStyleAttr) {

    init {
        resetPauseView()
    }

    fun actionPauseVideo(playWhenReady: Boolean, timeCurrent: Long, isPause: Boolean) {
        this.isVisible = true
        if (!playWhenReady && timeCurrent != 0L && isPause) {
            this.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(300).start()
        } else {
            resetPauseView()
        }
    }

    private fun resetPauseView() {
        isVisible = false
        scaleX = 5f
        scaleY = 5f
        alpha = 0f
    }

}