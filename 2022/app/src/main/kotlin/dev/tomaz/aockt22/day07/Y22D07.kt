package dev.tomaz.aockt22.day07

import dev.tomaz.aockt22.utils.readInput

/**
 * Created by TomazWang on 2022/12/09.
 *
 *
 * @author Tomaz Wang
 * @since 2022/12/09
 **/

fun main() {
    
    // val sampleInput = readInput(7, "sample.txt")
    val puzzleInput = readInput(7, "input.txt")
    
    // build maps
    val root = buildDirMap(puzzleInput)
    
    
    
    println(root.toString())
    println("rootSize: ${root.totalSize}")
    println(findDirLessThan(size = 100_000, root).sumOf { it.totalSize })
    
    

    // part two
    
    val totalSpace = 70_000_000
    val updateSpace = 30_000_000
    
    val usedSpace = root.totalSize
    val remainSpace = totalSpace - usedSpace
    val needToDelete = updateSpace - remainSpace
    
    
    val dirs = findDirBy(root) {it.totalSize > needToDelete }
    val del = dirs.minByOrNull { it.totalSize }!!
    
    println("usedSpace = $usedSpace")
    println("remainSpace = $remainSpace")
    println("needToDelete = $needToDelete")
    println("dirs = ${dirs.map { it.name }}")
    println(del.name)
    println(del.totalSize)
    
}


private fun buildDirMap(commandLines: List<String>): AcDirectory {
    
    val cdCmdRegex = """\$ cd (\S+)""".toRegex()
    val dirRegex = """dir (\S+)""".toRegex()
    val fileRegex = """(\d+) (\S+)""".toRegex()
    
    
    val rootDir = AcDirectory(name = "/", null)
    var currentPointer = rootDir
    
    commandLines.forEach { line ->
        
        println("(${currentPointer.name}\n)>$line")
        
        when {
            line.matches(cdCmdRegex) -> { // cd
                val dirName = cdCmdRegex.find(line)?.groups?.get(1)?.value
                
                println("cd to $dirName")
                
                currentPointer = when (dirName) {
                    ".." -> currentPointer.parent ?: throw Error("$currentPointer dir has null parent")
                    "/" -> rootDir
                    else -> dirName?.let {
                        currentPointer.getChildDir(it)
                    } ?: throw Error("$currentPointer dir has null parent")
                }
                
                println("current pointer = ${currentPointer.name}")
            }
            
            line.matches(dirRegex) -> { // dir xxxx
                dirRegex.find(line)?.groups?.get(1)?.value?.let { dirName ->
                    val dir = AcDirectory(dirName, currentPointer)
                    if (!currentPointer.containsChild(dirName)) {
                        println("add dir($dir) to ${currentPointer.name}")
                        currentPointer.addChild(dir)
                    }
                }
            }
            
            line.matches(fileRegex) -> { // file
                val (size, fileName) = fileRegex.find(line)?.destructured!!
                currentPointer.addChild(AcFile(fileName, size = size.toInt(), parent = currentPointer))
                println("add file($fileName) to ${currentPointer.name}")
            }
        }
    }
    return rootDir
}

private fun findDirLessThan(size: Int, root: AcDirectory): List<AcDirectory> = findDirBy(root) { it.totalSize <= size }

private fun findDirBy(root: AcDirectory, con: (AcDirectory) -> Boolean): List<AcDirectory> {
    val dirs = mutableListOf<AcDirectory>()
    
    if (con(root)) dirs.add(root)
    
    dirs.addAll(
        root.children
            .filterIsInstance<AcDirectory>()
            .flatMap { findDirBy(it, con) }
    )
    
    return dirs
}

//
// private fun getAtMost100KSizeChildrenDirSizeSum(dir: AcDirectory): Int {
//     val dirSize = if (dir.totalSize <= 100_000) dir.totalSize else 0
//     return dirSize +
//             dir.children
//                 .filterIsInstance<AcDirectory>()
//                 .sumOf { childDir ->
//                     val grantChildren = childDir.children.filterIsInstance<AcDirectory>()
//
//                     if (grantChildren.isEmpty()) { // not further child dir
//                         if (childDir.totalSize <= 100_000) childDir.totalSize else 0
//                     } else {
//                         getAtMost100KSizeChildrenDirSizeSum(childDir)
//                     }
//                 }
// }


private sealed interface AcElement {
    val name: String
    val totalSize: Int
    val parent: AcDirectory?
}

private data class AcDirectory(override val name: String, override val parent: AcDirectory?) :
    AcElement {
    
    val children = mutableListOf<AcElement>()
    override val totalSize: Int
        get() = children.sumOf { it.totalSize }
    
    
    fun addChild(child: AcElement) = this.children.add(child)
    
    fun containsChild(childName: String) = this.children.any { it.name == childName }
    
    fun getChildDir(childName: String) = this.children
        .filterIsInstance<AcDirectory>()
        .find { it.name == childName }
    
    
    companion object {
        // val Root = AcDirectory("/", null)
    }
    
    
    override fun toString(): String {
        
        val self = "- $name(dir)"
        
        // val childrenFileStr = children.filterIsInstance<AcFile>().map {  }
        // val childrenDirStr = children.filterIsInstance<AcDirectory>().map { it.toString() }
        
        
        val childrenStr = children.map {
            when (it) {
                is AcDirectory -> it.toString().lines().map { l -> "  $l" }.joinToString("\n")
                is AcFile -> "- ${it.name}(file, size = ${it.totalSize})"
            }
        }
        
        return self + "\n" + childrenStr.joinToString("\n")
    }
}

private data class AcFile(
    override val name: String,
    val size: Int,
    override val parent: AcDirectory?
) : AcElement {
    override val totalSize: Int
        get() = size
}
