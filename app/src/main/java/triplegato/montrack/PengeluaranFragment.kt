package triplegato.montrack

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import triplegato.montrack.databinding.FragmentPengeluaranBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PengeluaranFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PengeluaranFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentPengeluaranBinding?= null
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
        // Inflate the layout for this fragment
        _binding = FragmentPengeluaranBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val kategori = listOf(
            Kategori("makanan",R.drawable.fastfood),
            Kategori("internet",R.drawable.language),
            Kategori("kendaraan",R.drawable.directions_car),
            Kategori("obat",R.drawable.pill),
            Kategori("gawai",R.drawable.phone_iphone),
            Kategori("belanja",R.drawable.shopping_bag),
            Kategori("travelling",R.drawable.flight_takeoff),
            Kategori("gim",R.drawable.sports_esports),
            Kategori("olahraga",R.drawable.sports_volleyball),
            Kategori("kosmetik",R.drawable.health_and_beauty)
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
            val jenis = "pengeluaran"
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
         * @return A new instance of fragment PengeluaranFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PengeluaranFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}