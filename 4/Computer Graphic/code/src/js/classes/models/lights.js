import {PointLight, DirectionalLight} from "three";

export default function x(scene) {


    var dirLight = new DirectionalLight(0xffffff, 0.1);

    dirLight.position.set(-25, 45, -5);

    dirLight.castShadow = true;

    dirLight.shadow.camera.near = 10;
    dirLight.shadow.camera.far = 200;
    dirLight.shadow.camera.left = -64;
    dirLight.shadow.camera.bottom = -64;
    dirLight.shadow.camera.right = 64;
    dirLight.shadow.camera.top = 64;
    dirLight.shadow.bias=0.0001;
    dirLight.target.position.set(20, -2, 0);

    dirLight.shadow.radius = 1;

    scene.add(dirLight);
    scene.add(dirLight.target);

    
    let centerLight = new PointLight(0xee693c, 2, 30, 2);
    centerLight.position.set(0, -11, -0.5);
    scene.add(centerLight);

}
