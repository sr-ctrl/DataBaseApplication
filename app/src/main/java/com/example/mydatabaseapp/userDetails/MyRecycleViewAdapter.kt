package com.example.mydatabaseapp.userDetails

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.example.mydatabaseapp.R
import com.example.mydatabaseapp.database.RegisterEntity
import com.example.mydatabaseapp.database.RegisterRepository
import com.example.mydatabaseapp.databinding.ListItemBinding
import com.example.mydatabaseapp.databinding.RegisterHomeFragmentBinding
import kotlinx.android.synthetic.main.list_item.view.*

class MyRecycleViewAdapter(private val usersList :List<RegisterEntity>):RecyclerView.Adapter<MyviewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item,parent,false)
        return MyviewHolder(binding)
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
        holder.bind(usersList[position])
        holder.itemView.img_collapse.setOnClickListener {
            Log.d("TAG", "onBindViewHolder: collpse clicked ")
            if (holder.itemView.main_container.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(
                    holder.itemView.card_view,
                    AutoTransition()
                )
                holder.itemView.main_container.visibility = View.VISIBLE
                holder.itemView.img_collapse.setImageResource(R.drawable.open_shutter_icon)
            } else {
                TransitionManager.beginDelayedTransition(
                    holder.itemView.card_view,
                    AutoTransition()
                )
                holder.itemView.main_container.visibility = View.GONE
                holder.itemView.img_collapse.setImageResource(R.drawable.close_shutter_icon)
            }
        }
    }


}

class MyviewHolder(private val binding :ListItemBinding ):RecyclerView.ViewHolder(binding.root){

    fun bind(user : RegisterEntity){
        binding.txtFirstName.text = user.firstName
        binding.txtLastName.text = user.lastName
        binding.txtEmailId.text = user.email_id
        binding.txtUsername.text = user.userName
        binding.txtPhoneNumber.text = user.phone_number
        binding.txtDob.text = "${user.birthday}/${user.birthMonth}/${user.birthYear}"
        binding.txtGender.text = user.gender
//        binding.userTextField.text = user.userName
    }

}