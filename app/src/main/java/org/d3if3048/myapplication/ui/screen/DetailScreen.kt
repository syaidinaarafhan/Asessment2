package org.d3if3048.myapplication.ui.screen

import android.content.res.Configuration
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import org.d3if3048.myapplication.R
import org.d3if3048.myapplication.database.dbTahanan
import org.d3if3048.myapplication.ui.theme.Asessment2Theme
import org.d3if3048.myapplication.util.ViewModelFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

const val KEY_ID_MAHASISWA = "idMahasiswa"

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, id: Long? = null){
    val context = LocalContext.current
    val db = dbTahanan.getInstance(context)
    val factory = ViewModelFactory.ViewModelFactory(db.dao)
    val viewModel: DetailViewModel = viewModel(factory = factory)
    var showDialog by remember {
        mutableStateOf(false)
    }

    var nama by remember {
        mutableStateOf("")
    }
    var umur by remember {
        mutableStateOf("")
    }
    var tanggal_keluar by remember {
        mutableStateOf("")
    }
    var ruangan by remember {
        mutableStateOf("")
    }
    var deskripsi by remember {
        mutableStateOf("")
    }

    LaunchedEffect(true){
        if (id == null) return@LaunchedEffect
        val data = viewModel.getTahanan(id) ?: return@LaunchedEffect
        nama = data.nama
        umur = data.umur
        tanggal_keluar = data.tanggal_keluar
        ruangan = data.ruangan
        deskripsi = data.deskripsi
    }


    Scaffold (
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    if (id == null)
                        Text(text = stringResource(id = R.string.tambah_catatan))
                    else
                        Text(text = stringResource(id = R.string.edit_catatan))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = {
                        if (nama == "" || umur == "" || tanggal_keluar == "" || ruangan == "" || deskripsi == ""){
                            Toast.makeText(context, R.string.invalid, Toast.LENGTH_LONG).show()
                            return@IconButton
                        }
                        if (id == null){
                            viewModel.insert(nama, umur, tanggal_keluar, ruangan, deskripsi)
                        } else {
                            viewModel.update(id,nama, umur, tanggal_keluar, ruangan, deskripsi)
                        }
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = stringResource(id = R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    if (id != null){
                        DeleteAction {
                            showDialog = true
                        }
                        DisplayAlertDialog(
                            openDialog = showDialog,
                            onDismissRequest = { showDialog = false }
                        ) {
                            showDialog = false
                            viewModel.delete(id)
                            navController.popBackStack()
                        }
                    }
                }
            )
        }
    ){ padding ->
        FormKriminal(
            name = nama,
            onNameChange = {nama = it},
            umur = umur,
            onNimChange = {umur = it},
            modifier = Modifier.padding(padding),
            ruangan = ruangan,
            onClassChange = {ruangan = it},
            tanggal_keluar = tanggal_keluar,
            onDateChange = {tanggal_keluar = it},
            deskripsi = deskripsi,
            onDescChange = {deskripsi = it}
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FormKriminal(
    name: String, onNameChange: (String) -> Unit,
    umur: String, onNimChange: (String) -> Unit,
    tanggal_keluar: String, onDateChange: (String) -> Unit,
    ruangan: String, onClassChange: (String) -> Unit,
    deskripsi: String, onDescChange: (String) -> Unit,
    modifier: Modifier
) {
    val daftarRuangan = listOf("Ruang-1", "Ruang-2", "Ruang-3", "Ruang-4", "Ruang-5", "Ruang-6")
    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("MMM dd yyyy").format(pickedDate)
        }
    }
    val dateDialogState = rememberMaterialDialogState()

    Column (
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = {onNameChange(it) },
            label = { Text(text = stringResource(id = R.string.nama))},
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
            OutlinedTextField(
                value = umur,
                onValueChange = {onNimChange(it) },
                label = { Text(text = stringResource(id = R.string.umur))},
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Button(onClick = {
                dateDialogState.show()
            }) {
                Text(text = "Pick date")
            }
            Text(text = formattedDate)
            MaterialDialog(
                dialogState = dateDialogState,
                buttons = {
                    positiveButton(text = "Ok") {
                        val formattedString = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(pickedDate)
                        onDateChange(formattedString)
                    }
                    negativeButton(text = "Cancel")
                }
            ) {
                datepicker(
                    initialDate = LocalDate.now(),
                    title = "Pick a date",
                ) {
                    pickedDate = it
                }
            }

        Column (
            modifier = Modifier
                .padding(vertical = 8.dp)
                .border(1.dp, Color.Gray)
                .fillMaxWidth()
        ) {
            daftarRuangan.forEach { namaKelas ->
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    RadioButton(
                        selected = (ruangan == namaKelas),
                        onClick = { onClassChange(namaKelas) },
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                    )
                    Text(
                        text = namaKelas,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
        OutlinedTextField(
            value = deskripsi,
            onValueChange = {onDescChange(it) },
            label = { Text(text = stringResource(id = R.string.deskripsi_kriminal))},
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences
            ),
            modifier = Modifier.fillMaxSize()
        )
    }
}




@Composable
fun DeleteAction(detele: () -> Unit){
    var expanded by remember {
        mutableStateOf(false)
    }
    IconButton(
        onClick = { expanded = true }
    ) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(id = R.string.opsi_lainnya),
            tint = MaterialTheme.colorScheme.primary
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text(text = stringResource(R.string.hapus)) },
                onClick = {
                    expanded = false
                    detele()
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DetailScreenPreview() {
    Asessment2Theme {
        DetailScreen(rememberNavController())
    }
}