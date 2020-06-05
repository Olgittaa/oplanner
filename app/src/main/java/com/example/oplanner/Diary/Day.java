package com.example.oplanner.Diary;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.oplanner.R;
import com.example.oplanner.tasks.DataConverter;

import java.util.Date;
import java.util.Objects;

@Entity
public class Day {

    @PrimaryKey(autoGenerate = true)
    private int id = 0;
    private String description;

    @TypeConverters(DataConverter.class)
    private Date date = new Date();

    private int image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Day day = (Day) o;
        return id == day.id &&
                image == day.image &&
                Objects.equals(description, day.description) &&
                Objects.equals(date, day.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, date, image);
    }
}