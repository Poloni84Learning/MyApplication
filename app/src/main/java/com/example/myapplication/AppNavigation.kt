package com.example.myapplication


import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.models.JsonFileManager
import com.example.myapplication.models.ToolViewModel
import com.example.myapplication.models.ToolViewModelFactory
import com.example.myapplication.screens.loginandregister.ForgotPasswordScreen
import com.example.myapplication.screens.loginandregister.LoginScreen
import com.example.myapplication.screens.loginandregister.NewPasswordScreen
import com.example.myapplication.screens.loginandregister.RegisterScreen
import com.example.myapplication.screens.loginandregister.VerificationCodeScreen
import com.example.myapplication.screens.workspace.ProjectScreen
import com.example.myapplication.screens.workspace.SaveProjectScreen
import com.example.myapplication.screens.workspace.WorkSpaceScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val toolViewModel: ToolViewModel = viewModel(
        factory = ToolViewModelFactory(
            jsonFileManager = JsonFileManager(context)
        )
    )
    NavHost(
        navController = navController,
        startDestination = "project"

    ) {

        composable("welcome") {
            com.example.myapplication.screens.WelcomeScreen {
                navController.navigate("login")
            }
        }
        composable("login") {
            LoginScreen(
                onNavigateToForgotPassword = {
                    navController.navigate("forgotPassword")
                },
                onNavigateToRegister = {
                    navController.navigate("register")
                },
                onNavigateToProject = {
                    navController.navigate("project")
                }
            )

        }

        composable("register") {
            RegisterScreen(
                onNavigateToProject = {
                    navController.navigate("project")
                },
                onNavigateBack = {
                    navController.popBackStack()
                })

        }

        composable("forgotPassword") {
            ForgotPasswordScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onSendCode = {
                    navController.navigate("verificationCode")
                })
        }

        composable("verificationCode") {
            VerificationCodeScreen(
                onConfirm = {
                    navController.navigate("newPassword")
                },
                onResendCode = {

                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("newPassword") {
            NewPasswordScreen(
                onConfirm = {
                    navController.navigate("project")
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable("project") {
            ProjectScreen(
                onNavigateToWorkSpace = { projectId ->
                    if (projectId == null) {
                        navController.navigate("workspace/new")
                    } else {
                        navController.navigate("workspace/$projectId")
                    }
                },
                toolViewModel = toolViewModel
            )
        }

        composable("workspace/{projectId}") { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("projectId")
            WorkSpaceScreen(
                projectId = projectId,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateSave = {
                    navController.navigate("save")
                },

                toolViewModel = toolViewModel
            )
        }



        composable("save") {
            SaveProjectScreen(
                onNavigateBack = {
                    navController.popBackStack()

                },
                onNavigateToPro = {
                    navController.navigate("project")
                },
                toolViewModel = toolViewModel
            )
        }
    }
}

