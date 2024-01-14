package triplegato.montrack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import triplegato.montrack.databinding.ActivityMainBinding
import triplegato.montrack.databinding.ActivityPemasukkanBinding

class PemasukkanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPemasukkanBinding
    private lateinit var db : AktivitasDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = AktivitasDatabaseHelper(this)

        binding = ActivityPemasukkanBinding.inflate(layoutInflater)
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