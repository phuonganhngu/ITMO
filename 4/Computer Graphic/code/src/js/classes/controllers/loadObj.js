import { OBJLoader } from 'three/examples/jsm/loaders/OBJLoader.js';
import woodpile from "../../../resources/models/OBJ/woodpile.obj";
import { EquirectangularReflectionMapping, Mesh, MeshPhysicalMaterial, TextureLoader } from "three";
import woodpileTexture from "../../../resources/models/OBJ/WoodTexture.png";

export default function (scene) {
    const loader = new OBJLoader();

    // load a resource
    loader.load(
        woodpile,
        function (object) {

            let textureLoader = new TextureLoader();
            let textureCube = textureLoader.load(woodpileTexture, function () {

            });
            textureCube.mapping = EquirectangularReflectionMapping;
            textureCube.needsUpdate = true;

            object.traverse(function (child) {

                if (child instanceof Mesh) {
                    child.material = new MeshPhysicalMaterial();
                    child.material.map = textureCube;
                    child.material.envMap = textureCube;
                    child.castShadow = true;
                    child.receiveShadow = true;
                }

            });

            object.castShadow = true;
            object.receiveShadow = true;

            object.position.set(0, -21.2, 0);
            object.rotateY(Math.PI / 4);
            object.scale.set(1.4, 1.4, 1.4);
            scene.add(object);

        },

        // called when loading is in progresses
        function (xhr) {

            console.log((xhr.loaded / xhr.total * 100) + '% loaded');

        },
        // called when loading has errors
        function (error) {

            console.log('An error happened');

        }
    );
}