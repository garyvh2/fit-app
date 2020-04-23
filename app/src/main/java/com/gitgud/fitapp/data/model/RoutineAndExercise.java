package com.gitgud.fitapp.data.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class RoutineAndExercise {
    @Embedded public Routine routine;
    @Relation(
            parentColumn = "id",
    entityColumn = "routineId"
    )

    public List<Exercise> exerciseList;

}
