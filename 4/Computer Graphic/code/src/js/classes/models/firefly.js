import { FrontSide, PointLight, SphereGeometry, Mesh, MeshBasicMaterial, } from "three";
export default class Firefly {
   
    // x, y, z - position
    // size - size render
    constructor(scene, x, y, z, down, time, size) {
        this.firefly = this.create();
        this.firefly.position.set(x, y, z);
        this.firefly.scale.set(size, size, size);
        scene.add(this.firefly);
        
        this.time = time;
        this.down = down; // 1 = move down, 0 = move up
    }

    create() {
        let bulbGeometry = new SphereGeometry(0.1, 8, 8);
        let bulbLight = new PointLight(0xffee88, 1, 15, 5);
        let lightYellow =  new MeshBasicMaterial({
            color: 0xf2d944,
            side: FrontSide,
        }); 

        bulbLight.add(new Mesh(bulbGeometry, lightYellow));
        return bulbLight;
    }

    update() {
        let move = 0.25;
        let step = 10;
        if (this.down === 1) {
            if (this.time === 0) {
                this.time = step;
                this.down = 0;
                return;
            }
            this.firefly.translateY(-move);
            this.time--;
            return;
        }
        if (this.down === 0) {
            if (this.time === 0) {
                this.time = step;
                this.down = 1;
                return;
            }
            this.firefly.translateY(+move);
            this.time--;
            return;
        }
    }
}