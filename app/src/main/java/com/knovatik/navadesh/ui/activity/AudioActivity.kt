package com.knovatik.navadesh.ui.activity

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.knovatik.navadesh.R
import com.knovatik.navadesh.databinding.ActivityMediaBinding
import com.knovatik.navadesh.model.media.Media
import com.knovatik.navadesh.ui.vm.AudioViewModel
import kotlinx.android.synthetic.main.gallery_layout.view.*

/** The request code for requesting [Manifest.permission.READ_EXTERNAL_STORAGE] permission. */
private const val READ_EXTERNAL_STORAGE_REQUEST = 0x1045

/**
 * Code used with [IntentSender] to request user permission to delete an image with scoped storage.
 */
private const val DELETE_PERMISSION_REQUEST = 0x1033

/**
 * MainActivity for the sample that displays a gallery of images in a [RecyclerView] using
 * a [GridLayoutManager].
 */
class AudioActivity : AppCompatActivity() {

    private val viewModel: AudioViewModel by viewModels()
    private lateinit var binding: ActivityMediaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val galleryAdapter = GalleryAdapter { image ->
            // deleteImage(image)
            setResult(GeneratePostActivity.AUDIO, Intent().putExtra("AUDIO", image.contentUri.toString()))
            finish()
        }

        binding.gallery.also { view ->
            view.layoutManager = GridLayoutManager(this, 3)
            view.adapter = galleryAdapter
        }

        viewModel.images.observe(this, Observer<List<Media>> { images ->
            galleryAdapter.submitList(images)
        })

        viewModel.permissionNeededForDelete.observe(this, Observer { intentSender ->
            intentSender?.let {
                // On Android 10+, if the app doesn't have permission to modify
                // or delete an item, it returns an `IntentSender` that we can
                // use here to prompt the user to grant permission to delete (or modify)
                // the image.
                startIntentSenderForResult(
                    intentSender,
                    DELETE_PERMISSION_REQUEST,
                    null,
                    0,
                    0,
                    0,
                    null
                )
            }
        })

        binding.openAlbum.setOnClickListener { openMediaStore() }
        binding.grantPermissionButton.setOnClickListener { openMediaStore() }

        if (!haveStoragePermission()) {
            binding.welcomeView.visibility = View.VISIBLE
        } else {
            showImages()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            READ_EXTERNAL_STORAGE_REQUEST -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showImages()
                } else {
                    // If we weren't granted the permission, check to see if we should show
                    // rationale for the permission.
                    val showRationale =
                        ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )

                    /**
                     * If we should show the rationale for requesting storage permission, then
                     * we'll show [ActivityMainBinding.permissionRationaleView] which does this.
                     *
                     * If `showRationale` is false, this means the user has not only denied
                     * the permission, but they've clicked "Don't ask again". In this case
                     * we send the user to the settings page for the app so they can grant
                     * the permission (Yay!) or uninstall the app.
                     */
                    if (showRationale) {
                        showNoAccess()
                    } else {
                        goToSettings()
                    }
                }
                return
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == DELETE_PERMISSION_REQUEST) {
            viewModel.deletePendingImage()
        }
    }

    private fun showImages() {
        viewModel.loadImages()
        binding.welcomeView.visibility = View.GONE
        binding.permissionRationaleView.visibility = View.GONE
    }

    private fun showNoAccess() {
        binding.welcomeView.visibility = View.GONE
        binding.permissionRationaleView.visibility = View.VISIBLE
    }

    private fun openMediaStore() {
        if (haveStoragePermission()) {
            showImages()
        } else {
            requestPermission()
        }
    }

    private fun goToSettings() {
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:$packageName")
        ).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }.also { intent ->
            startActivity(intent)
        }
    }

    /**
     * Convenience method to check if [Manifest.permission.READ_EXTERNAL_STORAGE] permission
     * has been granted to the app.
     */
    private fun haveStoragePermission() =
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

    /**
     * Convenience method to request [Manifest.permission.READ_EXTERNAL_STORAGE] permission.
     */
    private fun requestPermission() {
        if (!haveStoragePermission()) {
            val permissions = arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            ActivityCompat.requestPermissions(this, permissions, READ_EXTERNAL_STORAGE_REQUEST)
        }
    }

    private fun deleteImage(image: Media) {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.delete_dialog_title)
            .setMessage(getString(R.string.delete_dialog_message, image.displayName))
            .setPositiveButton(R.string.delete_dialog_positive) { _: DialogInterface, _: Int ->
                viewModel.deleteImage(image)
            }
            .setNegativeButton(R.string.delete_dialog_negative) { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
            }
            .show()
    }

    /**
     * A [ListAdapter] for [Media]s.
     */
    private inner class GalleryAdapter(val onClick: (Media) -> Unit) :
        ListAdapter<Media, GalleryViewHolder>(Media.DiffCallback) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.gallery_layout, parent, false)
            return GalleryViewHolder(view, onClick)
        }

        override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
            val mediaStoreImage = getItem(position)
            holder.rootView.tag = mediaStoreImage

            holder.itemView.musicName.text = mediaStoreImage.displayName

            holder.itemView.image.alpha = 0.1f

            Glide.with(holder.imageView)
                .load(R.drawable.ic_musical_note)
                .thumbnail(0.33f)
                .centerCrop()
                .into(holder.imageView)
        }
    }
}