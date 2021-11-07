import {CylinderBufferGeometry, ConeBufferGeometry, Group, Mesh, MeshPhysicalMaterial} from "three";

export default class Tree {

    // x, y, z - position
    // size - size render
    constructor(x, y, z, size) {
        this.tree = this.createTree();
        this.tree.position.set(x, y, z);
        this.tree.scale.set(size, size, size);
    }

    createTree() {
        let group = new Group();

        //define colors
        let darkBrown = new MeshPhysicalMaterial({color: 0x3d2817}); 
        let darkGreen = new MeshPhysicalMaterial({color: 0x2d4c1e}); 
        let normalGreen = new MeshPhysicalMaterial({color: 0x356E19}); 
        let lightGreen = new MeshPhysicalMaterial({color: 0x79e345}); 

        let l0 = new Mesh(new CylinderBufferGeometry(2, 2, 12, 6, 1, true), darkBrown);
        l0.position.y = 6;
        l0.receiveShadow = true;
        l0.castShadow = true;
        let l1 = new Mesh(new ConeBufferGeometry(10, 14, 8), darkGreen);
        l1.position.y = 18;
        l1.receiveShadow = true;
        l1.castShadow = true;
        let l2 = new Mesh(new ConeBufferGeometry(8, 13, 8), normalGreen);
        l2.position.y = 25;
        l2.receiveShadow = true;
        l2.castShadow = true;
        
        let l3 = new Mesh(new ConeBufferGeometry(6, 12, 8), lightGreen);
        l3.position.y = 32;
        l3.receiveShadow = true;
        l3.castShadow = true;
        

        group.add(l0);
        group.add(l1);
        group.add(l2);
        group.add(l3);
        return group;
    }
}