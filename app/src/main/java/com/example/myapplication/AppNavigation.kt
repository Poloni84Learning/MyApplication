package com.example.myapplication

import android.os.WorkDuration
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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

    NavHost(
        navController = navController,
        startDestination = "workspace"
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
                onNavigateToWorkSpace = {
                    navController.navigate("workspace")
                }
            )
        }

        composable("workspace") {
            WorkSpaceScreen(
                onNavigateSave = {
                    navController.navigate("save")
                }
            )
        }

        composable("piano") {
            com.example.myapplication.screens.workspace.tools.PianoScreen()
        }

        composable("save") {
            SaveProjectScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

