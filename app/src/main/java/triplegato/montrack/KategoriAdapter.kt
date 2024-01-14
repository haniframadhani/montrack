package triplegato.montrack

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class KategoriAdapter(context: Context, private val resource: Int, objects: List<Kategori>) :
    ArrayAdapter<Kategori>(context, resource, objects)  {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)
        val kategori = getItem(position)!!

        val imageView = view.findViewById<ImageView>(R.id.imageView2)
        imageView.setImageResource(kategori.drawableId)

        val textView = view.findViewById<TextView>(R.id.textView2)
        textView.text = kategori.nama

        return view
    }
}