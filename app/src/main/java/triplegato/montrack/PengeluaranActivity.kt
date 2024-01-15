package triplegato.montrack

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import triplegato.montrack.databinding.ActivityPemasukkanBinding
import triplegato.montrack.databinding.ActivityPengeluaranBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class PengeluaranActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPengeluaranBinding
    private lateinit var db : AktivitasDatabaseHelper
    private var aktivitasId: Int = -1
    private var selectedDate: Long = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = AktivitasDatabaseHelper(this)

        binding = ActivityPengeluaranBinding.inflate(layoutInflater)
        setContentView(binding.root)

        aktivitasId = intent.getIntExtra(AKTIVITAS_ID,-1)
        if(aktivitasId == -1){
            finish()
            return
        }

        binding.date.setOnClickListener {
            showDatePickerDialog()
        }

        val aktivitas = db.getAktivitasById(aktivitasId)
        binding.deskripsiValue.setText(aktivitas.deskripsi)
        binding.listKategori.setText(aktivitas.kategori)
        binding.jumlahValue.setText(aktivitas.jumlah.toString())
        var date = Date(aktivitas.tanggal)
        var dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val formattedDate = dateFormat.format(date)
        binding.date.setText(formattedDate)

        binding.simpan.setOnClickListener {
            val jumlahString = binding.jumlahValue.text.toString()
            val jumlahNumber = if(jumlahString.isNotEmpty()){
                jumlahString.toInt()
            }else {
                0
            }
            val kategori = binding.listKategori.text.toString()
            val deskripsi = binding.deskripsiValue.text.toString()
            val tanggal = dateStringToLong(binding.date.text.toString())
            val aktivitas = Aktivitas(aktivitasId,kategori,tanggal,jumlahNumber,"pengeluaran","",deskripsi)
            db.updateAktivitas(aktivitas)
            finish()
            Toast.makeText(this,"aktivitas berhasil diubah", Toast.LENGTH_SHORT).show()
        }

        binding.kembali.setOnClickListener{
            finish()
        }

        binding.hapus.setOnClickListener {
            db.deleteAktivitas(aktivitasId)
            Toast.makeText(this, "aktivitas berhasil dihapus", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onResume(){
        super.onResume()

        val kategori = listOf(
            Kategori("makanan",R.drawable.fastfood),
            Kategori("internet",R.drawable.language),
            Kategori("kendaraan",R.drawable.directions_car),
            Kategori("obat",R.drawable.pill),
            Kategori("gawai",R.drawable.phone_iphone),
            Kategori("belanja",R.drawable.shopping_bag),
            Kategori("travelling",R.drawable.flight_takeoff),
            Kategori("gim",R.drawable.sports_esports),
            Kategori("olahraga",R.drawable.sports_volleyball),
            Kategori("kosmetik",R.drawable.health_and_beauty)
        )
        val arrayAdapter = KategoriAdapter(this, R.layout.item_dropdown, kategori)
        binding.listKategori.setAdapter(arrayAdapter)
    }

    private fun showDatePickerDialog(){
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                selectedDate = calendar.timeInMillis
                binding.date.setText(SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.time))
            }, year, month, dayOfMonth)

        datePickerDialog.show()
    }

    fun dateStringToLong(dateString: String): Long {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        try {
            val date: Date = dateFormat.parse(dateString) ?: Date()

            return date.time
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return 0L
    }

    companion object {
        const val AKTIVITAS_ID = "aktivitas_id"
    }
}