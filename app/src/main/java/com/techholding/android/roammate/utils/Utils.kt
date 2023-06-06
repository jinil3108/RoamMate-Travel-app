package com.techholding.android.roammate.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.techholding.android.roammate.R
import com.techholding.android.roammate.data.TripAdapter
import com.techholding.android.roammate.ui.fragment.plan.PlanViewModel
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

object Utils {

    const val RAPIDAPI_KEY = "b6b6f2593cmshf7f1d13e3b10ac9p1e5b88jsn5105004f7f80"
    const val RAPIDAPI_TRUEWAY_PLACES_HOST = "trueway-places.p.rapidapi.com"
    fun isValidEmail(email: String): Boolean {
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false
        }
        return true
    }

    fun isValidPassword(password: String): Boolean {
        if (password.isEmpty() || !Pattern.compile(
                "^(?=.*[!@#\$%^&*(),.?\":{}|<>])(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}\$"
            ).matcher(password).matches()
        ) {
            return false
        }
        return true
    }

    fun getFileExtension(imageUri: Uri, context: Context): String? {
        val contentResolver = context.contentResolver
        val stringMemeType = contentResolver?.getType(imageUri)
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(stringMemeType)
    }

    fun timeStamp(): String {
        val timeStamp = Timestamp(System.currentTimeMillis())
        val sdf = SimpleDateFormat("HH:mm")
        val time = sdf.format(Date(timeStamp.time))

        return time.toString()
    }

    fun onSwipeDelete(adapter: TripAdapter,planViewModel: PlanViewModel, context: Context,tripRecyclerView: RecyclerView) {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                val itemHeight = itemView.bottom - itemView.top

                val paint = Paint()
                paint.color = Color.parseColor("#FF8110")
                val bg = RectF(
                    itemView.right.toFloat() + dX,
                    itemView.top.toFloat(),
                    itemView.right.toFloat(),
                    itemView.bottom.toFloat()
                )

                c.drawRect(bg, paint)

                val deleteIcon =
                    context.let { ContextCompat.getDrawable(it, R.drawable.baseline_delete_24) }
                // Calculate position of delete icon
                val iconTop = itemView.top + (itemHeight - deleteIcon!!.intrinsicHeight) / 2
                val iconMargin = (itemHeight - deleteIcon.intrinsicHeight) / 2
                val iconLeft = itemView.right - iconMargin - deleteIcon.intrinsicWidth
                val iconRight = itemView.right - iconMargin
                val iconBottom = iconTop + deleteIcon.intrinsicHeight

                // Draw the delete icon
                deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                deleteIcon.draw(c)

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                val obj = adapter.getItemAt(pos)
                adapter.deleteItem(pos)
                planViewModel.deleteFromFirebase(obj.title)
            }
        }).attachToRecyclerView(tripRecyclerView)
    }
    }


