package com.android.app_allforone.fragment;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.app_allforone.R;
import com.android.app_allforone.adapters.MovieAdapter;
import com.android.app_allforone.adapters.MovieItemClickListener;
import com.android.app_allforone.adapters.SliderPagerAdapter;
import com.android.app_allforone.models.Movie;
import com.android.app_allforone.ui.MovieDetailActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HomePageFragment extends Fragment implements MovieItemClickListener {

    private ViewPager sliderpaper;
    DatabaseReference databaseReference;
    MovieAdapter movieAdapter;
    SliderPagerAdapter sliderPagerAdapter;
    ArrayList<Movie> listslide;
    StorageReference storageReference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference("movies");
        storageReference = FirebaseStorage.getInstance().getReference("images/");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        sliderpaper = view.findViewById(R.id.silder_paper);
        listslide = (ArrayList<Movie>) getAllMovies();
        sliderPagerAdapter = new SliderPagerAdapter(getActivity(), listslide, HomePageFragment.this) ;
        sliderpaper.setAdapter(sliderPagerAdapter);

        StorageReference s = storageReference.child("Frozen 2 small.jpg");
        s.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                System.out.println(uri);
            }
        });

        //Set up time for changing the theme
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 2000, 4000);
        TabLayout indicator = view.findViewById(R.id.indicator);
        indicator.setupWithViewPager(sliderpaper, true);

        //Recyclerview Setup
        RecyclerView movieRV = view.findViewById((R.id.rv_movie));

//        ArrayList<String> cate = new ArrayList<>();
//        cate.add("Hoạt hình");
//        cate.add("Hài hước");
//        cate.add("Phiêu lưu");
//        cate.add("Điện ảnh");
//        Movie movie = new Movie("Frozen 2", "2019", "Nữ Hoàng Băng Giá 2 kể về câu chuyện cùng dấn thân vào một cuộc phiêu lưu xa xôi thú vị, hai chị em Anna và Elsa đi đến chốn rừng sâu để tìm kiếm sự thật về bí ẩn cổ xưa của vương quốc mình. Tất cả những gì Anna & Elsa biết về bản thân, lịch sử và gia đình của họ đều bị thử thách khi họ bị cuốn vào một chuyến đi đầy quả cảm đến với vùng đất phía bắc bí ẩn ngoài Arendelle được báo trước.", 103, "Mỹ", "https://firebasestorage.googleapis.com/v0/b/allforone-266c9.appspot.com/o/images%2FFrozen%202%20small.jpg?alt=media&token=72331344-d78a-45d8-913e-c1d1872eb6f6", "https://firebasestorage.googleapis.com/v0/b/allforone-266c9.appspot.com/o/images%2FFrozen%202.jpg?alt=media&token=0f51e97f-fd32-4522-ab99-45ca28b664c7", "https://firebasestorage.googleapis.com/v0/b/allforone-266c9.appspot.com/o/movies%2FFrozen%202%20-%20Thuyết%20Minh%20-%20MọtPhim%20TV.mp4?alt=media&token=090b9389-c125-44c0-bb5c-c169975b1e77", cate);
//        databaseReference.child(movie.getName()).setValue(movie);
//
//        ArrayList<String> cate1 = new ArrayList<>();
//        cate1.add("Hài hước");
//        cate1.add("Phiêu lưu");
//        cate1.add("Điện ảnh");
//        Movie movie1 = new Movie("Test", "2019", "Nữ Hoàng Băng Giá 2 kể về câu chuyện cùng dấn thân vào một cuộc phiêu lưu xa xôi thú vị, hai chị em Anna và Elsa đi đến chốn rừng sâu để tìm kiếm sự thật về bí ẩn cổ xưa của vương quốc mình. Tất cả những gì Anna & Elsa biết về bản thân, lịch sử và gia đình của họ đều bị thử thách khi họ bị cuốn vào một chuyến đi đầy quả cảm đến với vùng đất phía bắc bí ẩn ngoài Arendelle được báo trước.", 103, "Mỹ", "https://firebasestorage.googleapis.com/v0/b/allforone-266c9.appspot.com/o/images%2FFrozen%202%20small.jpg?alt=media&token=72331344-d78a-45d8-913e-c1d1872eb6f6", "https://firebasestorage.googleapis.com/v0/b/allforone-266c9.appspot.com/o/images%2FFrozen%202.jpg?alt=media&token=0f51e97f-fd32-4522-ab99-45ca28b664c7", "https://firebasestorage.googleapis.com/v0/b/allforone-266c9.appspot.com/o/movies%2FFrozen%202%20-%20Thuyết%20Minh%20-%20MọtPhim%20TV.mp4?alt=media&token=090b9389-c125-44c0-bb5c-c169975b1e77", cate1);
//        databaseReference.child(movie1.getName()).setValue(movie1);

        ArrayList<Movie> movies = (ArrayList<Movie>) getAllMovies();
        movieAdapter = new MovieAdapter(getActivity(), movies, HomePageFragment.this);
        movieRV.setAdapter(movieAdapter);
        movieRV.setLayoutManager(new GridLayoutManager(getActivity(), 2){
            @Override
            public boolean canScrollVertically() {
                return false;
            }

            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onMovieItemClick(Movie movie, ImageView movieImageView) {
        //Here we send movie information to detail activity
        //also we ll create the transition animation between the two activity
        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
        intent.putExtra("name", movie.getName());
        intent.putExtra("image", movie.getImage());
        intent.putExtra("coverImage", movie.getCoverImage());
        intent.putExtra("duration", movie.getDuration());
        intent.putExtra("nation", movie.getNation());
        intent.putExtra("desc", movie.getDesc());
        intent.putExtra("link", movie.getLink());
        intent.putExtra("release", movie.getRelease());

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), movieImageView, "sharedName");

        startActivity(intent, options.toBundle());
    }

    @Override
    public void onMoviesSliderClick(Movie movie, ImageView movieImageView) {

    }

    class SliderTimer extends TimerTask {

        @Override
        public void run() {
            if(isAdded()){
                getActivity().runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        if(sliderpaper.getCurrentItem() < (listslide.size() - 1)){
                            sliderpaper.setCurrentItem(sliderpaper.getCurrentItem()+1);
                        }
                        else
                            sliderpaper.setCurrentItem(0);
                    }
                });
            }
        }
    }

    private ArrayList<Movie> getAllMovies()
    {
        ArrayList<Movie> movies = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot movieSnapshot:snapshot.getChildren()){
                    Movie movie = (Movie) movieSnapshot.getValue(Movie.class);
                    movies.add(movie);
                    movieAdapter.notifyDataSetChanged();
                    sliderPagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return movies;
    }
}