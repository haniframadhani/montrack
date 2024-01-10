package triplegato.montrack

data class Kategori(val nama: String, val drawableId: Int) {
    override fun toString(): String {
        return nama
    }
}