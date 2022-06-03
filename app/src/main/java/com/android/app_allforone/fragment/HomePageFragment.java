package com.android.app_allforone.fragment;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
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

//        ArrayList<String> cate = new ArrayList<>();
//        cate.add("Hoạt hình");
//        cate.add("Phiêu lưu");
//        cate.add("Hài hước");
//        Movie movie = new Movie("Frozen 2", "2019", "Nữ Hoàng Băng Giá 2 kể về câu chuyện cùng dấn thân vào một cuộc phiêu lưu xa xôi thú vị, hai chị em Anna và Elsa đi đến chốn rừng sâu để tìm kiếm sự thật về bí ẩn cổ xưa của vương quốc mình. Tất cả những gì Anna & Elsa biết về bản thân, lịch sử và gia đình của họ đều bị thử thách khi họ bị cuốn vào một chuyến đi đầy quả cảm đến với vùng đất phía bắc bí ẩn ngoài Arendelle được báo trước.", 103, "Mỹ", "", "", "", cate);
//        databaseReference.child("Frozen 2").setValue(movie);
//
//        cate.clear();
//        cate.add("Phiêu lưu");
//        cate.add("Hành động");
//        cate.add("Siêu anh hùng");
//        cate.add("Viễn tưởng");
//        movie = new Movie("Morbius", "2022", "Giáo Sư Ma Cà Rồng kể về một huyền thoại Marvel mới sắp lộ diện. Là ác nhân hay anh hùng? Sẽ phá hủy hay chữa lành thế giới này?", 104, "Mỹ", "", "", "", cate);
//        databaseReference.child("Morbius").setValue(movie);
//
//        cate.clear();
//        cate.add("Võ thuật");
//        cate.add("Kiếm hiệp");
//        movie = new Movie("Thần bút cửu long", "2022", "Thần Bút Cửu Long xoay quanh câu chuyện Thiếu Khang Kì Lăng điều tra và phá vỡ các cơ mật có sức mạnh ma thuật của “Cửu Long Bút”, theo chỉ dụ mật từ hoàng đế. Qúa trình điều tra, Kì Lăng phát hiện những thứ này có liên quan đến cái chết của cha mình. Sau nhiều nỗ lực, các cơ quan ở “Cửu Long Bút” đã được mở ra, nhờ vậy mà sự thật về vụ án cũng được mở ra. Cùng lúc đó, các thế lực ở triều đình bắt đầu những động thái ngầm và điều này khiến cho mọi chuyện trở nên cực kỳ phức tạp.", 75, "Trung Quốc", "", "", "", cate);
//        databaseReference.child("Thần bút cửu long").setValue(movie);
//
//        cate.clear();
//        cate.add("Khoa học");
//        cate.add("Viễn tưởng");
//        movie = new Movie("Song thể", "2022", "Cuộc Đấu Tay Đôi một người phụ nữ lựa chọn thủ tục nhân bản sau khi nhận được chẩn đoán giai đoạn cuối nhưng khi cô ấy phục hồi, nỗ lực để nhân bản của cô ấy ngừng hoạt động đã thất bại, dẫn đến một cuộc đấu tay đôi do tòa án ủy thác đến chết.", 94, "Mỹ", "", "", "", cate);
//        databaseReference.child("Song thể").setValue(movie);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        sliderpaper = view.findViewById(R.id.silder_paper);
        listslide = (ArrayList<Movie>) getAllMovies();
        sliderPagerAdapter = new SliderPagerAdapter(getActivity(), listslide, HomePageFragment.this) ;
        sliderpaper.setAdapter(sliderPagerAdapter);

        //Set up time for changing the theme
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 2000, 4000);
        TabLayout indicator = view.findViewById(R.id.indicator);
        indicator.setupWithViewPager(sliderpaper, true);

        //Recyclerview Setup
        RecyclerView movieRV = view.findViewById((R.id.rv_movie));

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
                getActivity().runOnUiThread(() -> {
                    if(sliderpaper.getCurrentItem() < (listslide.size() - 1)){
                        sliderpaper.setCurrentItem(sliderpaper.getCurrentItem()+1);
                    }
                    else
                        sliderpaper.setCurrentItem(0);
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