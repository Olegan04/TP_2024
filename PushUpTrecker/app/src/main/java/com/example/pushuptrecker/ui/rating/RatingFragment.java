package com.example.pushuptrecker.ui.rating;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pushuptrecker.databinding.FragmentRatingBinding;

import org.jetbrains.annotations.NotNull;

public class RatingFragment extends Fragment {

    private FragmentRatingBinding binding;
    private TextView textView;
    private Spinner typeOfTraining;
    private TextView forDay;
    private TextView forWeek;
    private TextView forMonth;
    private TextView forYear;
    private TextView forAllTime;

    private Button toLeaderboardBtn;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = init(inflater, container);

        toLeaderboardBtn.setOnClickListener(v->{
            Intent i = new Intent();
        });
        return root;
    }

    private View init(@NotNull LayoutInflater inflater, ViewGroup container){
        RatingViewModel ratingViewModel = new ViewModelProvider(this).get(RatingViewModel.class);
        binding = FragmentRatingBinding.inflate(inflater, container, false);
        textView = binding.dayInRow;
        typeOfTraining = binding.typeOfTraining;
        forDay = binding.forDay;
        forWeek = binding.forWeek;
        forMonth = binding.forMonth;
        forYear = binding.forYear;
        forAllTime = binding.forAllTime;
        toLeaderboardBtn = binding.toLeaderboardBtn;

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}