package com.example.trempelapp.presentation_layer.main_screen.main_fragments.profile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
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
import androidx.activity.result.ActivityResultLauncher
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.domain_layer.models.MainUserInfo
import com.example.trempelapp.BaseFragment
import com.example.trempelapp.R
import com.example.trempelapp.TrempelApplication
import com.example.trempelapp.presentation_layer.logIn_screen.TrempelLogInActivity
import com.example.trempelapp.utils.DEFAULT_LATITUDE
import com.example.trempelapp.utils.DEFAULT_LONGITUDE
import com.example.trempelapp.utils.EMPTY_STRING
import com.example.trempelapp.utils.GALLERY_PATH
import com.example.trempelapp.utils.PERMISSION_IS_NOT_GRANTED
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState

const val fineLocationPermission = Manifest.permission.ACCESS_FINE_LOCATION
const val coarseLocationPermission = Manifest.permission.ACCESS_COARSE_LOCATION

class ProfilePageFragment : BaseFragment() {

    lateinit var perLauncher: ActivityResultLauncher<Array<String>>

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.initFusedLocationProviderManager(requireActivity())

        perLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { isGranted ->

            if (isGranted[fineLocationPermission] == true) {
                getLocation(perLauncher)
            } else {
                Toast.makeText(context, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        viewModel.getUserInfo()
        setUpObservers()

        viewModel.getGpsStatus()

        return buildUI()
    }

    private fun setUpObservers() {
        viewModel.onLogOutButtonClicked.observe(viewLifecycleOwner) {
            val intent = Intent(context, TrempelLogInActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        viewModel.gpsStatusLiveData.observe(viewLifecycleOwner) {
            if (!it) {
                Toast.makeText(context, getString(R.string.no_gps), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getLocation(perLauncher: ActivityResultLauncher<Array<String>>) {

        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            perLauncher.launch(arrayOf(fineLocationPermission, coarseLocationPermission))
        } else {
            viewModel.getUserLocation()
        }
    }

    private fun buildUI(): ComposeView {
        return ComposeView(requireContext()).apply {
            setContent {
                val profileState = viewModel.profileState.observeAsState()
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
                            profileState.value?.let { SetUserImage(profileState.value?.user, it.isLoading) }
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxHeight(0.7f)
                                .fillMaxWidth()
                                .fillMaxWidth()
                                .padding(start = 40.dp),
                            verticalArrangement = Arrangement.SpaceEvenly,
                        ) {
                            TextField(
                                fieldName = getString(R.string.name),
                                fieldValue = profileState.value?.user?.name ?: EMPTY_STRING
                            )
                            TextField(
                                fieldName = getString(R.string.username),
                                fieldValue = profileState.value?.user?.username ?: EMPTY_STRING
                            )
                            TextField(
                                fieldName = getString(R.string.phone),
                                fieldValue = profileState.value?.user?.phone ?: EMPTY_STRING
                            )
                            TextField(
                                fieldName = getString(R.string.address),
                                fieldValue = profileState.value?.user?.address ?: EMPTY_STRING
                            )
                        }
                        CheckPermissionStatus(
                            perLauncher,
                            profileState.value?.location?.latitude,
                            profileState.value?.location?.longitude
                        )
                    }
                    profileState.value?.let { CircularIndeterminateProgressBar(isDisplayed = it.isLoading) }
                }
            }
        }
    }

    @Composable
    private fun CheckPermissionStatus(
        perLauncher: ActivityResultLauncher<Array<String>>,
        latitude: Double?,
        longitude: Double?
    ) {
        val finePermissionStatus =
            ContextCompat.checkSelfPermission(requireContext(), fineLocationPermission)
        if (viewModel.gpsStatusLiveData.value == true) {
            if (finePermissionStatus == PERMISSION_IS_NOT_GRANTED) {
                SetMapWithoutPermission(getString(R.string.resolve_location_permission))
                perLauncher.launch(arrayOf(fineLocationPermission, coarseLocationPermission))
            } else {
                getLocation(perLauncher)
                SetMapWithPermission(latitude, longitude)
            }
        } else {
            SetMapWithoutPermission(getString(R.string.enable_gps))
        }
    }

    @Composable
    private fun SetMapWithoutPermission(error: String) {
        Box(
            modifier = Modifier
                .padding(start = 40.dp, end = 40.dp)
                .clip(RoundedCornerShape(10)),
        ) {

            val location = LatLng(DEFAULT_LATITUDE, DEFAULT_LONGITUDE)
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(location, 11f)
            }
            GoogleMap(
                cameraPositionState = cameraPositionState,
                modifier = Modifier
                    .blur(5.dp)
            ) {
                Marker(
                    position = location,
                    snippet = getString(R.string.you_are_here)
                )
            }
            Column(
                modifier = Modifier
                    .clickable {
                        Toast
                            .makeText(
                                context,
                                getString(R.string.asking_for_permission),
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = error
                )
            }
        }
    }

    @Composable
    private fun SetMapWithPermission(latitude: Double?, longitude: Double?) {
        Box(
            modifier = Modifier
                .padding(start = 40.dp, end = 40.dp)
                .clip(RoundedCornerShape(10)),
        ) {

            val location = remember {
                mutableStateOf<LatLng?>(null)
            }

            location.value = if (latitude != null && longitude != null) {
                LatLng(latitude, longitude)
            } else {
                LatLng(DEFAULT_LATITUDE, DEFAULT_LONGITUDE)
            }
            location.value?.let {
                GoogleMap(
                    cameraPositionState = CameraPositionState(CameraPosition(it, 10f, 0f, 0f))
                ) {
                    Marker(
                        position = it,
                        snippet = getString(R.string.you_are_here)
                    )
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
        val imageUri = remember {
            mutableStateOf<Uri?>(null)
        }
        val bitmap = remember {
            mutableStateOf<Bitmap?>(null)
        }
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            imageUri.value = uri
        }
        SetImageByChoice(imageUri = imageUri, bitmap = bitmap, launcher = launcher)
        if (data?.imageUri != null) {

            bitmap.value = BitmapFactory.decodeFile(data.imageUri.toString())

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
        } else {
            SetDefaultImage(launcher, isEnabled)
        }
    }

    @Composable
    private fun SetImageByChoice(
        imageUri: MutableState<Uri?>,
        bitmap: MutableState<Bitmap?>,
        launcher: ManagedActivityResultLauncher<String, Uri?>
    ) {
        val context = LocalContext.current

        imageUri.value?.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images
                    .Media.getBitmap(context.contentResolver, it)
            } else {
                val source = ImageDecoder
                    .createSource(context.contentResolver, it)
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
    private fun SetDefaultImage(
        launcher: ManagedActivityResultLauncher<String, Uri?>,
        isEnabled: Boolean
    ) {
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
