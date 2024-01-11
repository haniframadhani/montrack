package triplegato.montrack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import triplegato.montrack.databinding.ActivityPemasukkanBinding
import triplegato.montrack.databinding.ActivityPengeluaranBinding

class PengeluaranActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPengeluaranBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPengeluaranBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.kembali.setOnClickListener{
            finish()
        }
    }
}