package com.example.tutorial4_6subcomponentbuilders_multiplemodulesandactvities.model

import android.content.Context
import javax.inject.Inject

/**
 * Constructor injected component should have the same scope with the component that is injects to same object
 * or no scope at all
 */
class SensorController @Inject constructor(context: Context)