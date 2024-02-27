package com.arodmar432p.blackjackspecial.cardGames.repository

import com.arodmar432p.blackjackspecial.cardGames.data.Ranking
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

/**
 *
 */
class RankingRepository {
    private val db = FirebaseFirestore.getInstance()

    fun saveRanking(ranking: Ranking) {
        val rankingRef = db.collection("rankings").document(ranking.uid)
        rankingRef.set(ranking)
    }

    fun getRankings(): Task<QuerySnapshot> {
        return db.collection("rankings")
            .orderBy("victories", Query.Direction.DESCENDING)
            .limit(3)
            .get()
    }
}