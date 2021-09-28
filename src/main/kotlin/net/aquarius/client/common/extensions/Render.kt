package net.aquarius.client.common.extensions

import net.minecraft.client.renderer.RenderGlobal
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.client.shader.Framebuffer
import net.minecraft.client.shader.Shader
import net.minecraft.client.shader.ShaderGroup
import net.aquarius.client.mixin.mixins.accessor.render.AccessorRenderGlobal
import net.aquarius.client.mixin.mixins.accessor.render.AccessorRenderManager
import net.aquarius.client.mixin.mixins.accessor.render.AccessorShaderGroup

val RenderGlobal.entityOutlineShader: ShaderGroup get() = (this as AccessorRenderGlobal).entityOutlineShader

val RenderManager.renderPosX: Double get() = (this as AccessorRenderManager).renderPosX
val RenderManager.renderPosY: Double get() = (this as AccessorRenderManager).renderPosY
val RenderManager.renderPosZ: Double get() = (this as AccessorRenderManager).renderPosZ
val RenderManager.renderOutlines: Boolean get() = (this as AccessorRenderManager).renderOutlines

val ShaderGroup.listShaders: List<Shader> get() = (this as AccessorShaderGroup).listShaders
val ShaderGroup.listFrameBuffers: List<Framebuffer> get() = (this as AccessorShaderGroup).listFramebuffers
