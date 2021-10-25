package co.tiagoaguiar.course.instagram.common.base

import co.tiagoaguiar.course.instagram.login.data.FakeLoginDataSource
import co.tiagoaguiar.course.instagram.login.data.LoginRepository
import co.tiagoaguiar.course.instagram.register.data.FakeRegisterEmailDataSource
import co.tiagoaguiar.course.instagram.register.data.FakeRegisterNameAndPasswordDataSource
import co.tiagoaguiar.course.instagram.register.data.RegisterEmailRepository
import co.tiagoaguiar.course.instagram.register.data.RegisterNameAndPasswordRepository

object DependencyInjector {
    fun loginRepository(): LoginRepository {
        return LoginRepository(FakeLoginDataSource())
    }

    fun registerEmailRepository(): RegisterEmailRepository {
        return RegisterEmailRepository(FakeRegisterEmailDataSource())
    }

    fun registerNameAndPassword(): RegisterNameAndPasswordRepository {
        return RegisterNameAndPasswordRepository(FakeRegisterNameAndPasswordDataSource())
    }
}