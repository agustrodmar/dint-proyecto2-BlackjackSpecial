package com.arodmar432p.blackjackspecial.cardGames.repository

import com.arodmar432p.blackjackspecial.cardGames.data.Ranking
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot


/**
 * Repository for managing rankings in Firestore.
 */
class RankingRepository {
    private val db = FirebaseFirestore.getInstance()

    /**
     * Saves a ranking to Firestore.
     *
     * @param ranking The ranking to save.
     */
    fun saveRanking(ranking: Ranking) {
        val rankingRef = db.collection("rankings").document(ranking.uid)
        rankingRef.set(ranking)
    }

    /**
     * Retrieves the top 3 rankings from Firestore, ordered by victories in descending order.
     *
     * @return A Task that will be completed with the query results.
     */
    fun getRankings(): Task<QuerySnapshot> {
        return db.collection("rankings")
            .orderBy("victories", Query.Direction.DESCENDING)
            .limit(3)
            .get()
    }
}