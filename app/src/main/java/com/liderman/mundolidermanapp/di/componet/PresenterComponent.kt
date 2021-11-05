package com.liderman.mundolidermanapp.di.componet

import com.liderman.mundolidermanapp.di.PresenterScope
import com.liderman.mundolidermanapp.di.module.PresenterModule
import com.liderman.mundolidermanapp.presentation.ui.activity.DirectoryActivity
import com.liderman.mundolidermanapp.presentation.ui.activity.QuestionnaireActivity
import com.liderman.mundolidermanapp.presentation.ui.activity.UpdateProfileActivity
import com.liderman.mundolidermanapp.presentation.ui.activity.*
import com.liderman.mundolidermanapp.presentation.ui.activity.AccidenteDeTrabajoActivity
import com.liderman.mundolidermanapp.presentation.ui.activity.AfiliacionesActivity
import com.liderman.mundolidermanapp.presentation.ui.activity.DescansoMedicoActivity
import com.liderman.mundolidermanapp.presentation.ui.activity.LicenciaActivity
import com.liderman.mundolidermanapp.presentation.ui.activity.DeseoAfiliarActivity
import com.liderman.mundolidermanapp.presentation.ui.activity.DeseoDesafiliarActivity
import com.liderman.mundolidermanapp.presentation.ui.activity.RenunciaActivity
import com.liderman.mundolidermanapp.presentation.ui.activity.LidermaniaActivity
import com.liderman.mundolidermanapp.presentation.ui.activity.MainActivity
import com.liderman.mundolidermanapp.presentation.ui.activity.MyTicketsActivity
import com.liderman.mundolidermanapp.presentation.ui.activity.SgiDocActivity
import com.liderman.mundolidermanapp.presentation.ui.activity.AmaPayActivity
import com.liderman.mundolidermanapp.presentation.ui.activity.LoansActivity
import com.liderman.mundolidermanapp.presentation.ui.activity.LoginActivity
import com.liderman.mundolidermanapp.presentation.ui.activity.TareoActivity
import com.liderman.mundolidermanapp.presentation.ui.activity.UtilidadActivity
import com.liderman.mundolidermanapp.presentation.ui.fragment.HomeFragment
import com.liderman.mundolidermanapp.presentation.ui.fragment.PlayFragment
import com.liderman.mundolidermanapp.presentation.ui.fragment.ProfileFragment
import com.liderman.mundolidermanapp.presentation.ui.fragment.TrafficFragment
import dagger.Component

@PresenterScope
@Component(dependencies = [AppComponent::class], modules = [PresenterModule::class])
interface PresenterComponent {
    fun inject(fragment: ProfileFragment)
    fun inject(fragment: HomeFragment)
    fun inject(fragment: TrafficFragment)
    fun inject(fragment: PlayFragment)
    fun inject(activity: MainActivity)
    fun inject(activity: LoginActivity)
    fun inject(activity: UpdateProfileActivity)
    fun inject(activity: SgiDocActivity)
    fun inject(activity: UtilidadActivity)
    fun inject(activity: PlanillasActivity)
    fun inject(activity: LidermaniaActivity)
    fun inject(activity: AfiliacionesActivity)
    fun inject(activity: DeseoDesafiliarActivity)
    fun inject(activity: DeseoAfiliarActivity)
    fun inject(activity: AccidenteDeTrabajoActivity)
    fun inject(activity: LoansActivity)
    fun inject(activity: OtrosActivity)
    fun inject(activity: PreguntasFrecuentesPlanillasActivity)
    fun inject(activity: TareoActivity)
    fun inject(activity: RenunciaActivity)
    fun inject(activity: MyTicketsActivity)
    fun inject(activity: AmaPayActivity)
    fun inject(activity: QuestionnaireActivity)
    fun inject(activity: UniformesActivity)
    fun inject(activity: SolicitudUniformeActivity)
    fun inject(activity: PreguntasFrecuentesUniformeActivity)
    fun inject(activity: VacacionesActivity)
    fun inject(activity: SucamecActivity)
    fun inject(activity: LicenciaActivity)
    fun inject(activity: DescansoMedicoActivity)
    fun inject(activity:ContratoActivity)
    fun inject(activity:DirectoryActivity)


}
