package com.example.films.model.entities;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.films.util.ViewSize;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Section<T> extends ArrayList<T> {
    private final Class<T> genericType;
    private String sectionName;
    private String sectionContentDescription;
    private LiveData<List<T>> listLiveData;
    private ViewSize mViewSize;


    public Section(String sectionName, String sectionContentDescription, Class<T> c, MutableLiveData<List<T>> mutableLiveData, ViewSize viewSize) {
        this.sectionName = sectionName;
        listLiveData = mutableLiveData;
        this.genericType = c;
        this.sectionContentDescription = sectionContentDescription;
        this.mViewSize = viewSize;
    }

    public Class<T> getGenericType() {
        return genericType;
    }

    public String getSectionName() {
        return sectionName;
    }

    public LiveData<List<T>> getLiveData() {
        return listLiveData;
    }

    public void setLiveData(LiveData<List<T>> liveData) {
        listLiveData = liveData;
    }

    public String getSectionContentDescription() {
        return sectionContentDescription;
    }

    public void setSectionContentDescription(String sectionContentDescription) {
        this.sectionContentDescription = sectionContentDescription;
    }

    public ViewSize getViewSize() {
        return mViewSize;
    }

    public void setViewSize(ViewSize viewSize) {
        mViewSize = viewSize;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Section)) return false;
        if (!super.equals(o)) return false;
        Section<?> section = (Section<?>) o;
        return getGenericType().equals(section.getGenericType()) && getSectionName().equals(section.getSectionName()) && Objects.equals(getSectionContentDescription(), section.getSectionContentDescription()) && Objects.equals(getLiveData(), section.getLiveData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getGenericType(), getSectionName(), getSectionContentDescription(), getLiveData());
    }
}
