package triplegato.montrack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import triplegato.montrack.databinding.ActivityPemasukkanBinding
import triplegato.montrack.databinding.ActivityPengeluaranBinding

class PengeluaranActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPengeluaranBinding
    private lateinit var db : AktivitasDatabaseHelper 

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = AktivitasDatabaseHelper(this)

        binding = ActivityPengeluaranBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.kembali.setOnClickListener{
            finish()
        }
        binding.hapus.setOnClickListener {
            val id = intent.getIntExtra(AKTIVITAS_ID, -1)
            db.deleteAktivitas(id)
            Toast.makeText(this, "Aktivitas dihapus", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
    companion object {
        const val AKTIVITAS_ID = "aktivitas_id"
    }
}