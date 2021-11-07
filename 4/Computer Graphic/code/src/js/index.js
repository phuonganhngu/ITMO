import "./../css/style.css";

import {
    Scene, MeshBasicMaterial, SphereGeometry, Mesh, FrontSide, AdditiveBlending,
} from "three";
import { WEBGL } from "three/examples/jsm/WebGL.js";

import Controls from "./classes/controllers/controls";
import Renderer from "./classes/controllers/renderer";
import Camera from "./classes/controllers/camera";
import * as Lights from "./classes/models/lights";
import * as woodpile from "./classes/controllers/loadObj";

import * as Ground from "./classes/models/ground";

import Moon from "./classes/models/moon";
import Sky from "./classes/models/sky";
import Tree from "./classes/models/tree";
import Stone from "./classes/models/stone";
import Firefly from "./classes/models/firefly";

if (WEBGL.isWebGLAvailable()) {
    init();
} else {
    let warning = WEBGL.getWebGLErrorMessage();
    document.body.appendChild(warning);
}

function init() {
    let container = document.body;

    let scene = new Scene();

    let renderer = new Renderer(container);
    let camera = new Camera(renderer.threeRenderer);
    let controls = new Controls(
        camera.threeCamera,
        renderer.threeRenderer.domElement
    );
    controls.threeControls.update();

    scene.add(new Sky().sky);
    scene.add(Ground.default());
    Lights.default(scene);
    scene.add(new Moon().moon);

    let customMaterial = new MeshBasicMaterial(
        {
            color: 0xee693c,
            side: FrontSide,
            blending: AdditiveBlending,
            transparent: true
        });

    let sphereGeom = new SphereGeometry(2, 32, 32);

    let outerGlow = new Mesh(sphereGeom, customMaterial);
    outerGlow.position.set(0, -12.5, -0.5);

    scene.add(outerGlow);

    let customMaterial2 = new MeshBasicMaterial(
        {
            color: 0xfb0808,
            side: FrontSide,
            blending: AdditiveBlending,
            //transparent: true
        });

    let sphereGeom2 = new SphereGeometry(1, 32, 32);

    let innerGlow = new Mesh(sphereGeom2, customMaterial2);
    innerGlow.position.set(0, -13, -0.5);

    scene.add(innerGlow);


    let fireflies = [];
    
    var i, j, k;
   /* for (i = -58; i < 58; i = i + 20)
        for (j = -58; j < 58; j = j + 20) if ((i * i + j * j > 250) && (i * i + j * j < 3400)) {
            k = Math.random();
            if (k < 0.3) scene.add(new Stone(i, -21.5, j, 0.2 + Math.random() * 0.3).stone);
            else scene.add(new Tree(i, -21, j, 0.4 + Math.random() * 0.5).tree);
        }

    
    for (i = -47; i < 47; i = i + 24)
        for (j = -47; j < 47; j = j + 24) if ((i * i + j * j > 280) && (i * i + j * j < 3500)) {
            fireflies.push(new Firefly(scene,i, -17 + Math.floor(Math.random() * 8), j,  
                        Math.floor(Math.random() * 2), Math.floor(Math.random() * 11), 3));
        }
   */

  scene.add(new Tree(-10, -21, -10, 0.4 + Math.random() * 0.5).tree);
    woodpile.default(scene);


    function animate() {
        requestAnimationFrame(animate);
        controls.threeControls.update();
        for (var f in fireflies) {
            fireflies[f].update();
        }
        renderer.render(scene, camera.threeCamera);
    }

    animate();
}
