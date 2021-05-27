package id.java.janganlupatugas;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tugas")
public class Tugas {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    @ColumnInfo(name = "judul")
    private String judul;
    @NonNull
    @ColumnInfo(name = "deskripsi")
    private String deskripsi;
    @NonNull
    @ColumnInfo(name = "tanggal")
    private String tanggal;
    @NonNull
    @ColumnInfo(name = "waktu")
    private String waktu;

    public Tugas(@NonNull String judul, String deskripsi, String tanggal,String waktu){
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.tanggal = tanggal;
        this.waktu = waktu;
    }
    public void setId(int id){this.id = id;}

    public int getId(){return id;}

    public String getJudul(){return judul;}
    public String getDeskripsi(){return deskripsi;}
    public String getTanggal(){return tanggal;}
    public String getWaktu(){return waktu;}
}