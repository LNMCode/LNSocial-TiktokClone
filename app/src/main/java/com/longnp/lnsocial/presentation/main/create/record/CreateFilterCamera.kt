package com.longnp.lnsocial.presentation.main.create.record

import androidx.annotation.DrawableRes
import com.longnp.lnsocial.R
import com.otaliastudios.cameraview.filter.Filters

enum class CreateFilterCamera(val filter: Filters, @DrawableRes val res: Int, val title: String) {
    NoneFilter(Filters.NONE, R.drawable.coding_image, "Origin"),
    AutoFixFilter(Filters.AUTO_FIX, R.drawable.coding_image, "Auto"),
    BlackAndWhiteFilter(Filters.BLACK_AND_WHITE, R.drawable.coding_image, "Black "),
    BrightnessFilter(Filters.BRIGHTNESS, R.drawable.coding_image, "Brightness"),
    ContrastFilter(Filters.CONTRAST, R.drawable.coding_image, "Contrast"),
    CrossProcessFilter(Filters.CROSS_PROCESS, R.drawable.coding_image, "Cross Process"),
    DocumentaryFilter(Filters.DOCUMENTARY, R.drawable.coding_image, "Documentary"),
    DuotoneFilter(Filters.DUOTONE, R.drawable.coding_image, "Duotone"),
    FillLightFilter(Filters.FILL_LIGHT, R.drawable.coding_image, "Fill Light"),
    GammaFilter(Filters.GAMMA, R.drawable.coding_image, "Gamma"),
    GrainFilter(Filters.GRAIN, R.drawable.coding_image, "Grain"),
    GrayscaleFilter(Filters.GRAYSCALE, R.drawable.coding_image, "Grayscale"),
    HueFilter(Filters.HUE, R.drawable.coding_image, "Hue"),
    InvertColorsFilter(Filters.INVERT_COLORS, R.drawable.coding_image, "Invert Colors"),
    LomoishFilter(Filters.LOMOISH, R.drawable.coding_image, "Lomoish"),
    PosterizeFilter(Filters.POSTERIZE, R.drawable.coding_image, "Posterize"),
    SaturationFilter(Filters.SATURATION, R.drawable.coding_image, "Saturation"),
    SepiaFilter(Filters.SEPIA, R.drawable.coding_image, "Sepia"),
    SharpnessFilter(Filters.SHARPNESS, R.drawable.coding_image, "Sharpness"),
    TemperatureFilter(Filters.TEMPERATURE, R.drawable.coding_image, "Cross Process"),
    TintFilter(Filters.TINT, R.drawable.coding_image, "Temperature"),
    VignetteFilter(Filters.VIGNETTE, R.drawable.coding_image, "Vignette"),
}