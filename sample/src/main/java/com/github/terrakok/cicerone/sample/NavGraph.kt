package com.github.terrakok.cicerone.sample

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.github.terrakok.cicerone.graph.dest
import com.github.terrakok.cicerone.graph.graph
import com.github.terrakok.cicerone.sample.ui.graph.ForkFragment
import com.github.terrakok.cicerone.sample.ui.graph.RoadFragment


fun Graph() = graph {
    edges = {
        dest("1") {
            screen = { id -> FragmentScreen(id) { RoadFragment.getNewInstance(id) } }
            edges = {
                dest("2") {
                    screen = { id -> FragmentScreen(id) { ForkFragment.getNewInstance(id) } }
                    edges = {
                        dest("3") {
                            screen = { id -> FragmentScreen(id) { RoadFragment.getNewInstance(id) } }
                            edges = {
                                dest("4") {
                                    screen = { id -> FragmentScreen(id) { RoadFragment.getNewInstance(id) } }
                                }
                            }
                        }
                        dest("5") {
                            screen = { id -> FragmentScreen(id) { ForkFragment.getNewInstance(id) } }
                            edges = {
                                dest("6") {
                                    screen = { id -> FragmentScreen(id) { RoadFragment.getNewInstance(id) } }
                                }
                                dest("7") {
                                    screen = { id -> FragmentScreen(id) { RoadFragment.getNewInstance(id) } }
                                }
                            }
                        }
                    }
                }
            }
        }
        dest("8") {
            screen = { id -> FragmentScreen(id) { ForkFragment.getNewInstance(id) } }
            edges = {
                dest("9") {
                    screen = { id -> FragmentScreen(id) { RoadFragment.getNewInstance(id) } }
                }
                dest("10") {
                    screen = { id -> FragmentScreen(id) { RoadFragment.getNewInstance(id) } }
                }
            }
        }
    }
}