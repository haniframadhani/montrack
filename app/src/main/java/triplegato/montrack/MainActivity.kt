package triplegato.montrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import triplegato.montrack.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tambah.setOnClickListener{
            val intent = Intent(this, ActivityActivity::class.java)
            startActivity(intent)
        }
    }
}