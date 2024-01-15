package triplegato.montrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import triplegato.montrack.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var db : AktivitasDatabaseHelper
    private lateinit var aktivitasAdapter : AktivitasAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AktivitasDatabaseHelper(this )
        aktivitasAdapter = AktivitasAdapter(db.getAllAktivitas(), this)

        binding.listAktivitas.layoutManager = LinearLayoutManager(this)
        binding.listAktivitas.adapter = aktivitasAdapter

        binding.tambah.setOnClickListener{
            val intent = Intent(this, ActivityActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        aktivitasAdapter.refreshData(db.getAllAktivitas())
        binding.outcomeAmountIncome.text = totalIncome()
        binding.outcomeAmountOutcome.text = totalOutcome()

    }

    fun totalIncome() : String{
        var total = db.getIncomeTotal().toDouble()
        total = total/1000
        var totalString = ""
        if(total >= 1.0) {
            var totalInt = total.toInt()
            totalString = totalInt.toString()
        } else {
            totalString = total.toString()
        }
        return "$totalString"+"K"
    }

    fun totalOutcome() : String{
        var total: Double = db.getOutcomeTotal().toDouble()
        total = total/1000
        var totalString = ""
        if(total >= 1.0) {
            var totalInt = total.toInt()
            totalString = totalInt.toString()
        } else {
            totalString = total.toString()
        }
        return "$totalString"+"K"
    }
}