import {Mesh, MeshStandardMaterial, CircleGeometry, RepeatWrapping, TextureLoader} from "three";
import grassTexture from "../../../resources/img/grass2.jpg";

export default function ground() {
    const textureLoader = new TextureLoader();

    let grass = textureLoader.load(grassTexture);
    grass.wrapT = RepeatWrapping;
    grass.wrapS = RepeatWrapping;
    grass.repeat.set(20, 20);

    let grassMaterial = new MeshStandardMaterial({
        color: 0xffffff,
        map: grass
    });


    var geometry2 = new CircleGeometry(60, 32);
    var material2 = grassMaterial;
    var ground = new Mesh(geometry2, material2);
    ground.receiveShadow = true;
    ground.castShadow = true;
    ground.rotation.x = -Math.PI / 2;
    ground.position.y = -21;
    return ground;
}