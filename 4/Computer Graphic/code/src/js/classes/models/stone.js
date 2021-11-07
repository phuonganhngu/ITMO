import { DodecahedronBufferGeometry, Group, Mesh, MeshPhysicalMaterial} from "three";

export default class Stone {

    // x, y, z - position
    // size - size render
    constructor(x, y, z, size) {
        this.stone = this.createStone();
        this.stone.position.set(x, y, z);
        this.stone.scale.set(size, size, size);
    }

    createStone() {
        let group = new Group();

        //define colors
        let gray = new MeshPhysicalMaterial({color: 0x304B58}); 

        let obj = new Mesh(new DodecahedronBufferGeometry(9,0), gray);
        obj.position.y = 6;
        obj.receiveShadow = true;
        obj.castShadow = true;


        group.add(obj);
        return group;
    }
}