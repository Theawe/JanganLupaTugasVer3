package id.java.janganlupatugas.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import id.java.janganlupatugas.R;
import id.java.janganlupatugas.history.HistoryAdapter;
import id.java.janganlupatugas.history.HistoryViewModel;


public class HistoryFragment extends Fragment {
    public static final int ADD_TUGAS_REQUEST = 1;
    public static final int DETAIL_TUGAS_REQUEST = 2;
    public static final int RESULT_OK = -1;
    HistoryViewModel historyViewModel;
    HistoryAdapter adapter = new HistoryAdapter();
    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewHistory);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        historyViewModel = new ViewModelProvider(this,new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(HistoryViewModel.class);
        historyViewModel.getAllHistory().observe(getViewLifecycleOwner(),histories -> {
            adapter.submitList(histories);
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                historyViewModel.delete(adapter.getHistoryAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getContext(), "History deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        return view;
    }
}