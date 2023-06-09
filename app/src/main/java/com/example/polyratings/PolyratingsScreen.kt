package com.example.polyratings

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.List
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import coil.compose.rememberAsyncImagePainter
import com.example.polyratings.data.BottomNavItem
import com.example.polyratings.data.PolyratingViewModel
import com.example.polyratings.pages.*

enum class PolyratingScreen(@StringRes val title: Int) {
    Home(title = R.string.app_name),
    List(title = R.string.list),
    Add(title = R.string.add),
    FAQ(title = R.string.faq),

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PolyratingsApp(
    modifier: Modifier = Modifier,
    viewModel: PolyratingViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
){

    LaunchedEffect(Unit, block = {
        viewModel.getProfessorList()
        print("Calling LaunchedEffect !!!!")
    })

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = PolyratingScreen.valueOf(
        backStackEntry?.destination?.route ?: PolyratingScreen.Home.name
    )
    val context = LocalContext.current

    var selectedItem = remember { mutableStateOf(0) }

    val items = listOf(
        BottomNavItem(
            name = "Home",
            route = "home",
            icon = Icons.Rounded.Home,
            navigateTo = PolyratingScreen.Home.name
        ),
        BottomNavItem(
            name = "List",
            route = "list",
            icon = Icons.Rounded.List,
            navigateTo = PolyratingScreen.List.name,
        ),
        BottomNavItem(
            name = "Add",
            route = "add",
            icon = Icons.Rounded.Add,
            navigateTo = PolyratingScreen.Add.name,
        ),
        BottomNavItem(
            name = "FAQs",
            route = "faq",
            icon = Icons.Rounded.Info,
            navigateTo = PolyratingScreen.FAQ.name,
        ),

    )

    Scaffold(
        topBar = {
            PolyratingAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        },
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(
                            imageVector = item.icon,
                            contentDescription = "${item.name} Icon",
                        ) },
                        label = { Text(
                            text = item.name,
                            fontWeight = FontWeight.SemiBold,
                        ) },
                        selected = items[index].name === currentScreen.name,
                        onClick = {
                            selectedItem.value = index
                            viewModel.increaseCount()
//                            navController.popBackStack(item.navigateTo, true)
                            navController.navigate(item.navigateTo)
                        }
                    )
                }
            }
        }
    ) {
        Box(modifier = Modifier
            .padding(0.dp)
            .fillMaxWidth()
            .fillMaxHeight()){
            val uiState by viewModel.uiState.collectAsState()
            NavHost(
                navController = navController,
                startDestination = PolyratingScreen.Home.name,
                modifier = modifier.padding(it)
            ) {

                composable(route = PolyratingScreen.Home.name) {
                    if(uiState.fetchingProfessors )
                        MainLoader()
                    else
                        HomeScreen(
                            viewModel,
                            onSearchClick  =  {
                                navController.navigate(PolyratingScreen.List.name)
                            }
                        )
                }
                composable(route = PolyratingScreen.List.name) {
                    ListScreen(
                        viewModel,
                        onAddProf = {
                            selectedItem.value = 2
                            navController.navigate(PolyratingScreen.Add.name)
                        }
                    )
                }
                composable(route = PolyratingScreen.Add.name) {
                    AddScreen(onSuccess = {
                        println("onSuccess Called")
                        selectedItem.value = 1
                        navController.navigate(PolyratingScreen.List.name)
                        Toast.makeText(context, "Thank you for adding a professor. It will be reviewed manually and will be available soon.", Toast.LENGTH_LONG).show()
                    })
                }
                composable(route = PolyratingScreen.FAQ.name) {
                    FaqScreen()
                }

            }
        }
    }
}
//

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PolyratingAppBar(
    currentScreen: PolyratingScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = colorScheme.onSecondaryContainer,
            titleContentColor = colorScheme.onSecondary
        ),
        title = {

            Row( modifier = Modifier
                .fillMaxWidth()
                .padding(end = 15.dp),
                horizontalArrangement = Arrangement.End,

            ) {
                Text(stringResource(currentScreen.title))
            }
        },
        modifier = modifier,

    )
}