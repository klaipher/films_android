package com.example.films.interfaces;

import androidx.recyclerview.widget.RecyclerView;

public interface CustomizableFragment {

    void changeLayout(RecyclerView.LayoutManager layoutManager);

    void bindData(String query);

}
