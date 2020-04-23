package com.gitgud.fitapp.data.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserAndHistoryStat {

    @Embedded public  User user;
    @Relation(
            parentColumn = "id",
            entityColumn = "userId"
    )
    public List<HistoryStat> historyStatList;
}
