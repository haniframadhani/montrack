package triplegato.montrack

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import triplegato.montrack.databinding.FragmentPemasukkanBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PemasukkanFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PemasukkanFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentPemasukkanBinding?= null
    private val binding get() = _binding!!
    private lateinit var db: AktivitasDatabaseHelper
    private var selectedDate: Long = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        db = AktivitasDatabaseHelper(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPemasukkanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val kategori = listOf(
            Kategori("gaji", R.drawable.mintmark),
            Kategori("hadiah",R.drawable.featured_seasonal_and_gifts),
            Kategori("investasi",R.drawable.show_chart)
        )
        val arrayAdapter = KategoriAdapter(requireContext(), R.layout.item_dropdown, kategori)
        binding.listKategori.setAdapter(arrayAdapter)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()
        binding.date.setOnClickListener {
            showDatePickerDialog()
        }
        binding.simpan.setOnClickListener {
            val jenis = "pemasukkan"
            val jumlahString = binding.jumlahValue.text.toString()
            val jumlahNumber = if(jumlahString.isNotEmpty()){
                jumlahString.toInt()
            }else {
                0
            }
            val kategori = binding.listKategori.text.toString()
            val deskripsi = binding.deskripsiValue.text.toString()
            val tanggal = selectedDate
            val aktivitas = Aktivitas(0,kategori,tanggal,jumlahNumber,jenis,"",deskripsi)
            db.insertAktivitas(aktivitas)
            activity.finish()
            Toast.makeText(requireContext(),"aktivitas tersimpan", Toast.LENGTH_SHORT).show()
        }
        binding.batal.setOnClickListener{
            activity.finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun showDatePickerDialog(){
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(),
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                selectedDate = calendar.timeInMillis
                binding.date.setText(SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.time))
            }, year, month, dayOfMonth)

        datePickerDialog.show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PemasukkanFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PemasukkanFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}