# Kulbako Artemy 2020 for Luxoft

import bpy
import sys
import os
from os import path
import json
import glob


# Применяет текстуру к материалы, если они имеют одинаковое название
def applyTextures(materials, textures):
    for m in materials:
        for t in textures:
            fileName = path.basename(path.splitext(t)[0])
            if m.name == fileName:
                m.use_nodes = True
                bsdf = m.node_tree.nodes["Principled BSDF"]
                texImage = m.node_tree.nodes.new('ShaderNodeTexImage')
                texImage.image = bpy.data.images.load(t)
                m.node_tree.links.new(bsdf.inputs['Base Color'], texImage.outputs['Color'])


# Устанавливает параметры рендера и запускает его
def render():
    argv = sys.argv
    argv = argv[argv.index("--") + 1:]
    params = json.loads(argv[0])
    textures = glob.glob(params["textures"] + "/*.jpg")
    applyTextures(bpy.data.materials, textures)
    renderParams = bpy.context.scene.render
    bpy.context.scene.display.render_aa = params["aa"]
    renderParams.image_settings.file_format = params["format"]
    renderParams.filepath = params["output"]
    renderParams.resolution_x = params["width"]
    renderParams.resolution_y = params["height"]
    bpy.data.scenes["Scene"].render.image_settings.compression = int(params["compress"])
    bpy.ops.render.render(write_still=True, use_viewport=True)


if __name__ == "__main__": render()