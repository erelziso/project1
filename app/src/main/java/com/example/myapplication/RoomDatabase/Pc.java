package com.example.myapplication.RoomDatabase;

import android.net.Uri;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "PcTable")
public class Pc
{
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo (name = "pcId")
    public long pcId;

    @ColumnInfo (name = "photoUri")
    public String uri;

    @ColumnInfo (name ="pcName")
    public String pcName;

    @ColumnInfo (name ="processorName ")
    public String processorName;

    @ColumnInfo (name ="ramSize")
    public int ram;


    public Pc() {

    }
    public Pc( String uriPc, String pcName, String processorName, int ramAmount)
    {
        this.uri = uriPc;
        this.pcName = pcName;
        this.processorName = processorName;
        this.ram =  ramAmount;

    }

    public long getPcId() {
        return pcId;
    }

    public String getUri() {
        return uri;
    }

    public String getPcName() {
        return pcName;
    }

    public String getProcessorName() {
        return processorName;
    }

    public int getRam() {
        return ram;
    }

    public void setPcId(long pcId) {
        this.pcId = pcId;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setPcName(String pcName) {
        this.pcName = pcName;
    }

    public void setProcessorName(String processorName) {
        this.processorName = processorName;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }
}
