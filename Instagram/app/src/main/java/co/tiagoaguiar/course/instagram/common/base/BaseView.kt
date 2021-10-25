package co.tiagoaguiar.course.instagram.common.base

import co.tiagoaguiar.course.instagram.login.Login

interface BaseView<T> {
    var presenter: T
}