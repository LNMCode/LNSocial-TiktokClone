package com.longnp.lnsocial.presentation.main.create.record

import androidx.annotation.DrawableRes
import com.longnp.lnsocial.R
import com.otaliastudios.cameraview.filter.Filters

enum class CreateFilterCamera(val filter: Filters, @DrawableRes val res: Int, val title: String) {
    NoneFilter(Filters.NONE, R.drawable.filter_none, "Origin"),
    AutoFixFilter(Filters.AUTO_FIX, R.drawable.filter_none, "Auto"),
    BlackAndWhiteFilter(Filters.BLACK_AND_WHITE, R.drawable.filter_black_and_white, "Black "),
    BrightnessFilter(Filters.BRIGHTNESS, R.drawable.filter_none, "Brightness"),
    ContrastFilter(Filters.CONTRAST, R.drawable.filter_contrast, "Contrast"),
    CrossProcessFilter(Filters.CROSS_PROCESS, R.drawable.filter_none, "Cross Process"),
    DocumentaryFilter(Filters.DOCUMENTARY, R.drawable.filter_none, "Documentary"),
    DuotoneFilter(Filters.DUOTONE, R.drawable.filter_duotone, "Duotone"),
    FillLightFilter(Filters.FILL_LIGHT, R.drawable.filter_none, "Fill Light"),
    GammaFilter(Filters.GAMMA, R.drawable.filter_none, "Gamma"),
    GrainFilter(Filters.GRAIN, R.drawable.filter_none, "Grain"),
    GrayscaleFilter(Filters.GRAYSCALE, R.drawable.filter_none, "Grayscale"),
    HueFilter(Filters.HUE, R.drawable.filter_none, "Hue"),
    InvertColorsFilter(Filters.INVERT_COLORS, R.drawable.filter_none, "Invert Colors"),
    LomoishFilter(Filters.LOMOISH, R.drawable.filter_none, "Lomoish"),
    PosterizeFilter(Filters.POSTERIZE, R.drawable.filter_none, "Posterize"),
    SaturationFilter(Filters.SATURATION, R.drawable.filter_none, "Saturation"),
    SepiaFilter(Filters.SEPIA, R.drawable.filter_none, "Sepia"),
    SharpnessFilter(Filters.SHARPNESS, R.drawable.filter_none, "Sharpness"),
    TemperatureFilter(Filters.TEMPERATURE, R.drawable.filter_none, "Cross Process"),
    TintFilter(Filters.TINT, R.drawable.filter_none, "Temperature"),
    VignetteFilter(Filters.VIGNETTE, R.drawable.filter_none, "Vignette"),
}