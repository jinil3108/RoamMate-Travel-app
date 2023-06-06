package com.techholding.android.roammate.utils

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.techholding.android.roammate.model.*
import java.text.SimpleDateFormat
import java.util.*

object DatabaseHelper {

    fun getDatabaseReference(): DatabaseReference {
        return Firebase.database.reference
    }

    fun getPlaces(
        query: String = "",
        onSuccess: (List<Place>, List<Place>) -> Unit,
        onError: (DatabaseError) -> Unit
    ) {
        val database = getDatabaseReference().child("places")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val generalList: MutableList<Place> = mutableListOf()
                val topList: MutableList<Place> = mutableListOf()

                for (childSnapshot in snapshot.children) {
                    if ((childSnapshot.child("name").value.toString().lowercase()
                            .contains(query.lowercase()))
                        || query.isEmpty()
                    ) {
                        generalList.add(
                            Place(
                                childSnapshot.key.toString(),
                                childSnapshot.child("name").value.toString(),
                                childSnapshot.child("city").value.toString(),
                                childSnapshot.child("state").value.toString(),
                                childSnapshot.child("country").value.toString(),
                                childSnapshot.child("latitude").value.toString(),
                                childSnapshot.child("description").value.toString(),
                                childSnapshot.child("longitude").value.toString(),
                                LocationManager.findDistanceFromUserLocation(
                                    childSnapshot.child("latitude").value as Double,
                                    childSnapshot.child("longitude").value as Double
                                ),
                                childSnapshot.child("logo").value.toString(),
                            )
                        )
                    }
                    topList.add(
                        Place(
                            childSnapshot.key.toString(),
                            childSnapshot.child("name").value.toString(),
                            childSnapshot.child("city").value.toString(),
                            childSnapshot.child("state").value.toString(),
                            childSnapshot.child("country").value.toString(),
                            childSnapshot.child("latitude").value.toString(),
                            childSnapshot.child("description").value.toString(),
                            childSnapshot.child("longitude").value.toString(),
                            LocationManager.findDistanceFromUserLocation(
                                childSnapshot.child("latitude").value as Double,
                                childSnapshot.child("longitude").value as Double
                            ),
                            childSnapshot.child("logo").value.toString()
                        )
                    )

                }

                generalList.sortBy {
                    it.distance
                }

                onSuccess.invoke(generalList, topList)
            }

            override fun onCancelled(error: DatabaseError) {
                onError.invoke(error)
            }
        })
    }

    fun getTrips(onSuccess: (List<Trip>) -> Unit, onError: (DatabaseError) -> Unit) {
        val database = getDatabaseReference().child("plan").child(AuthManager.getUserId())

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val tripList: MutableList<Trip> = mutableListOf()
                for (childSnapshot in snapshot.children) {
                    tripList.add(
                        Trip(
                            childSnapshot.key.toString()
                        )
                    )
                }
                onSuccess.invoke(tripList)
            }

            override fun onCancelled(error: DatabaseError) {
                onError.invoke(error)
            }
        })
    }

    fun deleteTrip(title: String, onError: (DatabaseError) -> Unit) {
        val database = getDatabaseReference().child("plan").child(AuthManager.getUserId())
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (childSnapShot in snapshot.children) {
                    if (title == childSnapShot.key.toString()) {
                        childSnapShot.ref.removeValue()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                onError.invoke(error)
            }

        })
    }

    fun getUserFullName(onSuccess: (String?) -> Unit, onError: (DatabaseError) -> Unit) {
        val database = getDatabaseReference()
        val user = AuthManager.getUserId()
        val uid = database.child("users").child(user)
        Log.d("username", uid.key.toString())
        uid.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.exists()) {
                    onSuccess.invoke(null)
                } else {
                    onSuccess.invoke(
                        snapshot.child("firstName").value.toString() + " " + snapshot.child(
                            "lastName"
                        ).value.toString()
                    )
                }
            }

            override fun onCancelled(error: DatabaseError) {
                onError.invoke(error)
            }
        })
    }

    fun getReviewsUserFullName(userId:String, onSuccess: (String?) -> Unit, onError: (DatabaseError) -> Unit){
        val database = getDatabaseReference().child("users")
        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(childSnapshot in snapshot.children){
                    if(childSnapshot.key.toString()==userId){
                        onSuccess.invoke(
                        childSnapshot.child("firstName").value.toString() + " " + childSnapshot.child("lastName").value.toString()
                        )
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                onError.invoke(error)
            }

        }

        )
    }

    fun setReviews(
        feedback: String,
        placesId: String,
        userRatings: Double,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        val database = getDatabaseReference().child("ratings")

        val formattor = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US)
        val now = Date()
        val timeStamp = formattor.format(now)
        val uid = Firebase.auth.currentUser!!.uid + timeStamp
        val rating =
            Rating(feedback, placesId, userRatings, Firebase.auth.currentUser!!.uid)
        database.child(uid).setValue(rating).addOnCompleteListener {
            onSuccess.invoke()
        }.addOnFailureListener { err ->
            onError.invoke(err)
        }
    }

    fun setUserDetails(
        firstName: String,
        lastName: String,
        emailId: String,
        address: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        val database = getDatabaseReference().child("users")
        val id = AuthManager.getUserId()
        val user = User(firstName, lastName, emailId, address)
        database.child(id).setValue(user).addOnCompleteListener {
            onSuccess.invoke()
        }.addOnFailureListener { err ->
            onError.invoke(err)
        }
    }

    fun uploadImageToFirebase(
        context: Context,
        placeId: String,
        imageUri: Uri,
        caption: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        val fileRef = FirebaseStorage.getInstance().reference.child(placeId).child(
            AuthManager.getUserId() + System.currentTimeMillis() + "." + Utils.getFileExtension(
                imageUri,
                context
            )
        )
        fileRef.putFile(imageUri).addOnSuccessListener {

            fileRef.downloadUrl.addOnSuccessListener {
                val database = getDatabaseReference().child("images")
                val formatDate = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US)
                val timeNow = formatDate.format(Date())

                val id = AuthManager.getUserId() + timeNow
                val image = Image(it.toString(), caption, placeId)
                database.child(id).setValue(image).addOnSuccessListener {
                    onSuccess.invoke()
                }.addOnFailureListener { err ->
                    onError.invoke(err)
                }
            }
        }.addOnFailureListener {
            onError.invoke(it)
        }

    }

    fun setTripData(
        tripName: String,
        title: String,
        value: Boolean,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ){
        val db = getDatabaseReference().child("plan/"+AuthManager.getUserId()+"/"+tripName+"/")

        db.child(title).setValue(value).addOnCompleteListener {
            onSuccess.invoke()
        }.addOnFailureListener {
            onError.invoke(it)
        }

    }


    fun addTripInPlan(
        trip:String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ){
        getDatabaseReference().child("plan").child(AuthManager.getUserId()).child(trip).child("Cost").setValue(0).addOnCompleteListener {
            onSuccess.invoke()
        }.addOnFailureListener {
            onError.invoke(it)
        }
    }

    fun checkTripList(
        tripName: String,
        onSuccess: (Boolean) -> Unit,
        onError: (DatabaseError) -> Unit
    ){
        val db = getDatabaseReference().child("plan").child(AuthManager.getUserId())
        db.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(childSnapShot in snapshot.children){
                    if(tripName == childSnapShot.key.toString()){
                        onSuccess.invoke(true)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                onError.invoke(error)
            }

        })
    }

    fun addPlacesInList(
        tripName: String,
        onSuccess: (String) -> Unit,
        onError: (DatabaseError) -> Unit
    ){
        val db = getDatabaseReference().child("plan/"+AuthManager.getUserId()+"/"+tripName)
        db.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var temp=""
                for (childSnapShot in snapshot.children){
                    if(childSnapShot.key.toString()!="Cost"&&childSnapShot.value==true){
                        if(temp=="")
                        {
                            temp = childSnapShot.key.toString()
                        }
                        else
                        {
                            temp += ", "+childSnapShot.key.toString()
                        }
                    }
                }
                onSuccess.invoke(temp)
            }

            override fun onCancelled(error: DatabaseError) {
                onError.invoke(error)
            }

        })
    }

    fun checkPlaces(trip: String, title: String, onSuccess: (Boolean) -> Unit, onError: (Exception) -> Unit) {
        val db = getDatabaseReference().child("plan").child(AuthManager.getUserId()).child(trip)

        db.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.child(title).value==true)
                {
                    onSuccess.invoke(true)
                }
                else
                {
                    onSuccess.invoke(false)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                onError.invoke(error.toException())
            }

        })
    }

}