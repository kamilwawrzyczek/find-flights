package eu.wawrzyczek.findflights.search.model

data class Station(val code: String = "", val name: String = "", val alternateName:String? = null) {
    val valid : Boolean
        get() = code.isNotEmpty() && name.isNotEmpty()

    override fun toString(): String = name
}
