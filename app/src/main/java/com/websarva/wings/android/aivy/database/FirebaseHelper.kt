package com.example.myapp.firebase

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseHelper {
    private val database: DatabaseReference = Firebase.database.reference

    /**
     * Realtime Database にデータを書き込む
     * @param path 書き込み先のパス (例: "users/user1")
     * @param data 書き込むデータ (任意の型)
     * @param onSuccess 成功時に実行するコールバック
     * @param onFailure 失敗時に実行するコールバック
     */
    fun writeData(
        path: String,
        data: Any,
        onSuccess: (() -> Unit)? = null,
        onFailure: ((Exception) -> Unit)? = null
    ) {
        database.child(path).setValue(data)
            .addOnSuccessListener {
                onSuccess?.invoke()
            }
            .addOnFailureListener { exception ->
                onFailure?.invoke(exception)
            }
    }

    /**
     * Realtime Database からデータを取得
     * @param path 読み取り先のパス (例: "users/user1")
     * @param onSuccess 成功時に取得したデータを渡すコールバック
     * @param onFailure 失敗時に実行するコールバック
     */
    fun readData(
        path: String,
        onSuccess: (Any?) -> Unit,
        onFailure: ((Exception) -> Unit)? = null
    ) {
        database.child(path).get()
            .addOnSuccessListener { dataSnapshot ->
                onSuccess(dataSnapshot.value)
            }
            .addOnFailureListener { exception ->
                onFailure?.invoke(exception)
            }
    }
}
