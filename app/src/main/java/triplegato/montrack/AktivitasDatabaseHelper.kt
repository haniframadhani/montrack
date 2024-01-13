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
}