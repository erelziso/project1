package com.example.myapplication.RoomDatabase;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PcDao
{
    @Query("SELECT * FROM PcTable")
    List <Pc> getAll();

    @Query ("DELETE FROM PcTable WHERE pcId == :id")
    void deletePcById(long id);

    @Insert
    void insert(Pc pc);


}
