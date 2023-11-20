package com.devgusta.crud_taskapp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


//Aqui se cria a class do adapter passando no construtor um fragmentActivity, extendendo a class
//        FragmentStateAdapter passando no construtor o valor do construtor primario (fragmentActivity)
class ViewPagerAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    // Aqui Criamos duas listas uma que armazena os Fragments e a outra os titulos ou quantias
    private val fragmentList: MutableList<Fragment> = ArrayList()
    private val titleList: MutableList<Int> = ArrayList()


    //Esta funcao recupera o valor do Titulo do fragment para a listagem posteriormente
    fun getTitle(position: Int): Int {
        return titleList[position]
    }

    //Esta funcao adiciona um fragment criando com base O Fragment eo titulo,
    // adicionando-os para a nossa lista
    fun addFragment(fragment: Fragment, title: Int) {
        fragmentList.add(fragment)
        titleList.add(title)
    }

    //Funcao que retorna a quantidade de itens a serem listados
    override fun getItemCount(): Int {
        return fragmentList.size
    }


    //Funcao principal para a criacao Da listagem, pega a posicao por valor Int e retorna
    // com base na lista criada
    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}