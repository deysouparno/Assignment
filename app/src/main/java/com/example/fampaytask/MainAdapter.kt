package com.example.fampaytask

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.text.Html
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.core.content.getSystemService
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.marginEnd
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.fampaytask.databinding.*
import com.example.fampaytask.model.Card

class MainAdapter(private val type: String, private val listener: ClickListener) :
    ListAdapter<Card, RecyclerView.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (type) {
            "HC1" -> {
                Hc1ViewHolder(
                    Hc1CardBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            "HC3" -> {
                Hc3ViewHolder(
                    Hc3CardBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            "HC5" -> {
                Hc5ViewHolder(
                    Hc5CardBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            "HC6" -> {
                Hc6ViewHolder(
                    Hc6CardBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                Hc9ViewHolder(
                    Hc9CardBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (type) {
            "HC1" -> {
                (holder as Hc1ViewHolder).bind(getItem(holder.adapterPosition), listener)
            }
            "HC3" -> {
                (holder as Hc3ViewHolder).bind(getItem(holder.adapterPosition), listener)
            }
            "HC5" -> {
                (holder as Hc5ViewHolder).bind(getItem(holder.adapterPosition), listener)
            }
            "HC6" -> {
                (holder as Hc6ViewHolder).bind(getItem(holder.adapterPosition), listener)
            }
            else -> {
                (holder as Hc9ViewHolder).bind(getItem(holder.adapterPosition), listener)
            }
        }
    }
}

private val diffCallback = object : DiffUtil.ItemCallback<Card>() {
    override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

}

class Hc1ViewHolder(private val binding: Hc1CardBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(card: Card, listener: ClickListener) {
        if (card.icon != null)
            Glide.with(binding.root.context).load(card.icon.imageURL)
                .circleCrop()
                .into(
                    binding.icon
                )

        if (card.formattedTitle != null) {
            var title = card.formattedTitle.text
            card.formattedTitle.entities.forEach { entity ->
                title.replaceFirst("{}", getColoredSpanned(entity.text, entity.color))
            }
            binding.formattedTitle.text = title
        }

        if (card.formattedDescription != null) {
            var description = card.formattedDescription.text
            card.formattedDescription.entities.forEach { entity ->
                description.replaceFirst("{}", getColoredSpanned(entity.text, entity.color))
            }
            binding.formattedDescription.text = description
            if (description == "")
                binding.formattedDescription.visibility = View.GONE
        }

        if (card.bgColor != null) {
            val drawable = GradientDrawable()
            drawable.apply {
                cornerRadius = 25F
                shape = GradientDrawable.RECTANGLE
                setColor(Color.parseColor(card.bgColor))
            }
            binding.root.background = drawable
        }

        binding.root.setOnClickListener {
            listener.onPress(card.url)
        }

    }
}


class Hc3ViewHolder(private val binding: Hc3CardBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(card: Card, listener: ClickListener) {
        var height = 0
        if (card.bgImage != null) {
            Glide.with(binding.backgroundImage.context).load(card.bgImage.imageURL).into(
                binding.backgroundImage
            )
            val windowManager: WindowManager = (binding.root.context).getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            val width = displayMetrics.widthPixels - 40
            height = (width  / card.bgImage.aspectRatio!!).toInt()
            val layoutParams = ViewGroup.MarginLayoutParams(width , (width  / card.bgImage.aspectRatio!!).toInt())
            layoutParams.setMargins(20, 10, 20, 10)
            binding.root.layoutParams = layoutParams

        }

        if (card.formattedTitle != null) {
            var title = String(card.formattedTitle.text.toCharArray())
            card.formattedTitle.entities.forEach { entity ->
                title = title.replaceFirst("{}", getColoredSpanned(entity.text, entity.color))
            }
            binding.formattedTitle.text = title
        }

        if (card.formattedDescription != null) {
            var description = card.formattedDescription.text
            card.formattedDescription.entities.forEach { entity ->
                description = description.replaceFirst("{}", getColoredSpanned(entity.text, entity.color))
            }
            binding.formattedDescription.text = description
        }

        if (card.cta != null) {
            val cta = card.cta[0]
            val drawable = GradientDrawable()
            drawable.apply {
                cornerRadius = 25F
                shape = GradientDrawable.RECTANGLE
                setColor(Color.parseColor(cta.bgColor))
            }
            binding.actionButton.background = drawable
            binding.actionButton.text = getColoredSpanned(cta.text, cta.textColor)
            binding.actionButton.setOnClickListener {
                listener.onPress(cta.url)
            }
        }



        binding.root.setOnLongClickListener {
            binding.container.animate().apply {
                duration = 100
                translationX(500F)
            }
            binding.contextAction.visibility = View.VISIBLE
            binding.contextAction.animate().apply {
                duration = 100
                scaleX(1F)
            }
            binding.contextAction.layoutParams = FrameLayout.LayoutParams(500, height)

            true
        }


        binding.closeButton.setOnClickListener {
            binding.container.animate().apply {
                duration = 100
                translationX(0F)
            }
            binding.contextAction.visibility = View.GONE
            binding.contextAction.animate().apply {
                duration = 100
                scaleX(0F)
            }

        }

        binding.root.setOnClickListener {
            listener.onPress(card.url)
        }
    }
}

class Hc5ViewHolder(private val binding: Hc5CardBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(card: Card, listener: ClickListener) {
        if (card.bgImage != null) {
            Glide.with(binding.backgroundImage.context).load(card.bgImage.imageURL).into(
                binding.backgroundImage
            )
            val windowManager: WindowManager = (binding.root.context).getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            val width = displayMetrics.widthPixels - 40
            val layoutParams = ViewGroup.MarginLayoutParams(width, (width / card.bgImage.aspectRatio!!).toInt())
            layoutParams.setMargins(20, 5, 20, 5)
            binding.root.layoutParams = layoutParams
        }

        if (card.bgColor != null) {
            val drawable = GradientDrawable()
            drawable.apply {
                cornerRadius = 20F
                shape = GradientDrawable.RECTANGLE
                setColor(Color.parseColor(card.bgColor))
            }
            binding.root.background = drawable
        }


        binding.root.setOnClickListener {
            listener.onPress(card.url)
        }
    }
}

class Hc6ViewHolder(private val binding: Hc6CardBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(card: Card, listener: ClickListener) {
        if (card.icon != null)
            Glide.with(binding.root.context).load(card.icon.imageURL).into(
                binding.icon
            )

        if (card.formattedTitle != null) {
            var title = card.formattedTitle.text
            card.formattedTitle.entities.forEach { entity ->
                title = title.replaceFirst("{}", getColoredSpanned(entity.text, entity.color))
            }
            binding.formattedTitle.text = title
        }

        if (card.formattedDescription != null) {
            var description = card.formattedDescription.text
            card.formattedDescription.entities.forEach { entity ->
                description = description.replaceFirst("{}", getColoredSpanned(entity.text, entity.color))
            }
            binding.formattedDescription.text = description
        }

        binding.root.setOnClickListener {
            listener.onPress(card.url)
        }
    }
}

class Hc9ViewHolder(private val binding: Hc9CardBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(card: Card, listener: ClickListener) {
        if (card.bgImage != null) {
            Glide.with(binding.backgroundImage.context).load(card.bgImage.imageURL).into(
                binding.backgroundImage
            )

            val layoutParams = ViewGroup.MarginLayoutParams((800 / card.bgImage.aspectRatio!!).toInt() , 800)
            layoutParams.setMargins(20, 20, 20, 10)
            binding.root.layoutParams = layoutParams
        }


        if (card.bgColor != null)
            binding.root.setBackgroundColor(Color.parseColor(card.bgColor))

        binding.root.setOnClickListener {
            listener.onPress(card.url)
        }
    }
}

private fun getColoredSpanned(text: String, color: String): String {
    return Html.fromHtml("<font color=$color>$text</font>").toString()
}