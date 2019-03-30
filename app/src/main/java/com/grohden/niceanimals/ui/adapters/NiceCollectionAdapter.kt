package com.grohden.niceanimals.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.grohden.niceanimals.AppConstants
import com.grohden.niceanimals.R
import com.grohden.niceanimals.realm.entities.NiceAnimal
import com.grohden.niceanimals.shibe.service.AnimalType
import com.grohden.niceanimals.ui.holders.NiceAnimalViewHolder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import io.realm.RealmRecyclerViewAdapter
import io.realm.RealmResults
import java.util.concurrent.TimeUnit

class NiceCollectionAdapter(results: RealmResults<NiceAnimal>, private val type: AnimalType) :
  RealmRecyclerViewAdapter<NiceAnimal, NiceAnimalViewHolder>(results, true) {

  private val onReachBottomSubject = PublishSubject.create<Int>()
  private val clickSubject = PublishSubject.create<Int>()

  fun onReachBottom(): Observable<Int> = onReachBottomSubject

  fun observeClicks(): Observable<Int> {
    return clickSubject
      .throttleWithTimeout(
        AppConstants.CLICK_THROTTLE_TIMEOUT,
        TimeUnit.MILLISECONDS,
        AndroidSchedulers.mainThread()
      )
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NiceAnimalViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val v = inflater.inflate(
      R.layout.animal_card,
      parent,
      false
    )

    return NiceAnimalViewHolder(v, clickSubject)
  }

  override fun onBindViewHolder(niceHolder: NiceAnimalViewHolder, position: Int) {
    val animalURL = getItem(position)
    niceHolder.bindAnimal(animalURL!!)

    data?.size?.let { size ->
      if (position == size - 1) {
        onReachBottomSubject.onNext(position)
      }
    }
  }

  override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
    super.onDetachedFromRecyclerView(recyclerView)
    onReachBottomSubject.onComplete()
  }
}