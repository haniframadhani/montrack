package triplegato.montrack

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AktivitasDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "montrack.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "aktivitas"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TANGGAL = "tanggal"
        private const val COLUMN_JENIS_AKTIVITAS = "jenis_aktivitas"
        private const val COLUMN_KATEGORI = "kategori"
        private const val COLUMN_JUMLAH = "jumlah"
        private const val COLUMN_LOKASI = "lokasi"
        private const val COLUMN_DESKRIPSI = "deskripsi"
        private const val PENGELUARAN = "pengeluaran"
        private const val PEMASUKAN = "pemasukkan"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY," +
                "$COLUMN_TANGGAL LONG NOT NULL," +
                "$COLUMN_JENIS_AKTIVITAS TEXT NOT NULL," +
                "$COLUMN_KATEGORI TEXT NOT NULL," +
                "$COLUMN_JUMLAH INTEGER NOT NULL," +
                "$COLUMN_LOKASI TEXT," +
                "$COLUMN_DESKRIPSI TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val createTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(createTableQuery)
        onCreate(db)
    }

    fun insertAktivitas(aktivitas: Aktivitas) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TANGGAL, aktivitas.tanggal)
            put(COLUMN_JENIS_AKTIVITAS, aktivitas.jenis)
            put(COLUMN_KATEGORI, aktivitas.kategori)
            put(COLUMN_JUMLAH, aktivitas.jumlah)
            put(COLUMN_LOKASI, aktivitas.lokasi)
            put(COLUMN_DESKRIPSI, aktivitas.deskripsi)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllAktivitas(): List<Aktivitas> {
        val aktivitasList = mutableListOf<Aktivitas>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_TANGGAL DESC"
        val cursor = db.rawQuery(query, null)

        while(cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val tanggal = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_TANGGAL))
            val jenis_aktivitas = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JENIS_AKTIVITAS))
            val kategori = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_KATEGORI))
            val jumlah = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_JUMLAH))
            val lokasi = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOKASI))
            val deskripsi = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESKRIPSI))

            val list = Aktivitas(id, kategori, tanggal, jumlah, jenis_aktivitas, lokasi, deskripsi)
            aktivitasList.add(list)
        }
        cursor.close()
        db.close()
        return aktivitasList
    }

    fun getIncomeTotal(): Int {
        val db = readableDatabase
        val query = "SELECT SUM($COLUMN_JUMLAH) AS Total FROM $TABLE_NAME WHERE $COLUMN_JENIS_AKTIVITAS = 'pemasukkan'"
        val cursor = db.rawQuery(query, null)

        var total = 0

        if (cursor.moveToFirst()) {
            total = cursor.getInt(cursor.getColumnIndexOrThrow("Total"))
        }

        cursor.close()
        db.close()

        return total
    }

    fun getOutcomeTotal(): Int {
        val db = readableDatabase
        val query = "SELECT SUM($COLUMN_JUMLAH) AS Total FROM $TABLE_NAME WHERE $COLUMN_JENIS_AKTIVITAS = 'pengeluaran'"
        val cursor = db.rawQuery(query, null)

        var total = 0

        if (cursor.moveToFirst()) {
            total = cursor.getInt(cursor.getColumnIndexOrThrow("Total"))
        }

        cursor.close()
        db.close()

        return total
    }

}