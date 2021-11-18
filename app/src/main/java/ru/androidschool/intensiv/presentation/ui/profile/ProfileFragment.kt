package ru.androidschool.intensiv.presentation.ui.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.fragment_profile.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.db.TheMovieDatabase

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var profileTabLayoutTitles: Array<String>

    private var profilePageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            Toast.makeText(
                requireContext(),
                "Selected position: $position",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Picasso.get()
            .load(R.drawable.ic_avatar)
            .transform(CropCircleTransformation())
            .placeholder(R.drawable.ic_avatar)
            .into(avatar)

        profileTabLayoutTitles = resources.getStringArray(R.array.tab_titles)

        val profileAdapter = ProfileAdapter(
            this,
            profileTabLayoutTitles.size
        )
        doppelgangerViewPager.adapter = profileAdapter
        val db = TheMovieDatabase.getInstance(requireContext()).movieDetailDao()
        doppelgangerViewPager.registerOnPageChangeCallback(profilePageChangeCallback)
        db.getMovieDetailGenres()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                it.forEach { Log.e("TAG", "onViewCreated: movieDetail =${it.movieDetail} genre =${it.genre}" ) }
            },{})
        TabLayoutMediator(tabLayout, doppelgangerViewPager) { tab, position ->

            // Выделение первой части заголовка таба
            // Название таба
            val title = profileTabLayoutTitles[position]
            // Раздеряем название на части. Первый элемент будет кол-во
            val parts = profileTabLayoutTitles[position].split(" ")
            val number = parts[0]
            val spannableStringTitle = SpannableString(title)
            spannableStringTitle.setSpan(RelativeSizeSpan(2f), 0, number.count(), 0)

            tab.text = spannableStringTitle
        }.attach()
    }
}
