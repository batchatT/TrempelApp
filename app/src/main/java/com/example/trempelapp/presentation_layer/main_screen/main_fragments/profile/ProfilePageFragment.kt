package com.example.trempelapp.presentation_layer.main_screen.main_fragments.profile

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.domain_layer.models.MainUserInfo
import com.example.trempelapp.BaseFragment
import com.example.trempelapp.R
import com.example.trempelapp.TrempelApplication
import com.example.trempelapp.presentation_layer.logIn_screen.TrempelLogInActivity
import com.example.trempelapp.utils.EMPTY_STRING
import com.example.trempelapp.utils.GALLERY_PATH

class ProfilePageFragment : BaseFragment() {

    companion object {
        fun newInstance(): ProfilePageFragment {

            val args = Bundle()

            val profileLoginFragment = ProfilePageFragment()
            profileLoginFragment.arguments = args
            return profileLoginFragment
        }
    }

    val viewModel by lazy {
        ViewModelProvider(this, viewModelProviderFactory)[ProfilePageViewModel::class.java]
    }

    override fun injectDagger() {
        (requireActivity().application as TrempelApplication).trempelApp.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        viewModel.getUserInfo()
        setUpObservers()
        return buildUI()
    }

    private fun setUpObservers() {
        viewModel.onLogOutButtonClicked.observe(viewLifecycleOwner) {
            val intent = Intent(context, TrempelLogInActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        viewModel.onImageSaved.observe(viewLifecycleOwner) {
            Toast.makeText(context, getString(R.string.on_image_saved), Toast.LENGTH_SHORT).show()
        }
    }

    private fun buildUI(): ComposeView {
        return ComposeView(requireContext()).apply {
            setContent {
                val data by viewModel.userData.observeAsState()
                val isDisplayed = viewModel.loading.value

                Box(modifier = Modifier.fillMaxSize()) {
                    Column {
                        Column(
                            modifier = Modifier
                                .padding(top = 32.dp)
                                .fillMaxWidth()
                                .wrapContentSize()
                                .size(200.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            SetUserImage(data, isDisplayed)
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxHeight(0.7f)
                                .fillMaxWidth()
                                .padding(start = 40.dp),
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            TextField(
                                fieldName = getString(R.string.name),
                                fieldValue = data?.name ?: EMPTY_STRING
                            )
                            TextField(
                                fieldName = getString(R.string.username),
                                fieldValue = data?.username ?: EMPTY_STRING
                            )
                            TextField(
                                fieldName = getString(R.string.phone),
                                fieldValue = data?.phone ?: EMPTY_STRING
                            )
                            TextField(
                                fieldName = getString(R.string.address),
                                fieldValue = data?.address ?: EMPTY_STRING
                            )
                        }
                    }
                    CircularIndeterminateProgressBar(isDisplayed = isDisplayed)
                }
            }
        }
    }

    @Composable
    private fun CircularIndeterminateProgressBar(isDisplayed: Boolean) {
        if (isDisplayed) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularProgressIndicator(
                    color = Black
                )
            }
        }
    }

    @Composable
    private fun TextField(fieldName: String, fieldValue: String) {
        Column {
            Text(
                text = fieldName,
                color = Color.Gray,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.montserrat_light))
            )
            Text(
                text = fieldValue,
                color = Black,
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.montserrat_regular))
            )
        }
    }

    @Composable
    fun SetUserImage(data: MainUserInfo?, isEnabled: Boolean) {
        var imageUri by remember {
            mutableStateOf<Uri?>(null)
        }
        val bitmap = remember {
            mutableStateOf<Bitmap?>(null)
        }
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            imageUri = uri
        }

        SetImageByChoice(imageUri, bitmap, launcher)

        data?.imageUri?.let {
            bitmap.value = BitmapFactory.decodeFile(it.toString())

            bitmap.value?.let { btm ->
                Image(
                    bitmap = btm.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(400.dp)
                        .clip(CircleShape)
                        .clickable {
                            launcher.launch(GALLERY_PATH)
                        },
                    contentScale = ContentScale.Crop
                )
            }
        }
        SetDefaultImage(launcher, isEnabled)
    }

    @Composable
    private fun SetImageByChoice(
        imageUri: Uri?,
        bitmap: MutableState<Bitmap?>,
        launcher: ManagedActivityResultLauncher<String, Uri?>
    ) {
        val context = LocalContext.current

        imageUri?.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images
                    .Media.getBitmap(context.contentResolver, imageUri)
            } else {
                val source = ImageDecoder
                    .createSource(context.contentResolver, imageUri)
                bitmap.value = ImageDecoder.decodeBitmap(source)
            }

            bitmap.value?.let { btm ->
                Image(
                    bitmap = btm.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(400.dp)
                        .clip(CircleShape)
                        .clickable {
                            launcher.launch(GALLERY_PATH)
                        },
                    contentScale = ContentScale.Crop
                )
                viewModel.saveUserImage(btm)
            }
        }
    }

    @Composable
    private fun SetDefaultImage(launcher: ManagedActivityResultLauncher<String, Uri?>, isEnabled: Boolean) {
        Image(
            painter = painterResource(id = R.drawable.ic_splash_activity_image),
            contentDescription = null,
            modifier = Modifier.clickable(
                enabled = !isEnabled,
                onClick = {
                    launcher.launch(GALLERY_PATH)
                }
            )
        )
    }
}
