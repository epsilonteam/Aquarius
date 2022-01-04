package net.spartanb312.aquarius.util.graphics.shaders

import net.spartanb312.aquarius.util.graphics.MatrixUtils
import org.joml.Matrix4f
import org.lwjgl.opengl.GL20.glGetUniformLocation

open class DrawShader(vertShaderPath: String, fragShaderPath: String) : Shader(vertShaderPath, fragShaderPath) {
    private val projectionUniform = glGetUniformLocation(programID, "projection")
    private val modelViewUniform = glGetUniformLocation(programID, "modelView")

    fun updateMatrix() {
        updateModelViewMatrix()
        updateProjectionMatrix()
    }

    fun updateProjectionMatrix() {
        MatrixUtils.loadProjectionMatrix().uploadMatrix(projectionUniform)
    }

    fun updateProjectionMatrix(matrix: Matrix4f) {
        MatrixUtils.loadMatrix(matrix).uploadMatrix(projectionUniform)
    }

    fun updateModelViewMatrix() {
        MatrixUtils.loadModelViewMatrix().uploadMatrix(modelViewUniform)
    }

    fun updateModelViewMatrix(matrix: Matrix4f) {
        MatrixUtils.loadMatrix(matrix).uploadMatrix(modelViewUniform)
    }
}