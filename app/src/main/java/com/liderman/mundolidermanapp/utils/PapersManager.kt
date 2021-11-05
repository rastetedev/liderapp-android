package com.liderman.mundolidermanapp.utils

import com.liderman.mundolidermanapp.domain.entity.contact.ContactEntity
import com.liderman.mundolidermanapp.domain.entity.QualityEntity
import com.liderman.mundolidermanapp.domain.entity.announcements.AnnouncementEntity
import com.liderman.mundolidermanapp.domain.entity.contract.ContractEntity
import com.liderman.mundolidermanapp.domain.entity.login.LoginEntity
import io.paperdb.BuildConfig
import io.paperdb.Paper

object PapersManager {
    val list: MutableList<QualityEntity> = mutableListOf(
        QualityEntity().apply {
            this.id = 0
            this.name = "Lidernet"
            this.isGood = null
        },
        QualityEntity().apply {
            this.id = 1
            this.name = "Tareo"
            this.isGood = null
        },
        QualityEntity().apply {
            this.id = 2
            this.name = "Préstamo"
            this.isGood = null
        },
        QualityEntity().apply {
            this.id = 3
            this.name = "Valor\nAgregado"
            this.isGood = null
        },
        QualityEntity().apply {
            this.id = 4
            this.name = "Capacitación"
            this.isGood = null
        }
    )

    var loginEntity: LoginEntity
        set(value) {
            Paper.book(BuildConfig.FLAVOR).write("user", value)
        }
        get() {
            return Paper.book(BuildConfig.FLAVOR).read("user", LoginEntity.default())
        }

    var contractEntity: ContractEntity
        set(value) {
            Paper.book(BuildConfig.FLAVOR).write("contract", value)
        }
        get() {
            return Paper.book(BuildConfig.FLAVOR).read("contract", ContractEntity.default())
        }

    var session: Boolean
        set(value) {
            Paper.book(BuildConfig.FLAVOR).write("session", value)
        }
        get() {
            return Paper.book(BuildConfig.FLAVOR).read("session", false)
        }

    var listTraffic: MutableList<QualityEntity>
        set(value) {
            Paper.book(BuildConfig.FLAVOR).write("traffic", value)
        }
        get() {
            return Paper.book(BuildConfig.FLAVOR).read("traffic", list)
        }

    var announcementsEntity: List<AnnouncementEntity>
        set(value) {
            Paper.book(BuildConfig.FLAVOR).write("announce", value)
        }
        get() {
            return Paper.book(BuildConfig.FLAVOR).read("announce", ArrayList())
        }

    var contactsEntity: List<ContactEntity>
        set(value) {
            Paper.book(BuildConfig.FLAVOR).write("contactEntity", value)
        }
        get() {
            return Paper.book(BuildConfig.FLAVOR).read("contactEntity", ArrayList())
        }
}