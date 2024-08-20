package com.example.todolist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Button
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.OutlinedTextField
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextFieldDefaults
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todolist.data.Task
import kotlinx.coroutines.launch

@Composable
fun AddEditDetailView(
    id: Long,
    viewModel: TaskViewModel,
    navController: NavController
){

    val snackMessage = remember{
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    if (id != 0L){
        val task = viewModel.getATaskById(id).collectAsState(initial = Task(0L, "", ""))
        viewModel.taskTitleState = task.value.title
        viewModel.taskDescriptionState = task.value.description
    }else{
        viewModel.taskTitleState = ""
        viewModel.taskDescriptionState = ""
    }

    Scaffold(
        topBar = {AppBarView(title =
        if(id != 0L) stringResource(id = R.string.update_task)
        else stringResource(id = R.string.add_task)
        ) {navController.navigateUp()}
        },
        scaffoldState = scaffoldState
    ) { it ->
        Column(modifier = Modifier
            .padding(it)
            .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Spacer(modifier = Modifier.height(10.dp))

            TaskTextField(label = "Title",
                value = viewModel.taskTitleState,
                onValueChanged = {
                    viewModel.onTaskTitleChanged(it)
                } )

            Spacer(modifier = Modifier.height(10.dp))

            TaskTextField(label = "Description",
                value = viewModel.taskDescriptionState,
                onValueChanged = {
                    viewModel.onTaskDescriptionChanged(it)
                } )

            Spacer(modifier = Modifier.height(10.dp))
            androidx.compose.material3.Button(
                onClick = {
                    if (viewModel.taskTitleState.isNotEmpty() &&
                        viewModel.taskDescriptionState.isNotEmpty()
                    ) {
                        if (id != 0L) {
                            viewModel.updateTask(
                                Task(
                                    id = id,
                                    title = viewModel.taskTitleState.trim(),
                                    description = viewModel.taskDescriptionState.trim(),
                                ),
                            )
                        } else {
                            //  AddTask
                            viewModel.addTask(
                                Task(
                                    title = viewModel.taskTitleState.trim(),
                                    description = viewModel.taskDescriptionState.trim(),
                                ),
                            )
                            snackMessage.value = "Task has been created"
                        }
                    } else {
                        //
                        snackMessage.value = "Enter fields to create a task"
                    }
                    scope.launch {
                        //scaffoldState.snackbarHostState.showSnackbar(snackMessage.value)
                        navController.navigateUp()
                    }

                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.app_bar_color)
                )
            ){
                androidx.compose.material3.Text(
                    text = if (id != 0L) stringResource(id = R.string.update_task)
                    else stringResource(
                        id = R.string.add_task
                    ),
                    style = TextStyle(
                        fontSize = 18.sp
                    )
                )
            }
        }
    }

}


@Composable
fun TaskTextField(
    label: String,
    value: String,
    onValueChanged: (String) -> Unit
){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = { androidx.compose.material3.Text(text = label, color = Color.Black) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            // using predefined Color
            textColor = Color.Black,
            // using our own colors in Res.Values.Color
            focusedBorderColor = colorResource(id = R.color.app_bar_color),
            unfocusedBorderColor = colorResource(id = R.color.bgColor),
            cursorColor = colorResource(id = R.color.black),
            focusedLabelColor = colorResource(id = R.color.app_bar_color),
            unfocusedLabelColor = colorResource(id = R.color.bgColor),
        )


    )
}

@Preview
@Composable
fun TaskTestFieldPrev(){
    TaskTextField(label = "text", value = "text", onValueChanged = {})
}
