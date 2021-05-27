package id.java.janganlupatugas.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import id.java.janganlupatugas.DetailTugasActivity;
import id.java.janganlupatugas.R;
import id.java.janganlupatugas.Tugas;
import id.java.janganlupatugas.TugasAdapter;
import id.java.janganlupatugas.TugasViewModel;
import id.java.janganlupatugas.activity.AddTugasActivity;
import id.java.janganlupatugas.history.History;
import id.java.janganlupatugas.history.HistoryViewModel;


public class ListFragment extends Fragment {
    public static final int ADD_TUGAS_REQUEST = 1;
    public static final int DETAIL_TUGAS_REQUEST = 2;
    public static final int RESULT_OK = -1;
    TugasViewModel tugasViewModel;
    TugasAdapter adapter = new TugasAdapter();
    public ListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        tugasViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(TugasViewModel.class);
        tugasViewModel.getAllTugas().observe(getViewLifecycleOwner(),tugases -> {
            adapter.submitList(tugases);
        });
        adapter.setOnItemClickListener(new TugasAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Tugas tugas, int position) {
                Intent intent = new Intent(getContext(), DetailTugasActivity.class);
                intent.putExtra(AddTugasActivity.EXTRA_TITLE,tugas.getJudul());
                intent.putExtra(AddTugasActivity.EXTRA_DESCRIPTION,tugas.getDeskripsi());
                intent.putExtra(AddTugasActivity.EXTRA_DEADLINE,tugas.getTanggal());
                intent.putExtra(AddTugasActivity.EXTRA_WAKTU,tugas.getWaktu());
                intent.putExtra("position",position);
                startActivityForResult(intent,DETAIL_TUGAS_REQUEST);
            }
        });



        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddTugasActivity.class);
                startActivityForResult(intent,ADD_TUGAS_REQUEST);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_TUGAS_REQUEST && resultCode == RESULT_OK){
            Tugas tugas = new Tugas(data.getStringExtra(AddTugasActivity.EXTRA_TITLE),data.getStringExtra(AddTugasActivity.EXTRA_DESCRIPTION),data.getStringExtra(AddTugasActivity.EXTRA_DEADLINE),
                    data.getStringExtra(AddTugasActivity.EXTRA_WAKTU));
            tugasViewModel.insert(tugas);
            Toast.makeText(
                    getActivity().getApplicationContext(),
                    "Tugas Saved",
                    Toast.LENGTH_LONG).show();
        }else if(requestCode == DETAIL_TUGAS_REQUEST && resultCode == RESULT_OK){
            if(data.hasExtra("delete_data")){
                Log.d("delete", Integer.toString(data.getIntExtra("delete_data",1)));
                int position = data.getIntExtra("delete_data",-1);
                Tugas tugas = adapter.getTugasAt(position);
                tugasViewModel.delete(tugas);
                History history = new History(data.getStringExtra(AddTugasActivity.EXTRA_TITLE),data.getStringExtra(AddTugasActivity.EXTRA_DESCRIPTION),data.getStringExtra(AddTugasActivity.EXTRA_DEADLINE),
                        data.getStringExtra(AddTugasActivity.EXTRA_WAKTU));
                HistoryViewModel historyViewModel;
                historyViewModel = new ViewModelProvider(this,new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(HistoryViewModel.class);
                historyViewModel.insert(history);
            }
        }else {
            Toast.makeText(
                    getActivity().getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }


}