package com.smarttoolfactory.tutorial9_2dynamicfeatureimplementation2.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.smarttoolfactory.tutorial9_2core.di.CoreModule
import com.smarttoolfactory.tutorial9_2core.model.CoreActivityDependency
import com.smarttoolfactory.tutorial9_2core.model.CoreDependency
import com.smarttoolfactory.tutorial9_2dynamicfeatureimplementation2.MainActivity
import com.smarttoolfactory.tutorial9_2dynamicfeatureimplementation2.R
import com.smarttoolfactory.tutorial9_2dynamicfeatureimplementation2.coreComponent
import com.smarttoolfactory.tutorial9_2dynamicfeatureimplementation2.model.FragmentObject
import com.smarttoolfactory.tutorial9_2dynamicfeatureimplementation2.model.HomeFragmentObject
import javax.inject.Inject

class HomeFragment : Fragment() {

    /**
     * Injected from [CoreModule] with @Singleton scope
     */
    @Inject
    lateinit var coreDependency: CoreDependency

    /**
     * Injected from [CoreModule] with no scope
     */
    @Inject
    lateinit var coreActivityDependency: CoreActivityDependency

    @Inject
    lateinit var homeFragmentObject: HomeFragmentObject

    /**
     * ❌ This dependency cannot be injected without providing inside [HomeFragmentModule]
     * because this fragment has no relation with [MainActivity]
     */
//    @Inject
//    lateinit var mainActivityObject: MainActivityObject

    @Inject
    lateinit var fragmentObject: FragmentObject


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnCamera = view.findViewById<Button>(R.id.btnCamera)
        val btnPhotos = view.findViewById<Button>(R.id.btnPhotos)

        btnCamera.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_nav_graph_camera)
        }

        btnPhotos.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_nav_graph_photos)
        }

        view.findViewById<TextView>(R.id.tvInfo).text =
                "CoreModule @Singleton coreDependency: ${coreDependency.hashCode()}\n" +
                        "CoreModule no scope coreActivityDependency: ${coreActivityDependency.hashCode()}\n" +
                        "HomeFragmentModule no scope homeFragmentObject: ${homeFragmentObject.hashCode()}\n" +
                        "Constructor @FragmentScope fragmentObject: ${fragmentObject.hashCode()}"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        initCoreDependentInjection()
        super.onCreate(savedInstanceState)
    }

    private fun initCoreDependentInjection() {

        val homeFragmentComponent = DaggerHomeFragmentComponent.factory()
                .create(
                        coreComponent = requireActivity().coreComponent(),
                        homeFragmentModule = HomeFragmentModule(),
                        application = requireActivity().application
                )

        homeFragmentComponent.inject(this)

    }


}