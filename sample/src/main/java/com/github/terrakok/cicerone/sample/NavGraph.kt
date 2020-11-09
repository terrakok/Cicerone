package com.github.terrakok.cicerone.sample

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.github.terrakok.cicerone.graph.*
import com.github.terrakok.cicerone.sample.ui.graph.ForkFragment
import com.github.terrakok.cicerone.sample.ui.graph.RoadFragment

private val RoadScreen = { id: String -> FragmentScreen(id) { RoadFragment.getNewInstance(id) } }
private val ForkScreen = { id: String -> FragmentScreen(id) { ForkFragment.getNewInstance(id) } }

fun Graph() = graph {
    edges = {
        dest("1") {
            screen = RoadScreen
            edges = {
                dest("2") {
                    screen = ForkScreen
                    edges = {
                        dest("3") {
                            screen = RoadScreen
                            edges = {
                                dest("4") {
                                    screen = RoadScreen
                                    edges = {
                                        edge("5")
                                    }
                                }
                            }
                        }
                        dest("5") {
                            screen = ForkScreen
                            edges = {
                                dest("6") {
                                    screen = RoadScreen
                                }
                                dest("7") {
                                    screen = RoadScreen
                                    jumps = {
                                        finish("1")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        dest("8") {
            screen = ForkScreen
            edges = {
                dest("9") {
                    screen = RoadScreen
                    edges = {
                        edge("1")
                    }
                    jumps = {
                        jump("1") {
                            backTo = ROOT_ID
                            chain("1", "2", "3", "4", "5", "7")
                        }
                    }
                }
                dest("10") {
                    screen = ForkScreen
                    edges = {
                        dest("11") {
                            screen = RoadScreen
                        }
                        edge("10")
                    }
                }
            }
        }
    }
}