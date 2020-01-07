package com.github.nekkan.jdakotlinlibrary.collections.utils

import com.github.nekkan.jdakotlinlibrary.client.DiscordResult

fun argument(l: List<String>, i: Int): String? { try { return l[i - 1] } catch(exc: java.lang.IndexOutOfBoundsException) { return null } }
fun argument(l: Array<String>, i: Int): String? { try { return l[i - 1] } catch(exc: java.lang.IndexOutOfBoundsException) { return null } }
fun argument(l: String, i: Int): String? { try { return l.split(" ")[i - 1] } catch(exc: java.lang.IndexOutOfBoundsException) { return null } }
fun fail(run: () -> Unit): DiscordResult { run(); return DiscordResult.ERROR }