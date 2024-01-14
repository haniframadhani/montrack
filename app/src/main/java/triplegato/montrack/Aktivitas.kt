package triplegato.montrack

import java.util.Date

data class Aktivitas(val id: Int, val kategori: String, val tanggal: Long, val jumlah: Int, val jenis: String, val lokasi: String, val deskripsi: String)