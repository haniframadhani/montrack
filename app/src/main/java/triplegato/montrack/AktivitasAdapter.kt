package triplegato.montrack

import android.content.Context
import android.content.Intent
import android.icu.text.NumberFormat
import android.renderscript.Sampler.Value
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AktivitasAdapter(private var aktivitas: List<Aktivitas>, context: Context) :
    RecyclerView.Adapter<AktivitasAdapter.AktivitasViewHolder>()
{

    class AktivitasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val category: TextView = itemView.findViewById(R.id.jenis_aktivitas)
        val jumlah: TextView = itemView.findViewById(R.id.harga)
        val tanggal: TextView = itemView.findViewById(R.id.tanggal)
        val icon: ImageView = itemView.findViewById(R.id.icon_aktivitas)
        val container : ConstraintLayout = itemView.findViewById(R.id.akvitasItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AktivitasViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_layout, parent, false)
        return AktivitasViewHolder(view)
    }

    override fun getItemCount(): Int = aktivitas.size


    override fun onBindViewHolder(holder: AktivitasViewHolder, position: Int) {
        val aktivitas = aktivitas[position]
        holder.category.text = aktivitas.kategori
        val jumlah = aktivitas.jumlah
        var formattedJumlah = NumberFormat.getNumberInstance(Locale("id")).format(jumlah)
        formattedJumlah.toString()
        holder.jumlah.text = "Rp. $formattedJumlah"
        var date = Date(aktivitas.tanggal)
        var dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val formattedDate = dateFormat.format(date)
        holder.tanggal.text = formattedDate

        val colorResId = when (aktivitas.jenis) {
            "pemasukkan" -> R.color.Alien_Armpit
            "pengeluaran" -> R.color.Carmine
            else -> R.color.Carmine
        }
        val backgroundColor = ContextCompat.getColor(holder.itemView.context, colorResId)
        holder.icon.setBackgroundColor(backgroundColor)

        val drawableResId = when (aktivitas.kategori) {
            "makanan" -> R.drawable.fastfood
            "internet" -> R.drawable.language
            "kendaraan" -> R.drawable.directions_car
            "obat" -> R.drawable.pill
            "gawai" -> R.drawable.phone_iphone
            "belanja" -> R.drawable.shopping_bag
            "travelling" -> R.drawable.flight_takeoff
            "gim" -> R.drawable.sports_esports
            "olahraga" -> R.drawable.sports_volleyball
            "kosmetik" -> R.drawable.health_and_beauty
            "gaji" -> R.drawable.mintmark
            "hadiah" -> R.drawable.featured_seasonal_and_gifts
            "investasi" -> R.drawable.show_chart
            else -> R.drawable.fastfood
        }
        holder.icon.setImageResource(drawableResId)

        holder.container.setOnClickListener{
            if (aktivitas.jenis == "pemasukkan"){
                val intent = Intent(holder.itemView.context, PemasukkanActivity::class.java).apply {
                    putExtra("aktivitas_id", aktivitas.id)
                }
                holder.itemView.context.startActivity(intent)
            }else if (aktivitas.jenis == "pengeluaran"){
                val intent = Intent(holder.itemView.context, PengeluaranActivity::class.java).apply {
                    putExtra("aktivitas_id", aktivitas.id)
                }
                holder.itemView.context.startActivity(intent)
            }
        }

    }
    fun refreshData( newAktivitas: List<Aktivitas>){
        aktivitas = newAktivitas
        notifyDataSetChanged()
    }

}