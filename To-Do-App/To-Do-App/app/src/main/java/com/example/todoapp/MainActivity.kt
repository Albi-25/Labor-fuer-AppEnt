package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.ui.theme.ToDoAppTheme

// Data class for a to-do item
data class TodoItem(
    val title: String,
    var isCompleted: Boolean
)

// MainActivity that sets the content view
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoAppTheme {
                AppScaffold()
            }
        }
    }
}

// ViewModel for managing to-do items
class TodoViewModel : ViewModel() {

    // LiveData for the to-do list
    private val _todos = MutableLiveData<List<TodoItem>>(emptyList())
    val todos: LiveData<List<TodoItem>> get() = _todos

    // Function to add a new to-do item
    fun addTodo(title: String) {
        val updatedList = _todos.value.orEmpty() + TodoItem(title = title, isCompleted = false)
        _todos.value = updatedList
    }

    // Function to delete a to-do item
    fun deleteTodo(todoItem: TodoItem) {
        val updatedList = _todos.value.orEmpty().filter { it != todoItem }
        _todos.value = updatedList
    }

    // Function to toggle the completion status of a to-do item
    fun toggleCompletion(todoItem: TodoItem) {
        val updatedList = _todos.value.orEmpty().map {
            if (it == todoItem) it.copy(isCompleted = !it.isCompleted) else it
        }
        _todos.value = updatedList
    }
}

// Composable scaffold for the app
@Composable
fun AppScaffold(viewModel: TodoViewModel = viewModel()) {
    // Observing LiveData as state in Compose
    val todoItems by viewModel.todos.observeAsState(emptyList())

    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true }
            ) {
                Text(text = "+") // Floating Action Button label
            }
        },
        content = { paddingValues ->
            TodoListScreen(
                modifier = Modifier.padding(paddingValues),
                todoItems = todoItems,
                onToggleCompletion = { viewModel.toggleCompletion(it) },
                onDelete = { viewModel.deleteTodo(it) }
            )
        }
    )

    // Dialog for adding a new to-do item
    if (showDialog) {
        AddTodoDialog(
            onAdd = { newItem ->
                viewModel.addTodo(newItem)
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }
}

// Composable to display the list of to-do items
@Composable
fun TodoListScreen(
    modifier: Modifier,
    todoItems: List<TodoItem>,
    onToggleCompletion: (TodoItem) -> Unit,
    onDelete: (TodoItem) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Title of the app
        Text(
            text = "Todo App",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // LazyColumn for dynamic list rendering
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(todoItems) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Row {
                        Checkbox(
                            checked = item.isCompleted,
                            onCheckedChange = { onToggleCompletion(item) }
                        )
                        IconButton(onClick = { onDelete(item) }) {
                            Icon(
                                imageVector = Icons.Default.Delete, // Trash icon
                                contentDescription = "Delete",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    }
}

// Composable dialog for adding a new to-do item
@Composable
fun AddTodoDialog(onAdd: (String) -> Unit, onDismiss: () -> Unit) {
    var textState by remember { mutableStateOf(TextFieldValue("")) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Add To-Do Item") },
        text = {
            TextField(
                value = textState,
                onValueChange = { textState = it },
                placeholder = { Text(text = "Enter your task") }
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onAdd(textState.text)
                }
            ) {
                Text(text = "Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        }
    )
}

// Preview for the To-Do app
@Preview(showBackground = true, name = "Todo App Preview")
@Composable
fun PreviewTodoApp() {
    ToDoAppTheme {
        AppScaffold()
    }
}
