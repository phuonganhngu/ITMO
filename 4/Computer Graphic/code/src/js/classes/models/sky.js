import { Mesh, SphereBufferGeometry, BackSide, MeshBasicMaterial, TextureLoader } from "three";
import nightText from "../../../resources/img/night.jpg";

export default class Sky {
    constructor() {
        this.sky = this.createSky();
    }

    createSky() {

        let skyGeo = new SphereBufferGeometry(64, 64, 64);
        const textureLoader = new TextureLoader();

        let map = textureLoader.load(nightText);

        let skyMat = new MeshBasicMaterial({
            side: BackSide,
            color: 0x001733,
            map: map,
        });
        let sky = new Mesh(skyGeo, skyMat);
        return sky;
    }
}