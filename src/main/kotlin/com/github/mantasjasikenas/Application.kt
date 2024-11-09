package com.github.mantasjasikenas

import com.github.mantasjasikenas.plugins.*
import com.github.mantasjasikenas.repository.ProjectRepository
import com.github.mantasjasikenas.repository.SectionRepository
import com.github.mantasjasikenas.repository.TaskRepository
import com.github.mantasjasikenas.repository.UserRepository
import com.github.mantasjasikenas.repository.impl.ProjectRepositoryImpl
import com.github.mantasjasikenas.repository.impl.SectionRepositoryImpl
import com.github.mantasjasikenas.repository.impl.TaskRepositoryImpl
import com.github.mantasjasikenas.repository.impl.UserRepositoryImpl
import com.github.mantasjasikenas.service.JwtService
import com.github.mantasjasikenas.service.SessionService
import com.github.mantasjasikenas.service.UserService
import com.github.mantasjasikenas.service.impl.JwtServiceImpl
import com.github.mantasjasikenas.service.impl.SessionServiceImpl
import com.github.mantasjasikenas.service.impl.UserServiceImpl
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*


fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {
    val userRepository: UserRepository = UserRepositoryImpl()
    val sessionService: SessionService = SessionServiceImpl()
    val jwtService: JwtService = JwtServiceImpl(this,sessionService)
    val userService: UserService = UserServiceImpl(userRepository, jwtService, sessionService)

    val projectRepository: ProjectRepository = ProjectRepositoryImpl()
    val taskRepository: TaskRepository = TaskRepositoryImpl()
    val sectionRepository: SectionRepository = SectionRepositoryImpl()


    configureCORS()
    configureMonitoring()
    configureSerialization()
    configureDatabases()
    configureHTTP()
    configureSecurity(jwtService)
    configureSwaggerUI()
    configureRouting(projectRepository, sectionRepository, taskRepository, userService)
    configureValidation()
}
