package tsisyk.app.forecast.data.provider

import tsisyk.app.forecast.internal.UnitSystem


interface UnitProvider {
    fun getUnitSystem(): UnitSystem
}