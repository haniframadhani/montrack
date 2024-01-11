package triplegato.montrack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import triplegato.montrack.databinding.ActivityMainBinding
import triplegato.montrack.databinding.ActivityPemasukkanBinding

class PemasukkanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPemasukkanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPemasukkanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.kembali.setOnClickListener{
            finish()
        }
    }
}