package triplegato.montrack

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date

class AktivitasAdapter(private var aktivitas: List<Aktivitas>, context: Context) :
    RecyclerView.Adapter<AktivitasAdapter.AktivitasViewHolder>()
{
    class AktivitasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val jenisAktivitas: TextView = itemView.findViewById(R.id.jenis_aktivitas)
        val jumlah: TextView = itemView.findViewById(R.id.harga)
        val tanggal: TextView = itemView.findViewById(R.id.tanggal)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AktivitasViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_layout, parent, false)
        return AktivitasViewHolder(view)
    }

    override fun getItemCount(): Int = aktivitas.size


    override fun onBindViewHolder(holder: AktivitasViewHolder, position: Int) {
        val aktivitas  = aktivitas[position]
        holder.jenisAktivitas.text =  aktivitas.kategori
        val jumlah = aktivitas.jumlah.toString()
        holder.jumlah.text = "Rp.$jumlah"
        var date = Date(aktivitas.tanggal)
        var dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val formattedDate = dateFormat.format(date)
        holder.tanggal.text = formattedDate
    }

    fun refreshData( newAktivitas: List<Aktivitas>){
        aktivitas = newAktivitas
        notifyDataSetChanged()
    }
}